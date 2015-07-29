package cn.aolc.group.performance.controller.pc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.aolc.group.performance.security.UserPasswordDetails;
import cn.aolc.group.performance.service.rest.UserRestService;
import cn.aolc.group.performance.util.LogUtil;

@ControllerAdvice(basePackages={"cn.aolc.group.performance.controller.pc","cn.aolc.group.performance.weixin.controller"})
public class BaseController {
	
	protected Logger logUtil=LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private UserRestService userRestService;
	
	@ExceptionHandler(Exception.class)
	public ModelAndView error(HttpServletRequest req, Exception exception){
		logUtil.debug("Request "+req.getRequestURI()+" exception", exception);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("common/error");
		mav.addObject("title", "发生错误啦！");
		mav.addObject("message", exception.getMessage());
		return mav;
	}
	
	@ModelAttribute
	public void addAttribute(@AuthenticationPrincipal UserPasswordDetails upd,Model model,WebRequest request) throws Exception{
		//
		if(upd!=null){
		//	logUtil.debug("addAttribute");
			model.addAttribute("user", userRestService.getUserById(upd.getUser().getId()));
		}		
	}

}
