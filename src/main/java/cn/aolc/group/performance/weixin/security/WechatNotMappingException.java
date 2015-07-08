package cn.aolc.group.performance.weixin.security;

import org.springframework.security.core.AuthenticationException;

public class WechatNotMappingException extends AuthenticationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2302508189694588212L;
	
	private String openId;

	public WechatNotMappingException(String msg,String openId) {
		super(msg);		
		this.openId=openId;
	}
	
	public String getOpenId(){
		return this.openId;
	}

}
