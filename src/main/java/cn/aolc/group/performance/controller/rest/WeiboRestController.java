package cn.aolc.group.performance.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.jpa.WeiboReply;
import cn.aolc.group.performance.jpa.tenant.WeiboTopic;
import cn.aolc.group.performance.service.rest.WeiboRestService;

@RestController
@RequestMapping("/rest/weibo")
@Transactional(propagation=Propagation.REQUIRED)
public class WeiboRestController {
	
	@Autowired
	private WeiboRestService weiboService;
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void save(@RequestParam String content,@RequestParam(required=false) Long id) throws Exception{
		weiboService.saveTopic(id, content);
	}
	
	@RequestMapping(value="/favor")
	public WeiboTopic favor(@RequestParam Long topicId) throws Exception{
		return weiboService.favorTopic(topicId);
	}
	
	@RequestMapping(value="/reply/save",method=RequestMethod.POST,consumes="text/plain")
	public WeiboReply saveReply(@RequestParam(required=false) Long id,@RequestParam(required=false) Long replyTo,@RequestParam Long topicId,@RequestBody String content) throws Exception{
		return weiboService.saveReply(id, content, topicId, replyTo);
	}
	
	@RequestMapping("/reply/getByTopic")
	public List<WeiboReply> getReplyByTopic(@RequestParam Long id) throws Exception{
		return weiboService.getReplyByTopic(id);
	}
	
	@RequestMapping("/reply/getPageReplyByTopic")
	public Page<WeiboReply> getPageReplyByTopic(@RequestParam Long id,@RequestParam Integer page) throws Exception{
		return weiboService.getReplyByTopic(id, page, 10);
	}
	
	@RequestMapping("/topic/delete")
	@ResponseStatus(value=HttpStatus.OK)
	public void deleteTopic(@RequestParam Long id) throws Exception{
		//weiboService.deleteTopic(id);
	}

}
