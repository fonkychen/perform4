package cn.aolc.group.performance.weixin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.aolc.group.performance.dao.UserRandomCommentRepository;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.tenant.DailyMarkCategory;
import cn.aolc.group.performance.model.StaffDailyBoardItemModel;
import cn.aolc.group.performance.model.StaffDailyBoardModel;
import cn.aolc.group.performance.model.WeeklyCalendarModel;
import cn.aolc.group.performance.security.UserPasswordDetails;
import cn.aolc.group.performance.service.rest.UserIndicatorService;
import cn.aolc.group.performance.service.rest.UserRestService;
import cn.aolc.group.performance.service.rest.UserScoredService;
import cn.aolc.group.performance.util.PerformanceUtil;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.service.WechatEventService;
import cn.aolc.group.performance.weixin.util.WeixinUtil;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.EventMessage;
import weixin.popular.bean.SnsToken;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

@RequestMapping("/wechat")
@Controller
public class WechatController {
	private final static Logger logger=LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private WechatUserMappingRepository wechatUserMappingRepository;
		
	@Autowired
	private UserRandomCommentRepository userRandomCommentRepository;
		
	@Autowired
	private WechatEventService wechatEventService;
	
	@Autowired
	private UserScoredService userScoredService;
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired	
	private UserIndicatorService userIndicatorService;
	
	private SnsAPI snsAPI;
	
	
	@PostConstruct
	public void init(){
		snsAPI=new SnsAPI();
	}
	
	@RequestMapping("/wxcallback")
	public void wxcallback(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam String signature,@RequestParam String timestamp,
			@RequestParam String nonce,@RequestParam(required=false) String echostr,@RequestBody(required=false) String xml) throws Exception{
		String m_signature=SignatureUtil.generateEventMessageSignature(WeixinUtil.CALLBACK_TOKEN,timestamp,nonce);
		logger.info("calculate signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" echostr:"+echostr);
		logger.info("m_signature:"+m_signature);
		if(!m_signature.equals(signature)){
			return;
		}
		if(echostr!=null){
			response.getWriter().print(echostr);
			response.getWriter().flush();
		}
		if(xml!=null){
			EventMessage msg=XMLConverUtil.convertToObject(EventMessage.class, xml);			
			XMLMessage message=wechatEventService.handleEvent(msg);
			if(message!=null){
				String res=message.toXML();
				response.getWriter().print(res);
				response.getWriter().flush();
			}
		}
	}
	
	@RequestMapping("/kpiauth")
	public ModelAndView kpiauth(@RequestParam(required=false) String state,@RequestParam(required=false) String code) throws Exception{
		ModelAndView mav=null;
		if(state==null){
			String url="";//WeixinUtil.REDIRECT_BASE+"?";
			url=url+"appid="+WeixinUtil.APP_ID;
			url=url+"&redirect_uri="+"http://www.kpi365.com/wechat/kpiauth.html";
			url=url+"&response_type=code&scope=snsapi_base&state=STATE";
			url=url.replaceAll(":", "%3A");
			url=url.replaceAll("/", "%2F");
			url=WeixinUtil.REDIRECT_BASE+"?"+url;
			mav=new ModelAndView("redirect:"+url);
		}
		else{
			mav=new ModelAndView("wechat/kpiauth");
		}
		
		return mav;
	}
	
	@RequestMapping("/createmapping")
	public @ResponseBody String createmapping(@RequestParam String code,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		SnsToken token=snsAPI.oauth2AccessToken(WeixinUtil.APP_ID, WeixinUtil.APP_SECRET, code);
		if(token==null || token.getOpenid()==null){
			return "false";
		}
		WechatUserMapping wm=null;
		List<WechatUserMapping> wmlist=wechatUserMappingRepository.findByOpenId(token.getOpenid());
		if(wmlist.size()>0){
			wm=wmlist.get(0);
		}
		if(wm==null){
			wm=new WechatUserMapping();			
		}
		wm.setOpenId(token.getOpenid());
		wm.setUser(upd.getUser());
		wm.setCreated(new Date());
		wechatUserMappingRepository.save(wm);
		return "true";
	}
	

	
	@RequestMapping("/dailyboard")
	public void dailyboard(Model model,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
	    WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
		List<User> users=new ArrayList<User>();
		users.add(upd.getUser());
		List<DailyBoard> dblist=userScoredService.getDailyBoardByUsersAndWeekOfYear(users, wcm.getYearNum(), wcm.getWeekofyear());
		StaffDailyBoardModel sdbm=new StaffDailyBoardModel();
		sdbm.setCountryId(-1l);
		sdbm.setUserId(upd.getUser().getId());
		sdbm.setUserName(upd.getUser().getName());
		List<StaffDailyBoardItemModel> models=new ArrayList<StaffDailyBoardItemModel>();
		Calendar scal=wcm.getStartCal();
		Calendar ecal=wcm.getEndCal();
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		while(!scal.after(ecal)){
			final int tyear=scal.get(Calendar.YEAR);
			final int tmonth=scal.get(Calendar.MONTH)+1;
			final int tdate=scal.get(Calendar.DATE);
			StaffDailyBoardItemModel sdbi=new StaffDailyBoardItemModel();
			sdbi.setYearNum(tyear);
			sdbi.setMonthNum(tmonth);
			sdbi.setDayNum(tdate);
			sdbi.setUserId(upd.getUser().getId());
			boolean isEnabled=false;
			if(scal.after(cal)){
				isEnabled=true;
			}
			sdbi.setIsEditable(isEnabled);
			for(DailyBoard db:dblist){
				if(db.getYearNum().equals(tyear) && db.getMonthNum().equals(tmonth) && db.getDayNum().equals(tdate)){
					 sdbi.setId(db.getId());
					 sdbi.setTask(db.getTask());
					 sdbi.setTaskType(db.getTaskType());
					 break;
				}
			}
			models.add(sdbi);
			
			scal.add(Calendar.DATE, 1);
		}
		sdbm.setStaffDailyBoardItemModels(models);
		model.addAttribute("dbmodel", sdbm);
		model.addAttribute("typegroups", userScoredService.getTaskTypeGroups());
	}
	
	@RequestMapping("/randomcomment")
	public void randomcomment(Model model,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
		UserRandomComment urc=userScoredService.getCommentRandom(null, wcm.getYearNum(), wcm.getWeekofyear());
		if(urc!=null){
			model.addAttribute("currandom", urc);
		}
		model.addAttribute("enablerandom", true);
		model.addAttribute("randomcats", userScoredService.getRandomCategory());
	}
	
	@RequestMapping("/selfmark-detail")
	public void selfmarkdetail(Model model,@AuthenticationPrincipal UserPasswordDetails upd,@RequestParam(required=false) Long userId) throws Exception{
		Calendar cal=Calendar.getInstance();
		Calendar tmpcal=(Calendar) cal.clone();
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){//should rollback
			tmpcal.add(Calendar.DATE, -1);
		}
		
		
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String weekstring=sdf.format(wcm.getStartCal().getTime())+"至"+sdf.format(wcm.getEndCal().getTime());
		model.addAttribute("comment", userScoredService.getCommentByWeeklyOfYear(null,wcm.getYearNum(), wcm.getWeekofyear()));
		model.addAttribute("selfmarks", userScoredService.getSelfMarks(null, wcm.getStartCal().getTime(), wcm.getEndCal().getTime()));
		model.addAttribute("weekstring", weekstring);
		
	}
	
