package cn.aolc.group.performance.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class KpiAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	public void onAuthenticationSuccess(HttpServletRequest request,
	        HttpServletResponse response, Authentication auth)
	        throws IOException, ServletException
	{
		

	    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	        response.getWriter().print(
	                "{\"success\":\"true\", \"targetUrl\":\""
	                        + determineTargetUrl(request, response) + "\"}");
	        response.getWriter().flush();
	        response.getWriter().close();
	    } else {
	        super.onAuthenticationSuccess(request, response, auth);
	    }
	}

}
