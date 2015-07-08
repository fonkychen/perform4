package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.ScoreHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
@Transactional
public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Long>{
	
	public List<ScoreHistory> findByScoreTypeAndReferenceId(ScoreType scoreType,Long referenceId);
	
	public List<ScoreHistory> findByUserAndYearNumAndMonthNumAndDayNumAndScoreTypeAndReferenceId(User user,Integer yearNum,Integer monthNum,Integer dayNum,ScoreType scoreType,Long referenceId);
	
	public List<ScoreHistory> findByYearNumAndMonthNumAndDayNumAndScoreTypeAndReferenceId(Integer yearNum,Integer monthNum,Integer dayNum,ScoreType scoreType,Long referenceId);
	
	@Query("select sum(sh.score) from ScoreHistory sh where sh.user=?1 and sh.yearNum=?2 and sh.monthNum=?3")
	public Integer findSumScoreByUserAndYearNumAndMonthNum(User user,Integer yearNum,Integer monthNum);
	
	@Query("select sum(sh.score) from ScoreHistory sh where sh.user=?1 and sh.yearNum=?2 ")
	public Integer findSumScoreByUserAndYearNum(User user,Integer yearNum);
	
//	@Query(nativeQuery=true,value="select sh.user_id , sum(sh.score) from Score_History sh group by sh.user_id")
//	public List<Object[]> findSumScoreGroupbyUser();
	
	@Query(value="select sh.yearNum,sh.monthNum,sh.dayNum,sh.scoreType,sum(sh.score) from ScoreHistory sh where sh.user in ?1 and CONCAT(sh.yearNum,'-',CASE WHEN sh.monthNum <10 THEN '0' ELSE '' END,sh.monthNum,'-',CASE WHEN sh.dayNum <10 THEN '0' ELSE '' END,sh.dayNum) between ?2 and ?3 group by sh.yearNum,sh.monthNum,sh.dayNum,sh.scoreType order by sh.yearNum asc ,sh.monthNum asc,sh.dayNum asc,sh.scoreType asc")	
	public List<Object[]> findDailyGroupSumScoreByUserInAndScoreTypeAndDateBetween(List<User> userlist,String startDate,String endDate);
	
	@Query("select sh.scoreType,sh.score,count(sh.score) from ScoreHistory sh where sh.user in ?1  and CONCAT(sh.yearNum,'-',CASE WHEN sh.monthNum <10 THEN '0' ELSE '' END,sh.monthNum,'-',CASE WHEN sh.dayNum <10 THEN '0' ELSE '' END,sh.dayNum) between ?2 and ?3 group by sh.scoreType,sh.score order by sh.scoreType asc,count(sh.score) desc")
	public List<Object[]> findDailyGroupCountScoreByUserInAndScoreTypeAndDateBetween(List<User> userlist,String startDate,String endDate);
	
	@Query("select sh.user,sum(sh.score) as sumscore from ScoreHistory sh where sh.yearNum=?1 and sh.monthNum=?2 and sh.user in ?3 order by sumscore desc")
	public List<Object[]> findMonthlyGroupSumScoreByYearNumAndMonthNumAndUserInOrderByGroupSum(Integer yearNum,Integer monthNum,List<User> userlist);
	
	@Query("select sh.user,sum(sh.score) as sumscore from ScoreHistory sh where sh.yearNum=?1 and ((sh.monthNum-1)/3+1)=?2 and sh.user in ?3 order by sumscore desc")
	public List<Object[]> findQuarterlyGroupSumScoreByYearNumAndMonthNumAndUserInOrderByGroupSum(Integer yearNum,int quarter,List<User> userlist);
	
	@Query("select sum(sh.score) as sumscore from ScoreHistory sh where sh.yearNum=?1 and ((sh.monthNum-1)/3+1)=?2 and sh.user =?3")
	public Long calQuarterlySumScoreByYearNumAndQuarterNumAndUser(Integer yearNum,Integer quarterNum,User user);
}
