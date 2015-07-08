package cn.aolc.group.performance.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.UserMessageRepository;
import cn.aolc.group.performance.jpa.AchieveEvent;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.enumeration.MessageType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.DrumEvent;
import cn.aolc.group.performance.util.exception.SyncDataException;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UpdateMessageService {
	
	@Autowired
	private UserMessageRepository userMessageRepository;
	
	public void updateUserMessage(MessageType messageType,EntityWithId reference) throws Exception{
		
		
		if(messageType.equals(MessageType.DrumEventType)){
			if(!(reference instanceof DrumEvent)) throw new SyncDataException("invalid parameter");
			DrumEvent de=(DrumEvent)reference;
			User user=de.getUser();
			broadcast(MessageType.DrumEventType, user, de.getEvent(), de.getId());
		}
		else if(messageType.equals(MessageType.AchieveEventType)){
			if(!(reference instanceof AchieveEvent)) throw new SyncDataException("invalid parameter");
			AchieveEvent ae=(AchieveEvent)reference;
			User user=ae.getUser();
			notice(MessageType.AchieveEventType, user, "恭喜 "+user.getName()+" 获得成就 "+ae.getAchieveMilestone().getName()+"，"+ae.getAchieveMilestone().getDescription(), ae.getId());
		}
		
		
	}
	
	private void broadcast(MessageType messageType,User user,String content,Long referenceId) throws Exception{
		List<UserMessage> umlist=new ArrayList<UserMessage>();
		Company company=user.getCompany();//should broadcast to everyone
		if(company!=null){
			List<User> users=company.getUsers();
			for(User cuser:users){
				UserMessage um=new UserMessage();
				um.setByUser(user);
				um.setContent(content);
				um.setIsRead(false);
				um.setMessageType(messageType);
				um.setReferenceId(referenceId);
				um.setUpdated(new Date());
				um.setUser(cuser);
				umlist.add(um);
			}
		}
		
		userMessageRepository.save(umlist);
	}
	
	private void notice(MessageType messageType,User user,String content,Long referenceId) throws Exception{
		UserMessage um=new UserMessage();
		um.setByUser(user);
		um.setContent(content);
		um.setIsRead(false);
		um.setMessageType(messageType);
		um.setReferenceId(referenceId);
		um.setUpdated(new Date());
		um.setUser(user);
		userMessageRepository.save(um);
	}

}
