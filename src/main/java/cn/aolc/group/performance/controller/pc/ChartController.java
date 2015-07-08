package cn.aolc.group.performance.controller.pc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.tenant.CountryRepository;
import cn.aolc.group.performance.dao.tenant.TaskTypeGroupRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.model.ActivityChartModel;
import cn.aolc.group.performance.model.BoardChartModel;
import cn.aolc.group.performance.model.IndicatorChartModel;
import cn.aolc.group.performance.model.ScoreChartModel;
import cn.aolc.group.performance.security.UserPasswordDetails;
import cn.aolc.group.performance.service.rest.ChartService;

@Controller
@RequestMapping("/chart")
public class ChartController extends BaseController{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private TaskTypeGroupRepository TaskTypeGroupRepository;
	
	@RequestMapping("/statis/userscore")
	public void userscore(Model model,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		Calendar cal1=Calendar.getInstance();
		cal1.add(Calendar.DATE, -7);
		Date startDate1=cal1.getTime();
		Calendar cal2=Calendar.getInstance();
		cal2.add(Calendar.DATE, -1);
		Date endDate1=cal2.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("startDate", sdf.format(startDate1));
		model.addAttribute("endDate", sdf.format(endDate1));
		model.addAttribute("countries", countryRepository.findAll());
		List<User> nocountryusers=userRepository.findByCompanyAndCountryAndUserStatusNot(upd.getUser().getCompany(), null, UserStatus.Retired);
		model.addAttribute("nocountryusers", nocountryusers);
		model.addAttribute("scoreTypes", ScoreType.values());
	}
	
	@RequestMapping("/requestscore")
	public @ResponseBody ScoreChartModel requserscore(@RequestBody(required=false) List<Long> userIds,@RequestParam(required=false) String startDate,@RequestParam(required=false) String endDate,@RequestParam(required=false) String groupBy,@RequestParam(required=false) String scoreType) throws Exception{
		List<User> users=null;
		Date startDate1,endDate1;
		
		if(userIds==null){
			users=userRepository.findAll();
		}
		else{
			users=new ArrayList<User>();
			for(Long userid:userIds){
				users.add(userRepository.findOne(userid));
			}
		}
		
		
		if(startDate!=null && endDate!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			startDate1=sdf.parse(startDate);
			endDate1=sdf.parse(endDate);
		}
		else{
			Calendar cal1=Calendar.getInstance();
			cal1.add(Calendar.DATE, -7);
			startDate1=cal1.getTime();
			Calendar cal2=Calendar.getInstance();
			cal2.add(Calendar.DATE, -1);
			endDate1=cal2.getTime();
		}
		
		if(scoreType==null){
			scoreType="daily";
		}
		
		return chartService.calScoreByDay(users, startDate1, endDate1);		
	}
	
	@RequestMapping("/statis/boardtask")
	public void boardtask(Model model,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		Calendar cal1=Calendar.getInstance();
		cal1.add(Calendar.DATE, -7);
		Date startDate1=cal1.getTime();
		Calendar cal2=Calendar.getInstance();
		cal2.add(Calendar.DATE, -1);
		Date endDate1=cal2.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("startDate", sdf.format(startDate1));
		model.addAttribute("endDate", sdf.format(endDate1));
		model.addAttribute("countries", countryRepository.findAll());
		List<User> nocountryusers=userRepository.findByCompanyAndCountryAndUserStatusNot(upd.getUser().getCompany(), null, UserStatus.Retired);
		model.addAttribute("nocountryusers", nocountryusers);
		model.addAttribute("typeGroups", TaskTypeGroupRepository.findAll());
		
	}
	
