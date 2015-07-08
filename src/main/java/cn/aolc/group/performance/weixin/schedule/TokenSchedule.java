package cn.aolc.group.performance.weixin.schedule;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.schedule.service.BaseScheduleService;
import cn.aolc.group.performance.weixin.util.WeixinUtil;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.Token;

@Component("tokenSchedule")
@Transactional(propagation=Propagation.REQUIRED)
public class TokenSchedule extends BaseScheduleService{
	
	private TokenAPI tokenAPI;
	
	@PostConstruct
	public void init(){
		tokenAPI=new TokenAPI();
	}
	
	@Scheduled(initialDelay=5000, fixedRate=1*1000*60*90)
	public void updateToken(){
		if(!isMaster())return;
		Token token=tokenAPI.token(WeixinUtil.APP_ID, WeixinUtil.APP_SECRET);
		WeixinUtil.ACCESS_TOKEN=token.getAccess_token();
	}

}
