package cn.aolc.group.performance.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.UserMessageRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserMessage;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MessageService extends BaseRestService{
	
	@Autowired
	private UserMessageRepository userMessageRepository;
	
	public Page<UserMessage> getMessages(User user,int page,String order,int num) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		
		Pageable pageable=new PageRequest(page-1, num, order.equals("asc")?Direction.ASC:Direction.DESC, "id");
		return userMessageRepository.findByUser(user, pageable);
	}	

}