	@RequestMapping("/requestboard")
	public @ResponseBody BoardChartModel requestboard(@RequestBody(required=false) List<Long> userIds,@RequestParam(required=false) String startDate,@RequestParam(required=false) String endDate) throws Exception{
		List<User> users=null;
		Date startDate1,endDate1;
		
		if(userIds==null){
			users=userRepository.findAll();
		}
		else{
			users=new ArrayList<User>();
			for(Long userid:userIds){
				users.add(userRepository.findOne(userid));
			}
		}
		
		
		if(startDate!=null && endDate!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			startDate1=sdf.parse(startDate);
			endDate1=sdf.parse(endDate);
		}
		else{
			Calendar cal1=Calendar.getInstance();
			cal1.add(Calendar.DATE, -7);
			startDate1=cal1.getTime();
			Calendar cal2=Calendar.getInstance();
			cal2.add(Calendar.DATE, -1);
			endDate1=cal2.getTime();
		}
		
		return chartService.getBoardCount(users, startDate1, endDate1);

	}
	
	@RequestMapping("/statis/userindicator")
	public void userindicator(Model model,@AuthenticationPrincipal UserPasswordDetails upd,@RequestParam(required=false) Long userId) throws Exception{
		Calendar cal1=Calendar.getInstance();
		cal1.add(Calendar.DATE, -7);
		Date startDate1=cal1.getTime();
		Calendar cal2=Calendar.getInstance();
		cal2.add(Calendar.DATE, -1);
		Date endDate1=cal2.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("startDate", sdf.format(startDate1));
		model.addAttribute("endDate", sdf.format(endDate1));
		List<Country> countries=countryRepository.findAll();
		model.addAttribute("countries", countries);
		List<User> nocountryusers=userRepository.findByCompanyAndCountryAndUserStatusNot(upd.getUser().getCompany(), null, UserStatus.Retired);
		model.addAttribute("nocountryusers", nocountryusers);
		
		User user=null;
		if(userId!=null){
			user=userRepository.findOne(userId);
		}
		
		if(user==null){
			int i=0;
			int clength=countries.size();
			while(i<clength && user==null){
				Country country=countries.get(i);
				List<User> counUsers=country.getUsers();
				if(counUsers.size()>0){
					user=counUsers.get(0);
				}
				i++;
			}
			
			if(user==null && nocountryusers.size()>0){
			   	user=nocountryusers.get(0);
			}
			
		}
		
		if(user==null) throw new Exception("没有有效的用户");
		
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/requestindicator")
	public @ResponseBody IndicatorChartModel requestindicator(@RequestParam Long userId) throws Exception{
		User user=userRepository.findOne(userId);
		return chartService.getUserIndicator(user);
	}
	
	@RequestMapping("/statis/useractivity")
	public void useractivity(Model model,@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		Calendar cal1=Calendar.getInstance();
		cal1.add(Calendar.DATE, -7);
		Date startDate1=cal1.getTime();
		Calendar cal2=Calendar.getInstance();
		cal2.add(Calendar.DATE, -1);
		Date endDate1=cal2.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("startDate", sdf.format(startDate1));
		model.addAttribute("endDate", sdf.format(endDate1));
		model.addAttribute("countries", countryRepository.findAll());
		List<User> nocountryusers=userRepository.findByCompanyAndCountryAndUserStatusNot(upd.getUser().getCompany(), null, UserStatus.Retired);
		model.addAttribute("nocountryusers", nocountryusers);
		model.addAttribute("activitys", ChartService.ACTIVITY_SHOW_LIST);
	}
	
	@RequestMapping("/requestactivity")
	public @ResponseBody ActivityChartModel requestactivity(@RequestBody(required=false) List<Long> userIds,@RequestParam(required=false) String startDate,@RequestParam(required=false) String endDate) throws Exception{
		List<User> users=new ArrayList<User>();
		if(userIds!=null){
			for(Long userId:userIds){
				users.add(userRepository.findOne(userId));
			}
		}
		

		Date startDate1,endDate1;
		if(startDate!=null && endDate!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			startDate1=sdf.parse(startDate);
			endDate1=sdf.parse(endDate);
		}
		else{
			Calendar cal1=Calendar.getInstance();
			cal1.add(Calendar.DATE, -7);
			startDate1=cal1.getTime();
			Calendar cal2=Calendar.getInstance();
			cal2.add(Calendar.DATE, -1);
			endDate1=cal2.getTime();
		}
		
		return chartService.getUserActivity(users, startDate1, endDate1);
	}

}
