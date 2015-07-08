package cn.aolc.group.performance.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.service.rest.DrumEventService;

@RestController
@RequestMapping("/rest/event")
public class UserEventRestController {
	
	@Autowired
	private DrumEventService drumEventService; 
	
	@RequestMapping("/drum/save")
	@ResponseStatus(value=HttpStatus.OK)
	public void saveDrum(String content) throws Exception{
		drumEventService.saveDrum(content);
	}

}
