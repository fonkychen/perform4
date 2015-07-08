package cn.aolc.group.performance.controller.pc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aolc.group.performance.annotation.NavMenu;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.MonthlyIndicator;
import cn.aolc.group.performance.jpa.MonthlyIndicatorStatis;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.WeeklyComment;
import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.jpa.tenant.DailyMarkCategory;
import cn.aolc.group.performance.jpa.tenant.TaskTypeGroup;
import cn.aolc.group.performance.model.StaffDailyBoardGroupModel;
import cn.aolc.group.performance.model.StaffDailyBoardItemModel;
import cn.aolc.group.performance.model.StaffDailyBoardModel;
import cn.aolc.group.performance.model.WeeklyCalendarModel;
import cn.aolc.group.performance.security.UserPasswordDetails;
import cn.aolc.group.performance.service.rest.CompanyService;
import cn.aolc.group.performance.service.rest.UserIndicatorService;
import cn.aolc.group.performance.service.rest.UserRestService;
import cn.aolc.group.performance.service.rest.UserScoredService;
import cn.aolc.group.performance.service.rest.WorkStatusService;
import cn.aolc.group.performance.site.menu.MenuCategory;
import cn.aolc.group.performance.util.PerformanceUtil;

@Controller
@RequestMapping("/staff")
@Transactional(propagation=Propagation.REQUIRED)
public class StaffController {
	private final static Logger logger=LoggerFactory.getLogger(StaffController.class);
	
		
	@Autowired
	private UserScoredService userScoredService;
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserIndicatorService userIndicatorService;
	
	@Autowired
	private WorkStatusService workStatusService;
	
	@NavMenu(category = MenuCategory.SCORE_HERO, rankWeight = 3, role = RoleType.USER, value = "主管点评")
	@RequestMapping("/comment")
	public void comment(Model model,@AuthenticationPrincipal UserPasswordDetails upd ,@RequestParam(required=false) Integer yearNum,@RequestParam(required=false) Integer weekOfYear,@RequestParam(required=false) Long userId) throws Exception{
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
		
		Integer yearm=null,weekofyear=null;
		if(yearNum==null){
			yearm=tmpcal.get(Calendar.YEAR);
		}
		else{
			yearm=yearNum;
		}
		if(weekOfYear==null){			
			weekofyear=tmpcal.get(Calendar.WEEK_OF_YEAR);				
		}
		else{
			weekofyear=weekOfYear;
		}
		if(yearm == tmpcal.get(Calendar.YEAR) && weekofyear == tmpcal.get(Calendar.WEEK_OF_YEAR)){
			model.addAttribute("isEnabled", true);
		}
		if((yearm*100+weekofyear)<(tmpcal.get(Calendar.YEAR)*100+tmpcal.get(Calendar.WEEK_OF_YEAR))){
			model.addAttribute("hasNext", true);
		}
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar(yearm, weekofyear);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String weekstring=sdf.format(wcm.getStartCal().getTime())+"至"+sdf.format(wcm.getEndCal().getTime());
		
		model.addAttribute("comment", userScoredService.getCommentByWeeklyOfYear(user,wcm.getYearNum(), wcm.getWeekofyear()));
		model.addAttribute("selfmarks", userScoredService.getSelfMarks(user, wcm.getStartCal().getTime(), wcm.getEndCal().getTime()));
		model.addAttribute("weekstring", weekstring);
		model.addAttribute("preyear", wcm.getPreYearNum());
		model.addAttribute("preweekofyear", wcm.getPreWeekofyear());
		model.addAttribute("nextyear", wcm.getNextYearNum());
		model.addAttribute("nextweekofyear", wcm.getNextWeekofyear());
		model.addAttribute("handleuser", user);
		
	}

	@NavMenu(category = MenuCategory.SCORE_HERO, rankWeight = 2, role = RoleType.USER, value = "每日自评")
	@RequestMapping("/selfmark")
	public void selfmark(Model model) throws Exception{
		List<DailyMarkCategory> categories=userRestService.getDailyMarkCategories();
		List<Indicator> indicators=userIndicatorService.getUserIndicators(null);
		UserSelfMark selfmark=userScoredService.getSelfMark();
		
	
		WeeklyComment wc=userScoredService.getLatestComment(null);
		if(wc!=null){
			model.addAttribute("weeklycomment", wc);
		}
		
		model.addAttribute("categories", categories);
		//logger.debug("indicator size :"+indicators.size());
		model.addAttribute("indicators", indicators);
		model.addAttribute("mark", selfmark);
	}
	
	@RequestMapping("/selfmark/detail")
	public void selfmarkdetail(Model model,@RequestParam(required=false) Integer yearNum,@RequestParam(required=false) Integer weekOfYear) throws Exception{
		Calendar cal=Calendar.getInstance();
		Calendar tmpcal=(Calendar) cal.clone();
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){//should rollback
			tmpcal.add(Calendar.DATE, -1);
		}
		//int curweekofyear=tmpcal.get(Calendar.WEEK_OF_YEAR);		
		
