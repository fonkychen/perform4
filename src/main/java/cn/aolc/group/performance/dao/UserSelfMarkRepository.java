package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserSelfMark;
@Transactional(propagation=Propagation.REQUIRED)
public interface UserSelfMarkRepository extends JpaRepository<UserSelfMark, Long>{
	public List<UserSelfMark> findByUserAndYearNumAndMonthNumAndDayNum(User user,Integer yearNum,Integer monthNum,Integer dayNum);
	
	@Query("select usm from UserSelfMark usm where usm.user in ?1 and usm.yearNum=?2 and usm.monthNum=?3 and usm.dayNum=?4")
	public List<UserSelfMark> findByUserInAndYearNumAndMonthNumAndDayNum(List<User> users,Integer yearNum,Integer monthNum,Integer dayNum);
	
	@Query("select usm from UserSelfMark usm where usm.user in ?1 and CONCAT(usm.yearNum,'-',CASE WHEN usm.monthNum <10 THEN '0' ELSE '' END,usm.monthNum,'-',CASE WHEN usm.dayNum <10 THEN '0' ELSE '' END,usm.dayNum) between ?2 and ?3")
	public List<UserSelfMark> findByUserInAndDateBetween(List<User> users,String startDate,String endDate);
	
	public Long countByUserAndYearNum(User user,Integer yearNum);

}
