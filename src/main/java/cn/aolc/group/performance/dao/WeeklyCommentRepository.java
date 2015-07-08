package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WeeklyComment;

@Transactional
public interface WeeklyCommentRepository extends JpaRepository<WeeklyComment, Long>{
	
	public List<WeeklyComment> findByUserAndYearNumAndWeekOfYear(User user,Integer yearNum,Integer weekofyear);
	
	public List<WeeklyComment> findByYearNumAndWeekOfYear(Integer yearNum,Integer weekofyear);
	
	public List<WeeklyComment> findByYearNumAndWeekOfYearAndByUser(Integer yearNum,Integer weekOfYear,User byUser);

	@Query("select wc from WeeklyComment wc where wc.user=?1 order by wc.yearNum desc,wc.weekOfYear desc")
	public List<WeeklyComment> findByUserOrderByYearNumDescWeekOfYearDesc(User user);
	
	@Query("select wc from WeeklyComment wc where wc.user=?1 order by wc.yearNum desc,wc.weekOfYear desc")
	public List<WeeklyComment> findByUserOrderByYearNumDescWeekOfYearDesc(User user,Pageable pageable);
	
	@Query("select sum(wc.scoreNum)/count(wc.id) from WeeklyComment wc where wc.user in ?1 and wc.yearNum=?2 and wc.weekOfYear=?3")
	public Double calAverageScoredByUserInAndYearNumAndWeekOfYear(List<User> users,Integer yearNum,Integer weekOfYear);

}
