package cn.aolc.group.performance.controller.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aolc.group.performance.jpa.tenant.WeiboTopic;
import cn.aolc.group.performance.service.rest.MessageService;
import cn.aolc.group.performance.service.rest.WeiboRestService;

@Controller
@RequestMapping("/club")
@Transactional(propagation=Propagation.REQUIRED)
public class ClubController {
	
	@Autowired
	private WeiboRestService weiboRestService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/topic")
	public void topic(Model model,@RequestParam(required=false) Integer page) throws Exception{
		if(page==null){
			page=1;
		}
		Page<WeiboTopic> ret=weiboRestService.getTopicOrderByCreated(page, 10);
		
		
		model.addAttribute("page", ret);
		model.addAttribute("order", "desc");
		model.addAttribute("favors", weiboRestService.getFavorTopics(ret.getContent(), null));
	}
	
	@RequestMapping("/topic/{type}")
	public void topicByType(Model model,@PathVariable String type,@RequestParam(required=false) Integer page) throws Exception{
		if(page==null){
			page=1;
		}
		Page<WeiboTopic> ret=null;
		if(type!=null && type.equals("hot")){
			ret=weiboRestService.getTopicOrderByDegree(page, 10);
		}
		else{
			ret=weiboRestService.getTopicOrderByCreated(page, 10);
		}
		model.addAttribute("type", type);
		model.addAttribute("page", ret);
		model.addAttribute("order", "desc");
		model.addAttribute("favors", weiboRestService.getFavorTopics(ret.getContent(), null));
	}
	
	@RequestMapping("/from")
	public void from(Model model,@RequestParam(required=false) String type,@RequestParam(required=false) Integer page) throws Exception{
		if(page==null){
			page=1;
		}
		Page<WeiboTopic> ret=weiboRestService.getTopicByUser(null, page, 10, type);
		
		
		model.addAttribute("page", ret);
		model.addAttribute("order", "desc");
		model.addAttribute("favors", weiboRestService.getFavorTopics(ret.getContent(), null));
	}
	
	@RequestMapping("/attention")
	public void attention(Model model,@RequestParam(required=false) String type,@RequestParam(required=false) Integer page) throws Exception{
		if(page==null){
			page=1;
		}
		Page<WeiboTopic> ret=null;
		if(type!=null && type.equals("hot")){
			ret=weiboRestService.getTopicOrderByDegree(page, 10);
		}
		else{
			ret=weiboRestService.getTopicOrderByCreated(page, 10);
		}
		model.addAttribute("page", ret);
		model.addAttribute("order", "desc");
		model.addAttribute("favors", weiboRestService.getFavorTopics(ret.getContent(), null));
	}
	
	@RequestMapping("/notice")
	public void notice(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(order==null){
			order="desc";
		}
		model.addAttribute("order", order);
		model.addAttribute("page", messageService.getMessages(null, page, order, 10));
	}

}
