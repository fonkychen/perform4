package cn.aolc.group.performance.controller.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aolc.group.performance.jpa.enumeration.UserTaskType;
import cn.aolc.group.performance.service.rest.DrumEventService;
import cn.aolc.group.performance.service.rest.RewardTaskService;

@Controller
@RequestMapping("/connect")
@Transactional(propagation=Propagation.REQUIRED)
public class ConnectController {
	
	@Autowired
	private RewardTaskService rewardTaskService;
	
	@Autowired
	private DrumEventService drumEventService;
	
	@RequestMapping("/reward/index")
	public void reward(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(order==null){
			order="desc";
		}
		model.addAttribute("page", rewardTaskService.getRewardList(null, page, 10,order));
		model.addAttribute("order", order);
	}
	
	@RequestMapping("/reward/new")
	public void newreward(Model model) throws Exception{
		
	}
	
	@RequestMapping("/reward/item-{id}")
	public void rewarditem(Model model,@PathVariable Long id) throws Exception{
		model.addAttribute("reward", rewardTaskService.getReward(id));
	}
	
	@RequestMapping("/task/index")
	public void task(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(order==null){
			order="desc";
		}
		
		model.addAttribute("page", rewardTaskService.getTaskList(null, page, 10, order));
		model.addAttribute("order", order);
	}
	
	@Secured(value="ROLE_ADMIN")
	@RequestMapping("/task/new")
	public void newtask(Model model) throws Exception{
		model.addAttribute("taskTypes", UserTaskType.values());
	}
	
	@RequestMapping("/task/item-{id}")
	public void taskitem(Model model,@PathVariable Long id) throws Exception{
		model.addAttribute("task", rewardTaskService.getTask(id));
	}
	
	@RequestMapping("/drum/index")
	public void drum(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(order==null){
			order="desc";
		}
		if(page==null){
			page=1;
		}
		model.addAttribute("page", drumEventService.getDrum(null, page, 10, order));
		model.addAttribute("order", order);
	}

}
