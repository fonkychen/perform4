package cn.aolc.group.performance.security;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


public class KpiAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            AuthenticationException exception)
     throws IOException, ServletException{
		//logger.info("kpi authentication fail", exception.getAuthentication().);
		String errmesg="";
		if(exception instanceof LockedException){
			errmesg="账号已被锁定，您可以通过<a href='/common/findpass.html'>此链接</a>重置密码";
		}
		else if(exception instanceof DisabledException){
			errmesg="改账号已被禁用，如有需要请联系管理员";
		}
//		else if(exception instanceof WechatNotMappingException){
//			response.sendRedirect("/wechat/kpiauth.html?openid="+((WechatNotMappingException)exception).getOpenId());
//			return;
//		}
		else{
			errmesg=exception.getMessage();
		}
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	        response.getWriter().print(
	                "{\"success\":\"false\", \"message\" : \""
	                        + errmesg + "\"}");
	        response.getWriter().flush();
	        response.getWriter().close();
	    }
		else{
			super.onAuthenticationFailure(request, response, exception);
		}
		
	}
}
