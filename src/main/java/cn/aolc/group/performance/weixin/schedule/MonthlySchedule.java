package cn.aolc.group.performance.weixin.schedule;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.tenant.UserGroupRepository;
import cn.aolc.group.performance.jpa.MonthlyIndicator;
import cn.aolc.group.performance.jpa.tenant.UserGroup;
import cn.aolc.group.performance.schedule.service.BaseScheduleService;
import cn.aolc.group.performance.weixin.api.TemplateAPI;
import cn.aolc.group.performance.weixin.api.bean.MonthlyIndicatorTemplateTask;
import cn.aolc.group.performance.weixin.api.bean.WeeklyTemplateTask;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.util.WeixinUtil;

@Component("weixinMonthlySchedule")
@Transactional(propagation=Propagation.REQUIRED)
public class MonthlySchedule extends BaseScheduleService{
	
	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private WechatUserMappingRepository wechatMappingRepository;
	
	private TemplateAPI templateAPI;
	
	@PostConstruct
	public void init(){
		templateAPI=new TemplateAPI();
	}
	
	@Scheduled(cron="0 31 19 ? * ?")
	public void notityIndicator() throws Exception{
		if(!isMaster())return;
		Calendar cal=Calendar.getInstance();
		int day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int cday=cal.get(Calendar.DAY_OF_MONTH);
        if(!(day==cday || day-cday==2)) return;
        
        List<UserGroup> userGroups=userGroupRepository.findAll();
		
		for(UserGroup ug:userGroups){
			if(ug.getOwner()==null)continue;
			List<WechatUserMapping> wmlist=wechatMappingRepository.findByUser(ug.getOwner());
			if(wmlist.size()>0){
				templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN, new MonthlyIndicatorTemplateTask(wmlist.get(0).getOpenId()));
			}
		}

	}

}
