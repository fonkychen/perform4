package cn.aolc.group.performance.controller.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TilesDefinitionHandlerInterceptor extends HandlerInterceptorAdapter {
	private final static Logger logger=LoggerFactory.getLogger(TilesDefinitionHandlerInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
     throws Exception{
		return true;
	}
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView){
		if(modelAndView==null) return;
		String viewname=modelAndView.getViewName();
		viewname=viewname.replaceAll("/", "_");
		modelAndView.addObject("viewname", viewname);
		
	}

}
