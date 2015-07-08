package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.enumeration.MessageType;

@Transactional
public interface UserMessageRepository extends JpaRepository<UserMessage, Long>{
	public List<UserMessage> findByUserAndIsReadAndMessageTypeOrderByIdDesc(User user,Boolean isRead,MessageType messageType,Pageable pageable);
	
	public List<UserMessage> findByUserAndMessageTypeOrderByIdDesc(User user,MessageType messageType,Pageable pageable);
	
	public List<UserMessage> findByUserAndIsReadOrderByIdDesc(User user,Boolean isRead,Pageable pageable);
	
	public Page<UserMessage> findByUser(User user,Pageable pageable);
}
