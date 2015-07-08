package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserPopularEvent;
import cn.aolc.group.performance.jpa.enumeration.UserPopularAction;
import cn.aolc.group.performance.model.UserPopularScatter;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserPopularEventRepository extends JpaRepository<UserPopularEvent, Long>{
	public Long countByYearNumAndMonthNumAndDayNumAndByUser(Integer yearNum,Integer monthNum,Integer dayNum,User byUser);
		
	@Query("select new cn.aolc.group.performance.model.UserPopularScatter(event.user,event.popularAction,count(event.id)) from UserPopularEvent event where event.user in ?1 and event.yearNum=?2 group by event.user,event.popularAction")
	public List<UserPopularScatter> countByUserInAndYearNum(List<User> users,Integer yearNum);
	
	public Long countByUserAndYearNumAndPopularAction(User user,Integer yearNum,UserPopularAction popularAction);
	
	public Long countByYearNumAndByUserAndPopularAction(Integer yearNum,User byUser,UserPopularAction popularAction);
	
	public Long countByYearNumAndMonthNumAndDayNumAndUserAndByUser(Integer yearNum,Integer monthNum,Integer dayNum,User user,User byUser);
}
