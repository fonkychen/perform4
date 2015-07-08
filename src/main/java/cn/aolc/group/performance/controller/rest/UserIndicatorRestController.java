package cn.aolc.group.performance.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.service.rest.UserIndicatorService;

@RestController
@RequestMapping("/rest/indicator")
@Transactional(propagation=Propagation.REQUIRED)
public class UserIndicatorRestController {
	private final static Logger logger=LoggerFactory.getLogger(UserIndicatorRestController.class);
	
	@Autowired
	private UserIndicatorService userIndicatorService;
	
	@RequestMapping("/progress/save")
	@ResponseStatus(value=HttpStatus.OK)
	public void saveprogress(Long indicatorId,Integer selfProgress) throws Exception{
		userIndicatorService.saveSelfProgress(indicatorId, selfProgress);
	}
	
	@RequestMapping("/manprogress/save")
	@ResponseStatus(value=HttpStatus.OK)
	public void savemanprogress(@RequestParam(required=false) Long userId,@RequestParam(required=false) Long indicatorId,
			@RequestParam String name,@RequestParam Integer weight,Integer manProgress) throws Exception{
		userIndicatorService.saveManProgress(userId, indicatorId, name, weight, manProgress);
	}

}
