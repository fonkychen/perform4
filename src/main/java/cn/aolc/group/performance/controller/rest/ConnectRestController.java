package cn.aolc.group.performance.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.jpa.enumeration.UserTaskType;
import cn.aolc.group.performance.service.rest.RewardTaskService;
import cn.aolc.group.performance.service.rest.UserRestService;

@RestController
@RequestMapping("/rest/connect")
public class ConnectRestController {
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired
	private RewardTaskService rewardTaskService;
	
	@RequestMapping("/reward/save")
	@ResponseStatus(HttpStatus.OK)
	public void savereward(@RequestParam String title,@RequestParam(required=false) String description,@RequestParam Integer dateGap,
			@RequestParam Long coinNum ) throws Exception{		
		
		rewardTaskService.saveReward(title, description, dateGap, coinNum);
	}
	
	@RequestMapping("/task/save")
	@ResponseStatus(HttpStatus.OK)
	public void savetask(@RequestParam String title,@RequestParam(required=false) String description,
			@RequestParam UserTaskType taskType,@RequestParam Long coinNum) throws Exception{
		rewardTaskService.saveTask(title, taskType, description, coinNum);
	}
	
}
