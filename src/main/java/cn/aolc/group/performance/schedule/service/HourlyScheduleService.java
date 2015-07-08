package cn.aolc.group.performance.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.tenant.WeiboTopicRepository;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class HourlyScheduleService {
	
	@Autowired
	private WeiboTopicRepository weiboTopicRepository;
	
	public void runSchedule(){
		
	}
	
	
//	public void updateTopicDegree(){
//		//
//		int page=0;boolean hasnext=true;
//		while(hasnext){
//			Pageable pageable=new PageRequest(page, 50, Direction.ASC, "created");
//		    Page<WeiboTopic> pwt=weiboTopicRepository.findAll(pageable);
//		    hasnext=pwt.hasNext();
//			page++;			
//		}
//		
//	}
//	
//	private double calTopicDegree(WeiboTopic topic) throws Exception{
//		Date created=topic.getCreated();
//		int replyNum=topic.getReplyNum();
//		int favorNum=topic.getFavorNum();
//		
//		Date now=new Date();
//		long gap=now.getTime()-created.getTime();
//		
//	}

}
