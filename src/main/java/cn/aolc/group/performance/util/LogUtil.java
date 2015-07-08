package cn.aolc.group.performance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("logUtil")
public class LogUtil {
	private Logger logger=LoggerFactory.getLogger(LogUtil.class);
	
	public void info(String message){
		logger.info(message);
	}
	
	public void debug(String message){
		logger.debug(message);
	}
	
	public void debug(String message,Throwable throwable){
		logger.debug(message, throwable);
	}
	
	public void debug(String message,Object obj){
		logger.debug(message, obj);
	}

}
