package cn.aolc.group.performance.weixin.security;

import java.util.Collection;





import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class WechatAuthentication extends AbstractAuthenticationToken{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1057316200921543815L;

	private Object principal;
	
	private Object credentials;
	
	private String openId;
	
	public WechatAuthentication(Object credentials){
		super(null);
		this.credentials=credentials;
	}

	public WechatAuthentication(Object principal,Collection<? extends GrantedAuthority> credentials,String openId) {
		super(credentials);
		this.principal=principal;
		this.credentials=credentials;
		this.setOpenId(openId);
	}

	public Object getCredentials() {
		return credentials;
	}

	public Object getPrincipal() {		
		return this.principal;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}