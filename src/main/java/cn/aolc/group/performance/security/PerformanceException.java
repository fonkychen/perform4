package cn.aolc.group.performance.security;

import org.springframework.security.core.AuthenticationException;

public class PerformanceException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4749584411227725893L;

	public PerformanceException(String msg) {
		super(msg);
		
	}

}
