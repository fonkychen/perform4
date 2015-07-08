package cn.aolc.group.performance.weixin.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class WechatAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            Authentication authentication)
              throws javax.servlet.ServletException, IOException{
		super.onAuthenticationSuccess(request, response, authentication);
	}
        
}
