package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.WeiboReplyRepository;
import cn.aolc.group.performance.dao.tenant.WeiboTopicRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WeiboReply;
import cn.aolc.group.performance.jpa.tenant.WeiboTopic;

@Service("weiboRestService")
public class WeiboRestService extends BaseRestService{
	@Autowired
	private WeiboTopicRepository weiboTopicRepository;
	
	@Autowired
	private WeiboReplyRepository weiboReplyRepository;
	
	public WeiboTopic saveTopic(Long id,String content) throws Exception{
		WeiboTopic wt=null;
		if(id!=null){
			wt=weiboTopicRepository.findOne(id);
		}
		if(wt==null){
			wt=new WeiboTopic();
			wt.setCreated(new Date());
			wt.setUser(getContextUser());
		}		
		wt.setContent(content);
		wt.setUpdated(new Date());
		wt.setFavorNum(0);
		wt.setReplyNum(0);
		wt.setDegree(calculateDegree(wt));
		wt=weiboTopicRepository.save(wt);
		return wt;
	}
	
	public WeiboReply saveReply(Long id,String content,Long topicId,Long replyToId) throws Exception{
		WeiboReply wr=null;
		if(id!=null){
			wr=weiboReplyRepository.findOne(id);			
		}
		
		if(wr==null){
			wr=new WeiboReply();
			wr.setCreated(new Date());
			wr.setUser(getContextUser());
			
			if(topicId!=null){
				WeiboTopic topic=weiboTopicRepository.findOne(topicId);
				topic.setReplyNum((topic.getReplyNum()==null?0:topic.getReplyNum())+1);
				wr.setWeiboTopic(topic);
				topic.setDegree(calculateDegree(topic));
				weiboTopicRepository.save(topic);
			}
			
			if(replyToId!=null){
				wr.setReplyTo(weiboReplyRepository.findOne(replyToId));
			}
		}
		
		wr.setContent(content);
		wr.setUpdated(new Date());
		
		wr=weiboReplyRepository.save(wr);
		return wr;
	}
	
	public WeiboTopic favorTopic(Long topicId) throws Exception{
		WeiboTopic wt=weiboTopicRepository.findOne(topicId);
		if(wt==null) throw new Exception(localeMessages.getMessage("object.invalid", new Object[]{"WeiboTopic"} , null));
		List<User> users=wt.getFavorUsers();
		User user=getContextUser();
		if(!users.contains(user)){
			users.add(user);
			wt.setFavorUsers(users);
			wt.setFavorNum((wt.getFavorNum()==null?0:wt.getFavorNum())+1);
				
		}
		else{
			users.remove(user);
			wt.setFavorUsers(users);
			wt.setFavorNum((wt.getFavorNum()==null?0:wt.getFavorNum())-1);
			
		}
		wt.setDegree(calculateDegree(wt));
		weiboTopicRepository.save(wt);
		return wt;
	}
	
	public WeiboTopic undoFavorTopic(Long topicId) throws Exception{
		WeiboTopic wt=weiboTopicRepository.findOne(topicId);
		if(wt==null) throw new Exception(localeMessages.getMessage("object.invalid", new Object[]{"WeiboTopic"} , null));
		List<User> users=wt.getFavorUsers();
		User user=getContextUser();
		if(users.contains(user)){
			users.remove(user);
			wt.setFavorUsers(users);
			wt.setFavorNum(users.size());
			wt.setDegree(calculateDegree(wt));
			weiboTopicRepository.save(wt);
		}
		return wt;
	}
	
	public Page<WeiboTopic> getTopicOrderByCreated(int page,int size) throws Exception{
		Pageable pageable=new PageRequest(page-1, size);
		return weiboTopicRepository.findByCreatedDesc(pageable);
	}
	
	public Page<WeiboTopic> getTopicOrderByDegree(int page,int size) throws Exception{
		Pageable pageable=new PageRequest(page-1, size);
		return weiboTopicRepository.findByDegreeDescAndCreatedDesc(pageable);
	}
	
	public Page<WeiboTopic> getTopicByUser(User user,int page,int size,String type) throws Exception{
		Pageable pageable=new PageRequest(page-1, size);
		if(user==null){
			user=getContextUser();
		}
		if(type!=null && type.equals("hot")){
			return weiboTopicRepository.findByUserOrderByDegreeDesc(user, pageable);
		}
		return weiboTopicRepository.findByUserOrderByCreatedDesc(user, pageable);
	}
	
	public List<WeiboReply> getReplyByTopic(Long topicId) throws Exception{
		WeiboTopic wt=weiboTopicRepository.findOne(topicId);
		if(wt!=null){
			return wt.getWeiboReplys();
		}
		return null;
	}
	
	public Page<WeiboReply> getReplyByTopic(Long topicId,Integer page,int size) throws Exception{
		Pageable pageable=new PageRequest(page-1, size);
		WeiboTopic wt=null;
		if(topicId!=null){
			wt=weiboTopicRepository.findOne(topicId);
		}
		
		return weiboReplyRepository.findByWeiboTopicOrderByCreatedDesc(wt, pageable);
	}
	
	
	public List<WeiboTopic> getFavorTopics(List<WeiboTopic> topics,User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		return weiboTopicRepository.findByTopicInAndUserFavored(topics, user);
	}
	
	public void deleteTopic(Long topicId) throws Exception{
		//not supported
		throw new Exception("不支持的操作");
//		User user=getContextUser();
//		WeiboTopic wt=weiboTopicRepository.findOne(topicId);
//		if(wt==null || !wt.getUser().equals(user)) throw new Exception("没有权限");
//		weiboTopicRepository.delete(wt);
	}
	
	public double calculateDegree(WeiboTopic topic) throws Exception{
		int replyNum=topic.getReplyNum()==null?0:topic.getReplyNum();
		int favorNum=topic.getFavorNum()==null?0:topic.getFavorNum();
		Date created=topic.getCreated();
		
		Calendar cal=Calendar.getInstance();
		cal.set(2010, 0, 1, 0, 0, 0);//Constants
		Date scal=cal.getTime();
		
		
		long gap=created.getTime()-scal.getTime();
		return ((double)gap)/1000/3600/24+((replyNum==0 && favorNum==0)?0l:Math.log(replyNum*3+favorNum*2));
		//gap/45
	}

}
