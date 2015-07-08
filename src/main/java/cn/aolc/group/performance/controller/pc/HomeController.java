package cn.aolc.group.performance.controller.pc;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aolc.group.performance.jpa.AttendEvent;
import cn.aolc.group.performance.jpa.MonthlyCountryStatis;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.jpa.tenant.WeiboTopic;
import cn.aolc.group.performance.service.rest.CountryService;
import cn.aolc.group.performance.service.rest.MessageService;
import cn.aolc.group.performance.service.rest.TitleService;
import cn.aolc.group.performance.service.rest.UserPopularService;
import cn.aolc.group.performance.service.rest.UserRestService;
import cn.aolc.group.performance.service.rest.UserWealthService;
import cn.aolc.group.performance.service.rest.WeiboRestService;
import cn.aolc.group.performance.util.LogUtil;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired	
	private LogUtil logUtil;	
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private UserWealthService userWealthService;
	
	@Autowired
	private WeiboRestService weiboRestService;
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired
	private UserPopularService userPopularService;
	
	@Autowired
	private MessageService messageService;

	@RequestMapping("/index")
	public void index(Model model) throws Exception{		
		logUtil.debug("homecontroller index");
		List<MonthlyCountryStatis> mcslist=countryService.getcountrystatis(null, null);
		if(mcslist.size()>0){
			model.addAttribute("countrystatis", mcslist.get(0));
		}
		
		Title title=titleService.getNextTitle(null);
		if(title!=null){
			model.addAttribute("nexttitle",title);
		}
		
		AttendEvent attendEvent=userWealthService.getAttend(null);
		if(attendEvent!=null){
			model.addAttribute("attendevent", attendEvent);
		}
		
		Page<WeiboTopic> wpage1=weiboRestService.getTopicOrderByCreated(1, 2);
		
		Page<WeiboTopic> wpage2=weiboRestService.getTopicOrderByDegree(1, 2);
		
		model.addAttribute("newtopics", wpage1.getContent());
		model.addAttribute("hottopics", wpage2.getContent());
		
		model.addAttribute("scorepage", userRestService.userRank(null, "userScored", "desc", 1, 10));
		model.addAttribute("coinpage", userRestService.userRank(null, "userCoins", "desc", 1, 10));
		model.addAttribute("popularpage", userRestService.userRank(null, "userPopular", "desc", 1, 10));
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		model.addAttribute("hisrank", userRestService.getDailyRank(null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), "score"));
		
		model.addAttribute("pmessages", messageService.getMessages(null, 1, "desc", 8));
		
	}
	
	@RequestMapping("/toprank/{type}")
	public void toprank(Model model,@RequestParam(required=false) String order,@RequestParam(required=false) Integer page,@PathVariable String type) throws Exception{
		if(page==null){
			page=1;
		}
		if(order!=null && order.equals("asc")){
			order="asc";
		}
		else{
			order="desc";
		}
		
		if(type==null){
			type="userScored";
		}
		Page<User> ret=userRestService.userRank(null, type, order, page, 30);
		model.addAttribute("page", ret);
		model.addAttribute("type", type);
		model.addAttribute("order", order);
		model.addAttribute("popularlist", userPopularService.getScatter(ret.getContent(), null));
	}	

}
