package cn.aolc.group.performance.weixin.security;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class WechatAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	@Value("${server.proto}://${server.host}")
	private String server;
	
	public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            AuthenticationException exception)
     throws IOException, ServletException{
		response.sendRedirect(server+"/wechat/kpiauth.html?err=1");
	}

}
