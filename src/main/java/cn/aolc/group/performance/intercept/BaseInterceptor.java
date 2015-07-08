package cn.aolc.group.performance.intercept;

import org.springframework.security.core.context.SecurityContextHolder;

import cn.aolc.group.performance.security.UserPasswordDetails;

public class BaseInterceptor {
	
	protected UserPasswordDetails getPrincipal(){
		if(SecurityContextHolder.getContext().getAuthentication()==null) return null;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserPasswordDetails){
			return (UserPasswordDetails)principal;
		}
		return null;
	}

}
