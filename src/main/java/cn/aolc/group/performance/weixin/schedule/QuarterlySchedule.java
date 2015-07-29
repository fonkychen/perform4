package cn.aolc.group.performance.weixin.schedule;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.QuarterlyWealthBonusRepository;
import cn.aolc.group.performance.jpa.QuarterlyWealthBonus;
import cn.aolc.group.performance.schedule.service.BaseScheduleService;
import cn.aolc.group.performance.weixin.api.TemplateAPI;
import cn.aolc.group.performance.weixin.api.bean.UserCoinBonusTemplateTask;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.util.WeixinUtil;

@Component("weixinQuarterlySchedule")
@Transactional(propagation=Propagation.REQUIRED)
public class QuarterlySchedule extends BaseScheduleService{
	@Autowired
	private QuarterlyWealthBonusRepository quarterlyWealthBonusRepository;
	
	@Autowired
	private WechatUserMappingRepository wechatMappingRepository;
	
	private TemplateAPI templateAPI;
	
	@PostConstruct
	public void init(){
		templateAPI=new TemplateAPI();
	}
	
	@Scheduled(cron="0 55 11 ? * ?")
	public void notifyQuarterlyBonus() throws Exception{
		if(!isMaster())return;
		Calendar cal=Calendar.getInstance();
		if(cal.get(Calendar.DATE)!=3) return;
		
		cal.add(Calendar.MONTH,-1);		
		int month=cal.get(Calendar.MONTH)+1;
		if(month %3 !=0) return;
		
		int year=cal.get(Calendar.YEAR);
		
		List<QuarterlyWealthBonus> qwlist=quarterlyWealthBonusRepository.findByYearNumAndQuarter(year, month/3);
		for(QuarterlyWealthBonus qw:qwlist){
			List<WechatUserMapping> wmlist=wechatMappingRepository.findByUser(qw.getUser());
			if(wmlist.size()>0){
				WechatUserMapping wm=wmlist.get(0);
				templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN, new UserCoinBonusTemplateTask(wm.getOpenId(), qw.getCoinNum()));
			}
		}
	}

}
