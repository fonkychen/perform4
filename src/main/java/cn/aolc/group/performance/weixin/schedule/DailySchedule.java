package cn.aolc.group.performance.weixin.schedule;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.CompanyRepository;
import cn.aolc.group.performance.dao.CountryDailyReportRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.schedule.service.BaseScheduleService;
import cn.aolc.group.performance.service.rest.UserScoredService;
import cn.aolc.group.performance.weixin.api.TemplateAPI;
import cn.aolc.group.performance.weixin.api.bean.TemplateSendResult;
import cn.aolc.group.performance.weixin.api.bean.SelfMarkTemplateTask;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.service.WechatMessageService;
import cn.aolc.group.performance.weixin.util.WeixinUtil;

@Component("weixinDailySchedule")
@Transactional(propagation=Propagation.REQUIRED)
public class DailySchedule extends BaseScheduleService{
	
	@Autowired
	private WechatUserMappingRepository wechatMappingRepository;
	
	@Autowired
	private UserScoredService userScoredService;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository; 
	
	@Autowired
	private CountryDailyReportRepository countryDailyReportRepository;
	
	@Autowired
	private WechatMessageService wechatMessageService;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	private TemplateAPI templateAPI;
	
	@PostConstruct
	public void init(){
		templateAPI=new TemplateAPI();
	}
	
	@Scheduled(cron="0 25 20 * * ?")
	public void notifyUnHandleSelfmark(){
		if(!isMaster())return;
		//logger.info("notifyUnHandleSelfmark");
		List<WechatUserMapping> wumlist=wechatMappingRepository.findAll();
		//logger.info("mapping size "+wumlist.size());
		for(WechatUserMapping wum:wumlist){
			if(checkIfNeedNotify(wum.getUser())){
				TemplateSendResult result=templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN, new SelfMarkTemplateTask(wum.getOpenId()));
				
			}
		}				
	} 
	

	private boolean checkIfNeedNotify(User user){
		if(user.getUserStatus().equals(UserStatus.Retired))return false;
		Calendar cal=Calendar.getInstance();
		int yearm=cal.get(Calendar.YEAR);
		int monthm=cal.get(Calendar.MONTH)+1;
		int daym=cal.get(Calendar.DATE);
		
		boolean isenabled=userScoredService.isEnabledForUser(yearm, monthm, daym, user);
		if(isenabled){
			List<UserSelfMark> usmlist=userSelfMarkRepository.findByUserAndYearNumAndMonthNumAndDayNum(user, yearm, monthm, daym);
			if(usmlist.size()<=0){
				return true;
			}
		}
		return false;
	}

}
