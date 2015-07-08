package cn.aolc.group.performance.weixin.schedule;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.tenant.UserGroupRepository;
import cn.aolc.group.performance.jpa.tenant.UserGroup;
import cn.aolc.group.performance.schedule.service.BaseScheduleService;
import cn.aolc.group.performance.weixin.api.TemplateAPI;
import cn.aolc.group.performance.weixin.api.bean.WeeklyTemplateTask;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.util.WeixinUtil;

@Component("weixinWeeklySchedule")
@Transactional(propagation=Propagation.REQUIRED)
public class WeeklySchedule extends BaseScheduleService{
	
//	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private WechatUserMappingRepository wechatMappingRepository;
	
	private TemplateAPI templateAPI;
	
	@PostConstruct
	public void init(){
		templateAPI=new TemplateAPI();
	}
	
	@Scheduled(cron="0 31 18 ? * FRI")
	public void notityWeeklyComment() throws Exception{
		if(!isMaster())return;
		List<UserGroup> userGroups=userGroupRepository.findAll();
		
		for(UserGroup ug:userGroups){
			if(ug.getOwner()==null)continue;
			List<WechatUserMapping> wmlist=wechatMappingRepository.findByUser(ug.getOwner());
			if(wmlist.size()>0){
				templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN, new WeeklyTemplateTask(wmlist.get(0).getOpenId()));
			}
		}
		
	}
	

}