		Integer yearm=null,weekofyear=null;
		if(yearNum==null){
			yearm=tmpcal.get(Calendar.YEAR);
		}
		else{
			yearm=yearNum;
		}
		if(weekOfYear==null){			
			weekofyear=tmpcal.get(Calendar.WEEK_OF_YEAR);				
		}
		else{
			weekofyear=weekOfYear;
		}
		
		
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar(yearm, weekofyear);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String weekstring=sdf.format(wcm.getStartCal().getTime())+"至"+sdf.format(wcm.getEndCal().getTime());
		model.addAttribute("comment", userScoredService.getCommentByWeeklyOfYear(null,wcm.getYearNum(), wcm.getWeekofyear()));
		model.addAttribute("selfmarks", userScoredService.getSelfMarks(null, wcm.getStartCal().getTime(), wcm.getEndCal().getTime()));
		model.addAttribute("weekstring", weekstring);
		model.addAttribute("preyear", wcm.getPreYearNum());
		model.addAttribute("preweekofyear", wcm.getPreWeekofyear());
		model.addAttribute("nextyear", wcm.getNextYearNum());
		model.addAttribute("nextweekofyear", wcm.getNextWeekofyear());
	}
	
	@NavMenu(category = MenuCategory.SCORE_HERO, rankWeight = 1, role = RoleType.USER, value = "公事榜")
	@RequestMapping("/dailyboard")
	public void dailyboard(Model model,@AuthenticationPrincipal UserPasswordDetails upd ,@RequestParam(required=false) Integer yearNum, @RequestParam(required=false) Integer weekOfYear) throws Exception{
		List<TaskTypeGroup> typeGroups=userScoredService.getTaskTypeGroups();
		model.addAttribute("typeGroups", typeGroups);
		Long cuid=-1l;
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		Calendar tmpcal=(Calendar) cal.clone();
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){//should rollback
			tmpcal.add(Calendar.DATE, -1);
		}
		int curweekofyear=tmpcal.get(Calendar.WEEK_OF_YEAR);		
		
		Integer yearm=null,weekofyear=null;
		if(yearNum==null){
			yearm=tmpcal.get(Calendar.YEAR);
		}
		else{
			yearm=yearNum;
		}
		if(weekOfYear==null){			
			weekofyear=tmpcal.get(Calendar.WEEK_OF_YEAR);				
		}
		else{
			weekofyear=weekOfYear;
		}
		
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar(yearm, weekofyear);
		List<User> users=null;
		if(upd.getUser().getCompany()!=null){
			Company company=companyService.getById(upd.getUser().getCompany().getId());
			users=company.getUsers();
			if(upd.getUser().getCountry()!=null){
				cuid=upd.getUser().getCountry().getId();
			}
			if(upd.getUser().getUserStatus().equals(UserStatus.Newbie)){
				cuid=-2l;
			}
		}
		else{
			users=new ArrayList<User>();
			users.add(upd.getUser());
		}
		List<DailyBoard> dblist=userScoredService.getDailyBoardByUsersAndWeekOfYear(users, yearm, weekofyear);
		

		
		Calendar scal=wcm.getStartCal();
		Calendar ecal=wcm.getEndCal();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String weekstring=sdf.format(scal.getTime())+"至"+sdf.format(ecal.getTime());
		HashMap<Long, StaffDailyBoardGroupModel> groupmap=new HashMap<Long, StaffDailyBoardGroupModel>();
		
		StaffDailyBoardGroupModel cmodel=null;
		while(!scal.after(ecal)){
			final int tyear=scal.get(Calendar.YEAR);
			final int tmonth=scal.get(Calendar.MONTH)+1;
			final int tdate=scal.get(Calendar.DATE);
			
			 for(User user:users){
		    	 Country country=user.getCountry();
				 Long cid=-1l;
				 if(country!=null){
				   cid=country.getId();
				 }
				 if(user.getUserStatus().equals(UserStatus.Newbie)){
					 cid=-2l;
				 }
				 StaffDailyBoardGroupModel sdbg=null;
				 sdbg=groupmap.get(cid);
				
				 
				 if(sdbg==null){
				   sdbg=new StaffDailyBoardGroupModel();
				   sdbg.setCountryId(cid);
				   sdbg.setCountryName(country==null?(cid.equals(-2l)?"新兵营":""):country.getName());
				   
				   sdbg.setStaffDailyBoardModels(new ArrayList<StaffDailyBoardModel>());
				   groupmap.put(cid, sdbg);
				 }
				 StaffDailyBoardModel sdbm=null;
				 for(StaffDailyBoardModel sdmodel:sdbg.getStaffDailyBoardModels()){
					 if(sdmodel.getUserId().equals(user.getId())){
						 sdbm=sdmodel;
					 }
				 }
				 
				 if(sdbm==null){
					 sdbm=new StaffDailyBoardModel();
					 sdbm.setCountryId(cid);
					 sdbm.setUserId(user.getId());
					 sdbm.setUserName(user.getName());
					 sdbm.setStaffDailyBoardItemModels(new ArrayList<StaffDailyBoardItemModel>());
					 sdbg.getStaffDailyBoardModels().add(sdbm);
				 }
				
				 List<StaffDailyBoardItemModel> modellist=null;
				 modellist=sdbm.getStaffDailyBoardItemModels();
//				 
//				 if(modellist==null){
//					 modellist=new ArrayList<StaffDailyBoardItemModel>();
//				 }
				 boolean isEnabled=false;
				 
				 boolean isCurrentUser=false;
				 if(user.getId().equals(upd.getUser().getId())){
					 isCurrentUser=true;
				 }
				 if(isCurrentUser && scal.after(cal)){//detect if iseditable
					isEnabled=true;					
				 }
				
				 
				 StaffDailyBoardItemModel sdbi=new StaffDailyBoardItemModel();
				 
				 sdbi.setYearNum(tyear);
				 sdbi.setMonthNum(tmonth);
				 sdbi.setDayNum(tdate);
				 sdbi.setUserId(user.getId());
				 sdbi.setIsEditable(isEnabled);
				 
				// logger.debug("year-month-day-user-enabled "+tyear+"-"+tmonth+"-"+tdate+"-"+user.getId()+"-"+isEnabled);
				 for(DailyBoard db:dblist){
					if(db.getUser()==null || db.getUser().getUserStatus().equals(UserStatus.Retired))continue;
					 if(db.getUser().equals(user) && db.getYearNum().equals(tyear) && db.getMonthNum().equals(tmonth) && db.getDayNum().equals(tdate)){
						 sdbi.setId(db.getId());
						 sdbi.setTask(db.getTask());
						 sdbi.setTaskType(db.getTaskType());
						 break;
					 }
				 }
				 
				 modellist.add(sdbi);
				
		     }
			
		
			scal.add(Calendar.DATE, 1);
		}
        cmodel=groupmap.get(cuid);
		
		model.addAttribute("cmodel", cmodel);
		
		groupmap.remove(cuid);
		
		model.addAttribute("modellist", groupmap.values());
		
		model.addAttribute("weekofyear", weekofyear);
		model.addAttribute("curweekofyear", curweekofyear);
		model.addAttribute("weekstring", weekstring);
		model.addAttribute("preyear", wcm.getPreYearNum());
		model.addAttribute("preweekofyear", wcm.getPreWeekofyear());
		model.addAttribute("nextyear", wcm.getNextYearNum());
		model.addAttribute("nextweekofyear", wcm.getNextWeekofyear());
	}
	

	
	@NavMenu(category=MenuCategory.SCORE_HERO, rankWeight = 4, role = RoleType.USER, value = "指标进度")
	@RequestMapping("/indicator")
	public void indicator(Model model) throws Exception{
		model.addAttribute("indicators", userIndicatorService.getUserIndicators(null));
		
		List<MonthlyIndicator> milist=userIndicatorService.getMonthlyIndicatorsByUser(null);
		model.addAttribute("milist", milist);
		
		List<MonthlyIndicatorStatis> mislist=userIndicatorService.getMonthlyIndicatorStatisByUser(null);
		model.addAttribute("mislist", mislist);
		
		model.addAttribute("map", userIndicatorService.getGrouplyMonthlyIndicatorByUser(null));
		
	}
	
	@RequestMapping("/manindicator")
	public void manindicator(@RequestParam(required=false) Long userId,Model model) throws Exception{
		User user=userRestService.getGroupUser(userId);
		List<MonthlyIndicator> milist=userIndicatorService.getMonthlyIndicatorsByUser(user);		
		model.addAttribute("milist", milist);
		List<Indicator> indicators=userIndicatorService.getUserIndicators(user);
		model.addAttribute("indicators", indicators);
		model.addAttribute("map", userIndicatorService.getGrouplyMonthlyIndicatorByUser(user));
		List<MonthlyIndicatorStatis> mislist=userIndicatorService.getMonthlyIndicatorStatisByUser(user);
		model.addAttribute("mislist", mislist);
		model.addAttribute("handleuser", user);
	}
	
	@RequestMapping("/dailyboard/overtime")
	public void overtime(Model model) throws Exception{
		//model.addAttribute("overtimes", workStatusService.get)
	}
	
	@RequestMapping("/random")
	public void random(Model model) throws Exception{
		UserRandomComment urc=userScoredService.getCommentRandom(null, null, null);
		model.addAttribute("random", urc);
		model.addAttribute("randomcats", userScoredService.getRandomCategory());
	}

}
