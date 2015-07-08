package cn.aolc.group.performance.weixin.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.security.UserPasswordDetails;
import cn.aolc.group.performance.weixin.dao.WechatUserMappingRepository;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;

public class WechatAuthenticationManager implements AuthenticationManager{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WechatUserMappingRepository wechatUserMappingRepository;

	public Authentication authenticate(Authentication arg0)
			throws AuthenticationException {
		String openid=arg0.getCredentials().toString();
		List<WechatUserMapping> wmlist=wechatUserMappingRepository.findByOpenId(openid);
		if(wmlist.size()<=0){
			throw new WechatNotMappingException("no mapping "+openid, openid);
		}
		WechatUserMapping wm=wmlist.get(0);
		User user=wm.getUser();
		List<RoleType> roles=user.getRoles();
		List<GrantedAuthority> galist=new ArrayList<GrantedAuthority>();
		
		if(roles!=null){
			for(RoleType role:roles){
				GrantedAuthorityImpl ga=new GrantedAuthorityImpl("ROLE_"+role.name());
				galist.add(ga);
			}
		}
		WechatAuthentication wa=new WechatAuthentication(new UserPasswordDetails(user), galist, openid);
		wa.setAuthenticated(true);		
		
		return wa;
	}

}