	@RequestMapping("/selfmark")
	public void selfmark(Model model,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		List<DailyMarkCategory> categories=userRestService.getDailyMarkCategories();
		List<Indicator> indicators=userIndicatorService.getUserIndicators(null);
		UserSelfMark selfmark=userScoredService.getSelfMark();
		
	
		
		model.addAttribute("categories", categories);
		//logger.debug("indicator size :"+indicators.size());
		model.addAttribute("indicators", indicators);
		model.addAttribute("mark", selfmark);
		
	}
	
	@RequestMapping("/weeklycomment")
	public void weeklycomment(Model model,@AuthenticationPrincipal UserPasswordDetails upd,Long userId) throws Exception{
		Calendar cal=Calendar.getInstance();
		Calendar tmpcal=(Calendar) cal.clone();
		int weekindex=tmpcal.get(Calendar.DAY_OF_WEEK);
		if(weekindex==Calendar.SUNDAY){//should rollback
			tmpcal.add(Calendar.DATE, -2);
		}
		else{
			tmpcal.add(Calendar.DATE, 6-weekindex);
		}
		User user=userRestService.getGroupUser(userId);
		
		User ouser=userRestService.getContextUser();
	
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
		
		model.addAttribute("comment", userScoredService.getCommentByWeeklyOfYear(user,wcm.getYearNum(), wcm.getWeekofyear()));
		//model.addAttribute("selfmarks", userScoredService.getSelfMarks(user, wcm.getStartCal().getTime(), wcm.getEndCal().getTime()));
		
		model.addAttribute("handleuser", user);
		model.addAttribute("usergroups", ouser.getOwnerGroups());
	}	
	
	//@RequestMapping("/teamreport")
	public void teamreport(@AuthenticationPrincipal UserPasswordDetails upd,Model model) throws Exception{
		User user=userRestService.getUserById(upd.getUser().getId());
		//staffService.countryDailyReport(user, model);
		//staffService.companyDailyReport(user, model);		
	}
	
	//@RequestMapping("/viewreport")
	public void viewreport(@RequestParam(required=false) String reportDate,@AuthenticationPrincipal UserPasswordDetails upd,Model model) throws Exception{
//		Calendar cal=Calendar.getInstance();
//		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		model.addAttribute("today", sdf.format(new Date()));
//		if(reportDate!=null){
//			Date cdate=sdf.parse(reportDate);
//			cal.setTime(cdate);
//		}
//		
//		List<CompanyDailyReport> companyDailyReports=companyDailyReportRepository.findByYearNumAndMonthNumAndDayNumAndCompany(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), upd.getUser().getCompany());
//		
//		model.addAttribute("companyReports", companyDailyReports);
//		
//		List<CountryDailyReport> countryDailyReports=countryDailyReportRepository.findByYearNumAndMonthNumAndDayNumAndCountryIn(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), countryRepository.findAll());
//		model.addAttribute("countryReports", countryDailyReports);
//		cal.add(Calendar.DATE, -1);
//		model.addAttribute("preday", sdf.format(cal.getTime()));
//		
//		cal.add(Calendar.DATE, 2);
//		if(!cal.after(Calendar.getInstance())){
//			model.addAttribute("postday", sdf.format(cal.getTime()));
//		}
//		
		
	}
	


}
