package cn.aolc.group.performance.weixin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.SnsToken;
import cn.aolc.group.performance.util.exception.PerformanceException;
import cn.aolc.group.performance.weixin.util.WeixinUtil;

public class WechatSecurityFilter extends AbstractAuthenticationProcessingFilter {
		
	private final static Logger logger=LoggerFactory.getLogger(WechatSecurityFilter.class);
	
	private SnsAPI snsAPI;

	protected WechatSecurityFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		snsAPI=new SnsAPI();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		logger.info("WechatSecurityFilter attemptAuthentication");
		String code=request.getParameter("code");
		if(code==null){
			throw new PerformanceException("code cannot be null");
		}
		SnsToken token=snsAPI.oauth2AccessToken(WeixinUtil.APP_ID, WeixinUtil.APP_SECRET, code);
		String openid=token.getOpenid();
		if(openid==null) throw new PerformanceException("cannot get openid");
		
		WechatAuthentication wa=new WechatAuthentication(openid);

    	return getAuthenticationManager().authenticate(wa);
		
		
	}

}
