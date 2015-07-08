package cn.aolc.group.performance.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserNotify;
import cn.aolc.group.performance.jpa.enumeration.NotifyType;

@Transactional
public interface UserNotifyRepository extends JpaRepository<UserNotify, Long>{
	
	public List<UserNotify> findByUser(User user);
	
	public List<UserNotify> findByUserAndNotifyType(User user,NotifyType notifyType);
	
	@Query("SELECT un FROM UserNotify un WHERE un.user IN ?1 AND un.notifyType=?2 ")
	public List<UserNotify> findByUserInAndNotifyType(Collection<User> users,NotifyType notifyType);

}
