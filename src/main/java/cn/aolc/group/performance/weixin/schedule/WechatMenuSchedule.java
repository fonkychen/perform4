package cn.aolc.group.performance.weixin.schedule;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.schedule.service.BaseScheduleService;
import cn.aolc.group.performance.weixin.util.WeixinUtil;
import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.MenuButtons;
import weixin.popular.bean.MenuButtons.Button;

@Component("wechatMenuSchedule")
@Transactional(propagation=Propagation.REQUIRED) 
public class WechatMenuSchedule extends BaseScheduleService{
	private MenuAPI menuAPI;
	
//	@Value("${server.proto}://${server.host}:${server.port}")
//	private String server;
	
	@Value("${server.proto}")
	private String proto;
	
	@Value("${server.host}")
	private String host;
	
	@Value("${server.port}")
	private String port;
	
	
	
	@PostConstruct
	public void init(){
		menuAPI=new MenuAPI();
	}
	
	@Scheduled(initialDelay=1000*30, fixedRate=5*1000*60*60*24)//update every five days
	public void createMenu(){
		if(!isMaster())return;
		MenuButtons mb=new MenuButtons();
		Button[] buttons=new Button[3];
		Button button1=new Button();//绩效英雄
		//button1.setKey(key);
		button1.setName("绩效英雄");
		//button1.setType("view");
		//button1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri="+proto+"%3A%2F%2F"+host+"%2Fwechat%2Fselfmark-detail.html&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
		
		List<Button> btnlist1=new ArrayList<MenuButtons.Button>();
		Button btn11=new Button();
		btn11.setType("view");
		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri="+proto+"%3A%2F%2F"+host+"%2Fwechat%2Fselfmark-detail.html&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
		btn11.setName("绩效信息");
		btnlist1.add(btn11);
		
		Button btn12=new Button();
		btn12.setType("view");
		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri="+proto+"%3A%2F%2F"+host+"%2Fwechat%2Fviewreport.html&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect");
		btn12.setName("团队日报");
		btnlist1.add(btn12);
		button1.setSub_button(btnlist1);
		
		Button button2=new Button();
		button2.setName("财富英雄");
		List<Button> btnlist2=new ArrayList<MenuButtons.Button>();
		Button btn21=new Button();
		btn21.setKey(WeixinUtil.PERSONAL_WEALTH_KEY);
		btn21.setName("个人财富");
		btn21.setType("click");
		btnlist2.add(btn21);
		
		Button btn22=new Button();
		btn22.setKey(WeixinUtil.COUNTRY_WEALTH_KEY);
		btn22.setName("国家财富");
		btn22.setType("click");
		btnlist2.add(btn22);		
		
		Button btn23=new Button();
		btn23.setKey(WeixinUtil.ATTEND_WEALTH_KEY);
		btn23.setName("每日签到");
		btn23.setType("click");
		btnlist2.add(btn23);		
		
		Button btn24=new Button();
		btn24.setKey(WeixinUtil.MALL_WEALTH_KEY);
		btn24.setName("商城抽奖");
		btn24.setType("click");
		btnlist2.add(btn24);
		
		button2.setSub_button(btnlist2);
		
		Button button3=new Button();		
		button3.setName("人气英雄");
		List<Button> btnlist3=new ArrayList<MenuButtons.Button>();
		
		Button btn31=new Button();
		btn31.setKey(WeixinUtil.MY_POPULAR_KEY);
		btn31.setName("我的人气");
		btn31.setType("click");
		btnlist3.add(btn31);
		
		Button btn32=new Button();
		btn32.setKey(WeixinUtil.FLOWER_POPULAR_KEY);
		btn32.setName("随机送花");
		btn32.setType("click");
		btnlist3.add(btn32);
		
		Button btn33=new Button();
		btn33.setKey(WeixinUtil.EGG_POPULAR_KEY);
		btn33.setName("随机砸蛋");
		btn33.setType("click");
		btnlist3.add(btn33);
		
		button3.setSub_button(btnlist3);
		
		buttons[0]=button1;
		buttons[1]=button2;
		buttons[2]=button3;
		
		mb.setButton(buttons);
		BaseResult res=menuAPI.menuCreate(WeixinUtil.ACCESS_TOKEN, mb);
	}

}
