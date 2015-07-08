package cn.aolc.group.performance.service.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import cn.aolc.group.performance.dao.ResetPassCodeRepository;
import cn.aolc.group.performance.jpa.ResetPassCode;
import cn.aolc.group.performance.jpa.User;

@Component
public class KpiMailSenderService {
	
	@Autowired
	private ResetPassCodeRepository resetPassCodeRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Value("${mail.username}")
	private String mailusername;
	

	@Value("${server.proto}://${server.host}:${server.port}")
	private String server;
	
	@Async
	public void sendLockNotify(final String name,final String mailAddress,final String urlcontent,final String urlhref){
		//logger.info("try to sendLockNotify");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage arg0) throws Exception {
				MimeMessageHelper mmh = new MimeMessageHelper(arg0,true,"UTF-8");
		        mmh.setTo(mailAddress);
		        mmh.setFrom(mailusername);
				Map model = new HashMap();
				model.put("username", name);
				//model.put("url.name", urlcontent);
				model.put("linkhref", urlhref);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日 HH:mm");
				model.put("message","您在"+sdf.format(new Date())+ " 账号因连续输错5次被锁定");
				String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail.vm", "UTF-8", model);
				
				mmh.setSubject("KPI账号锁定通知");
				mmh.setText(text,true);
				
			}
			
		};
		try{
			mailSender.send(preparator);
		}catch(MailException me){
			//logger.info("send mail exception", me);
		}
		
		
	}
	
	@Async
	public void sendPassReset(final User user){
		ResetPassCode rpc=new ResetPassCode();
		rpc.setUser(user);
		Calendar cal=Calendar.getInstance();
		rpc.setCreated(cal.getTime());
		cal.add(Calendar.DATE, 1);
		rpc.setExpired(cal.getTime());
		rpc.setClicked(false);
		rpc.setCode(generateCode());
		resetPassCodeRepository.save(rpc);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日 HH:mm");
		final String urlhref=server+"/common/resetpass/"+rpc.getCode()+".html";
		final String datetext="请在"+sdf.format(rpc.getExpired())+ "之前通过该链接重置密码";
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage arg0) throws Exception {
				MimeMessageHelper mmh = new MimeMessageHelper(arg0,true,"UTF-8");
		        mmh.setTo(user.getEmailAddress());
		        mmh.setFrom(mailusername);
				Map model = new HashMap();
				model.put("username", user.getName());
				//model.put("url.name", urlcontent);
				model.put("linkhref", urlhref);
				
				model.put("message",datetext);
				String text=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail.vm", "UTF-8", model);
				
				mmh.setSubject("KPI账号重置邮件");
				mmh.setText(text,true);
			}
			
		};
		
		try{
			mailSender.send(preparator);
		}catch(MailException me){
			//logger.info("send mail exception", me);
		}
	}
	
	private String generateCode(){
		return RandomStringUtils.randomAlphabetic(24);
	}

}
