package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WorkOvertime;

@Transactional(propagation=Propagation.REQUIRED)
public interface WorkOvertimeRepository extends JpaRepository<WorkOvertime, Long>{
	
	public List<WorkOvertime> findByYearNumAndMonthNumAndDayNumAndUser(Integer yearNum,Integer monthNum,Integer dayNum,User user);
	
	public List<WorkOvertime> findByYearNumAndMonthNumAndDayNum(Integer yearNum,Integer monthNum,Integer dayNum);
	
	@Query("select wo from WorkOvertime wo where wo.yearNum=?1 and wo.monthNum=?2 and wo.dayNum=?3 and wo.user in ?4 order by wo.user asc")
	public List<WorkOvertime> findByYearNumAndMonthNumAndDayNumAndUserIn(Integer yearNum,Integer monthNum,Integer dayNum,List<User> users);
	
	@Query("select wo from WorkOvertime wo where CONCAT(wo.yearNum,'-',CASE WHEN wo.monthNum <10 THEN '0' ELSE '' END,wo.monthNum,'-',CASE WHEN wo.dayNum <10 THEN '0' ELSE '' END,wo.dayNum) >= ?1 and wo.user in ?2")
	public List<WorkOvertime> findByDateAfterAndUserIn(String date,List<User> users);
	
	@Query("select wo from WorkOvertime wo where wo.yearNum=?1 and wo.monthNum=?2 and  wo.user in ?3 order by wo.yearNum desc,wo.monthNum desc,wo.dayNum desc,wo.user asc")
	public List<WorkOvertime> findByYearNumAndMonthNumAndUserIn(Integer yearNum,Integer monthNum,List<User> users);
	
	@Query(value="select wo from WorkOvertime wo where  wo.user in ?1 order by wo.yearNum desc,wo.monthNum desc,wo.dayNum desc,wo.user asc")
	public Page<WorkOvertime> findByUserIn(List<User> users,Pageable pageable);

}
