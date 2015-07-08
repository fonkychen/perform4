package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.User;

@Transactional
public interface DailyBoardRepository extends JpaRepository<DailyBoard, Long>{
	
	//public List<WeeklyBoard> findByDateBetweenAndUserOrderByDateAsc(Date startdate,Date endDate,User user);
	
	//public List<WeeklyBoard> findByDateBetweenAndUserInOrderByDateAsc(Date startDate,Date endDate,List<User> users);
	
	@Query("SELECT wb FROM DailyBoard wb  where ((wb.yearNum*12*31+wb.monthNum*31+wb.dayNum) between (?1*12*31+?2*31+?3) and (?4*12*31+?5*31+?6)) and wb.user in ?7")	
	public List<DailyBoard> findByCalDateAndUserIn(Integer syearNum,Integer smonthNum,Integer sdayNum
			,Integer eyearNum,Integer emonthNum,Integer edayNum,List<User> users);
	
	public List<DailyBoard> findByYearNumAndMonthNumAndDayNum(Integer yearNum,Integer monthNum,Integer dayNum);
	
	public List<DailyBoard> findByUserAndYearNumAndMonthNumAndDayNum(User user,Integer yearNum,Integer monthNum,Integer dayNum);
	
	@Query("select db.yearNum,db.monthNum,db.dayNum,db.taskType,count(db.taskType) from DailyBoard db where db.user in ?1 and CONCAT(db.yearNum,'-',CASE WHEN db.monthNum <10 THEN '0' ELSE '' END,db.monthNum,'-',CASE WHEN db.dayNum <10 THEN '0' ELSE '' END,db.dayNum) between ?2 and ?3 group by db.yearNum,db.monthNum,db.dayNum,db.taskType order by db.yearNum asc,db.monthNum asc,db.dayNum asc,db.taskType asc")
	public List<Object[]> findGroupbyCountTaskTypeByUserInAndDateBetween(List<User> users,String startDate,String endDate );
	
	@Query("select db from DailyBoard db where db.user in ?1 and CONCAT(db.yearNum,'-',CASE WHEN db.monthNum <10 THEN '0' ELSE '' END,db.monthNum,'-',CASE WHEN db.dayNum <10 THEN '0' ELSE '' END,db.dayNum) between ?2 and ?3 order by db.yearNum desc,db.monthNum desc,db.dayNum desc")
	public List<DailyBoard> findByUserInAndDateBetween(List<User> users,String startDate,String endDate);
	
	public Long countByUserAndYearNum(User user,Integer yearNum);
}
