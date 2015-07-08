package cn.aolc.group.performance.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.CompanyDailyReport;
import cn.aolc.group.performance.jpa.CountryDailyReport;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.weixin.api.TemplateAPI;
import cn.aolc.group.performance.weixin.api.bean.NewUserNotifyTemplate;
import cn.aolc.group.performance.weixin.api.bean.ReportTemplateTask;
import cn.aolc.group.performance.weixin.api.bean.TemplateSendResult;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;
import cn.aolc.group.performance.weixin.util.WeixinUtil;

@Service
public class WechatMessageService {
	
	@Autowired
	private WechatUserMappingRepository wechatMappingRepository;
	
    private TemplateAPI templateAPI;
    
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@PostConstruct
	public void init(){
		templateAPI=new TemplateAPI();
	}
	
	public void sendCountryDailyReport(Company company,CountryDailyReport dailyReport){
		
		List<WechatUserMapping> wumlist=wechatMappingRepository.findAll();
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fviewreport.html%3FreportDate="+sdf.format(new Date())+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
		//logger.info("send country daily report "+company.getName() +" mapping list"+wumlist.size());
		for(WechatUserMapping wum:wumlist){
			if(wum.getUser().getCompany().equals(company) && !wum.getUser().getUserStatus().equals(UserStatus.Retired)){				
				ReportTemplateTask task=new ReportTemplateTask(wum.getOpenId(), dailyReport.getCountry().getName()+"日报已经生成了，快去看看吧！", dailyReport.getSummary()==null?"":dailyReport.getSummary(), url);
				TemplateSendResult result=
						templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN,task);
				//logger.info("result:"+result.getErrcode()+" "+result.getErrmsg());
			}
		}
	}
	
    public void sendCompanyDailyReport(CompanyDailyReport dailyReport){
    	List<WechatUserMapping> wumlist=wechatMappingRepository.findAll();
    	
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fviewreport.html%3FreportDate="+sdf.format(new Date())+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
		for(WechatUserMapping wum:wumlist){
			if(wum.getUser().getCompany().equals(dailyReport.getCompany())  && !wum.getUser().getUserStatus().equals(UserStatus.Retired)){
				
				ReportTemplateTask task=new ReportTemplateTask(wum.getOpenId(), "公司日报已经生成了，快去看看吧！", dailyReport.getSummary()==null?"":dailyReport.getSummary(), url);
				templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN,task);
			}
		}
	}
    
    public void sendNewUserNotify(User user){
    	List<WechatUserMapping> wumlist=wechatMappingRepository.findAll();
    	for(WechatUserMapping wum:wumlist){
    		if(wum.getUser().getCompany().equals(user.getCompany())  && !wum.getUser().getUserStatus().equals(UserStatus.Retired)){
    			NewUserNotifyTemplate task=new NewUserNotifyTemplate(wum.getOpenId(), user);
    			templateAPI.templateMessageSend(WeixinUtil.ACCESS_TOKEN, task);
    		}
    	}
    }

}
