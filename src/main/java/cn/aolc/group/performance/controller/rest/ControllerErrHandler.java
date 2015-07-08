package cn.aolc.group.performance.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.aolc.group.performance.service.rest.UserRestService;

@ControllerAdvice(basePackages="cn.aolc.group.performance.controller.rest")
public class ControllerErrHandler {
	private final static Logger logger=LoggerFactory.getLogger(ControllerErrHandler.class);
	
	@Autowired
	private UserRestService userRestController;
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String errHandle(Exception ex){
		logger.debug("rest controller exception", ex);
		return ex.getMessage();
	}
	
	

}
