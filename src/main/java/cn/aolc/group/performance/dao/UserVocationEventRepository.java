package cn.aolc.group.performance.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserVocationEvent;
import cn.aolc.group.performance.jpa.enumeration.VocationEventType;

@Transactional
public interface UserVocationEventRepository extends JpaRepository<UserVocationEvent, Long>{
	
	public Page<UserVocationEvent> findByUserOrderByCreatedDesc(User user,Pageable pageable);
	
	@Query(value="select u from UserVocationEvent u where u.user in ?1 and u.eventType in ?2 order by u.created asc")
	public Page<UserVocationEvent> findByUserInAndEventTypeInOrderByCreatedDesc(List<User> userlist,List<VocationEventType> eventTypes,Pageable pageable);

}
