package cn.aolc.group.performance.weixin.service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.PopularHistoryRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.tenant.CountryRepository;
import cn.aolc.group.performance.jpa.AttendEvent;
import cn.aolc.group.performance.jpa.LuckyTable;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.PopularType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.service.rest.UserPopularService;
import cn.aolc.group.performance.service.rest.UserWealthService;
import cn.aolc.group.performance.util.exception.AlreadyAttendException;
import cn.aolc.group.performance.util.exception.PopularLimitException;
import cn.aolc.group.performance.util.exception.PopularRepeatException;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.util.WeixinUtil;
import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;

@Service
public class WechatEventService { 
	
	@Autowired
	private WechatUserMappingRepository wechatUserMappingRepository;

    @Autowired
	private UserWealthService userWealthService;
    
    @Autowired
    private UserPopularService userPopularService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private PopularHistoryRepository popularHistoryRepository; 
	
	@Value("${server.proto}://${server.host}")
	private String server;
	
	public XMLMessage handleEvent(EventMessage msg) throws Exception{
		
		String content="";
		if(msg.getMsgType()!=null && msg.getMsgType().equals("event") && msg.getEvent()!=null && msg.getEvent().equals("CLICK")){
			String openid=msg.getFromUserName();
			WechatUserMapping wm=null;
			List<WechatUserMapping> wmlist=wechatUserMappingRepository.findByOpenId(openid);
			if(wmlist.size()>0){
				wm=wmlist.get(0);
			}
			if(wm==null){
				content="欢迎使用KPI互动社区，您还没有绑定KPI账号，<a href=\""+server+"/wechat/kpiauth.html?err=1"+"\">点击这里，立即绑定!</a>";
			}
			Calendar cal=Calendar.getInstance();
			String eventKey=msg.getEventKey();
			if(wm!=null && eventKey!=null){
				//logger.info("event key is :"+eventKey);
				
				User user=wm.getUser();
				//inject user security principal
				
				if(eventKey.equals(WeixinUtil.PERSONAL_WEALTH_KEY)){
					//logger.info("go personal wealth");
					List<User> userlist=userRepository.findByCompanyAndUserStatusNotOrderByUserCoinsDesc(user.getCompany(), UserStatus.Retired);
					content="您当前拥有"+user.getUserCoins()+"个UB，排名第"+(userlist.indexOf(user)+1);
				}
				else if(eventKey.equals(WeixinUtil.COUNTRY_WEALTH_KEY)){
					List<Country> countrylist=countryRepository.findByTenantIdOrderByWealthDesc(user.getCompany().getCode());
					content=user.getCountry()==null?"您当前没有国家属性":"您所在的国家是"+user.getCountry().getName()+", 当前财富值为:"+user.getCountry().getWealth()+"，排名第"+(countrylist.indexOf(user.getCountry())+1);
				}
				else if(eventKey.equals(WeixinUtil.MY_POPULAR_KEY)){
					//logger.info("go personal popular");
					int fp=popularHistoryRepository.countByUserAndYearNumAndPopularType(user, cal.get(Calendar.YEAR), PopularType.Flower);
					int ep=popularHistoryRepository.countByUserAndYearNumAndPopularType(user, cal.get(Calendar.YEAR), PopularType.Egg);
					List<User> userlist=userRepository.findByCompanyAndUserStatusNotOrderByUserPopularDesc(user.getCompany(), UserStatus.Retired);
					content="您当前拥有"+fp+"朵鲜花，"+ep+"颗鸡蛋，共计人气值"+user.getUserPopular()+"，排名第"+(userlist.indexOf(user)+1);
				}
				else if(eventKey.equals(WeixinUtil.ATTEND_WEALTH_KEY)){
					try{
						AttendEvent ae=userWealthService.attend(user, false);
						Long coin=ae.getCoinNum();
						if(coin>0){
							content="恭喜您，签到获得"+coin+"个UB";
						}
						else{
							content="倒霉!签到得到"+coin+"个UB";
						}
						
					}catch(AlreadyAttendException ex){
						AttendEvent ae=userWealthService.getAttend(user);
						content="今天已经签到了，获得了"+ae.getCoinNum()+"个UB，明天再来吧";						
					}
				}
				else if(eventKey.equals(WeixinUtil.MALL_WEALTH_KEY)){
					try{
						LuckyTable lt=userWealthService.lottery(user);
						
						Long coin=lt.getCoinNum();
						content="幸运转转转，您抽奖获得"+coin+"个UB";
						
					}catch(cn.aolc.group.performance.util.exception.AlreadyLotteryException ex){
						LuckyTable lt=userWealthService.getLuckyTable(user);
						content="今天已经抽过奖了，获得了"+lt.getCoinNum()+"个UB，明天再来吧";
						
					}
				}
				
				else if(eventKey.equals(WeixinUtil.FLOWER_POPULAR_KEY)){
					List<User> userlist=userRepository.findByCompanyAndUserStatusNotOrderByCountryAsc(user.getCompany(), UserStatus.Retired);
					User taruser=user;
					do{
						Random rand=new Random();
						int index=rand.nextInt(userlist.size());
						taruser=userlist.get(index);
					}while(userlist.size()>1 && taruser.equals(user));
					
					
					try{
						userPopularService.favorUser(taruser.getId(), 0,user);
								
						content="缘分就是这样莫名其妙，您对 "+taruser.getName()+" 偷偷献上一朵花。";
					}catch(cn.aolc.group.performance.util.exception.PopularRepeatException ex1){//已经操作过
						content="因缘巧合又是Ta，再试一次吧！";
					}catch(PopularLimitException ex2){//超过3次
						content="三次机会已用光，明天再来吧！";
					}catch(Exception ex){//
						
					}
				}
				else if(eventKey.equals(WeixinUtil.EGG_POPULAR_KEY)){
					List<User> userlist=userRepository.findByCompanyAndUserStatusNotOrderByCountryAsc(user.getCompany(), UserStatus.Retired);
					User taruser=user;
					do{
						Random rand=new Random();
						int index=rand.nextInt(userlist.size());
						taruser=userlist.get(index);
					}while(userlist.size()>1 && taruser.equals(user));
					
					
					try{
						userPopularService.favorUser(taruser.getId(), 1, user);
						content="这一定是命运的指引，您对 "+taruser.getName()+" 偷偷丢了一颗蛋";
					}catch(PopularRepeatException ex1){//已经操作过
						content="因缘巧合又是Ta，再试一次吧！";
					}catch(PopularLimitException ex2){//超过3次
						content="三次机会已用光，明天再来吧！";
					}catch(Exception ex){//
						
					}
				}
			}
		}
		
		XMLMessage text=new XMLTextMessage(msg.getFromUserName(), msg.getToUserName(), content);
		return text;
	}

}
