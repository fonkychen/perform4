package cn.aolc.group.performance.util.exception;

import org.springframework.security.core.AuthenticationException;

public class PerformanceException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PerformanceException(String message){
		super(message);
	}
	
	public PerformanceException(String message,Throwable throwable){
		super(message, throwable);
	}

}
