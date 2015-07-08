package cn.aolc.group.performance.controller.pc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import cn.aolc.group.performance.dao.ResetPassCodeRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.AchieveMilestone;
import cn.aolc.group.performance.jpa.ResetPassCode;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserIcon;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.MallProduct;
import cn.aolc.group.performance.service.rest.KpiMailSenderService;
import cn.aolc.group.performance.service.rest.MallService;
import cn.aolc.group.performance.service.rest.UserAchieveService;
import cn.aolc.group.performance.service.rest.UserRestService;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired
	private MallService mallService;
	
	@Autowired
	private ResetPassCodeRepository resetPassCodeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private KpiMailSenderService mailService;
	
	@Autowired	
	private UserAchieveService userAchieveService;
	
    private Producer kaptchaProducer = null;
	
	private String sessionKeyValue = null;

	private String sessionKeyDateValue = null;
	
	@PostConstruct
	public void init(){
		Properties props=new Properties();
		props.put("kaptcha.border", "no");
		props.put("kaptcha.textproducer.font.color", "black");
		props.put("kaptcha.textproducer.char.space", "3");
		props.put("kaptcha.image.width", "120");
		props.put("kaptcha.image.height", "40");
		//props.put("kaptcha.producer.impl", "com.google.code.kaptcha.impl.NoNoise");
		Config config = new Config(props);
		this.kaptchaProducer = config.getProducerImpl();
		this.sessionKeyValue = config.getSessionKey();
		this.sessionKeyDateValue = config.getSessionDate();
		
		
	}
	
	@RequestMapping("/login")
	public void login(){
		
	}
	
	@RequestMapping("/usericon")
	public void usericon(@RequestParam Long iconId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		UserIcon ui=userRestService.getIcon(iconId);
		if(ui!=null){
			try{
				response.setContentType("image/png");
				ServletOutputStream outputStream = response.getOutputStream();
		        outputStream.write(ui.getPicture());
		        outputStream.flush();
		        outputStream.close();
				
			}catch(IOException ie){
				
			}
			
		}
	}
	
	@RequestMapping("/producticon")
	public void producticon(@RequestParam Long productId,HttpServletRequest request ,HttpServletResponse response) throws Exception{
		MallProduct mp=mallService.getProduct(productId);
		if(mp!=null && mp.getIcon()!=null){
			try{
				response.setContentType("image/png");
				ServletOutputStream outputStream = response.getOutputStream();
		        outputStream.write(mp.getIcon());
		        outputStream.flush();
		        outputStream.close();
				
			}catch(IOException ie){
				
			}			
		}
	}
	
	@RequestMapping("/passreset")
	public void passreset() throws Exception{
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/getverifycode")
	public void getverifycode(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		resp.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		resp.setHeader("Pragma", "no-cache");

		// return a jpeg
		resp.setContentType("image/jpeg");
		
		String capText = this.kaptchaProducer.createText();

		// store the text in the session
		req.getSession().setAttribute(this.sessionKeyValue, capText);

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		req.getSession().setAttribute(this.sessionKeyDateValue, new Date());

		// create the image with the text
		BufferedImage bi = this.kaptchaProducer.createImage(capText);

		ServletOutputStream out = resp.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
	}
	
	@RequestMapping(value="/resetpass/{resetcode}",method=RequestMethod.GET)
	public String resetpass(Model model, @PathVariable String resetcode) throws Exception{
		ResetPassCode rpc=null;
		List<ResetPassCode> rpcl=resetPassCodeRepository.findByCode(resetcode);		
		if(rpcl.size()>0){
			rpc=rpcl.get(0);
		}
		if(rpc==null) throw new Exception("无效的链接地址");
		if((rpc.getClicked()!=null && rpc.getClicked()) || (rpc.getExpired()!=null && rpc.getExpired().before(new Date()))) throw new Exception("链接地址已经失效");
//		rpc.setClicked(true);
//		resetPassCodeRepository.save(rpc);
		
		model.addAttribute("code", resetcode);
		return "common/resetpass";
		
	}
	
	@RequestMapping(value="/changepass",method=RequestMethod.POST)
	public @ResponseBody String changepass(@RequestParam String code,@RequestParam String password) throws Exception{
		ResetPassCode rpc=null;
		List<ResetPassCode> rpcl=resetPassCodeRepository.findByCode(code);		
		if(rpcl.size()>0){
			rpc=rpcl.get(0);
		}
		if(rpc==null) throw new Exception("无效的请求");
		if((rpc.getClicked()!=null && rpc.getClicked()) || (rpc.getExpired()!=null && rpc.getExpired().before(new Date()))) throw new Exception("无效的请求");
		
		User user=rpc.getUser();
		if(user.getUserStatus().equals(UserStatus.Retired)) return "disabled";
		rpc.setClicked(true);
		ShaPasswordEncoder spe=new ShaPasswordEncoder(256);
		user.setPassword(spe.encodePassword(password, null));
		if(user.getUserStatus().equals(UserStatus.Locked))user.setUserStatus(UserStatus.Onduty);
		resetPassCodeRepository.save(rpc);
		userRepository.save(user);
		
		return "ok";
	}
	
	@RequestMapping("/resetpass2")
	public void resetpass2() throws Exception{
		
	}
	
	@RequestMapping("/findpass")
	public void findpass() throws Exception{
		
	}
	
	@RequestMapping("/findpass2")
	public void findpass2() throws Exception{
		
	}
	
	@RequestMapping(value="/checkandreset",method=RequestMethod.POST)
	public @ResponseBody String checkandreset(HttpServletRequest req,@RequestParam String mail,@RequestParam String captcha){
		
		List<User> userlist= userRepository.findByEmailAddressIgnoreCase(mail);
		if(userlist.size()<=0) return "err1";
		String captext=req.getSession().getAttribute(this.sessionKeyValue)==null?"":req.getSession().getAttribute(this.sessionKeyValue).toString();
		if(!captext.equals(captcha)) return "err2";
		
		
		mailService.sendPassReset(userlist.get(0));
		return "ok";
	}
	
	@RequestMapping("/achieveicon/{id}")
	public void achieveIcon(@PathVariable Long id,HttpServletRequest request ,HttpServletResponse response) throws Exception{
		AchieveMilestone milestone=userAchieveService.get(id);
		try{
			response.setContentType("image/png");
			ServletOutputStream outputStream = response.getOutputStream();
	        //outputStream.write(mp.getIcon());
	        outputStream.flush();
	        outputStream.close();
			
		}catch(IOException ie){
			
		}		
	}
	

}
