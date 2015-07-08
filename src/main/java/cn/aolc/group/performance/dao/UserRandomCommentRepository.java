package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;

@Transactional
public interface UserRandomCommentRepository extends JpaRepository<UserRandomComment, Long>{
	
	public List<UserRandomComment> findByYearNumAndWeekOfYearAndByUser(Integer yearNum,Integer weekOfYear,User byUser);
	
	public List<UserRandomComment> findByYearNumAndWeekOfYearAndUser(Integer yearNum,Integer weekOfYear,User user);
	
	public List<UserRandomComment> findByYearNumAndWeekOfYear(Integer yearNum,Integer weekofYear);
	
	public Page<UserRandomComment> findByYearNumAndUserOrderByWeekOfYearDesc(Integer yearNum,User user,Pageable pageable);

	@Query("select sum(urc.actualScored)/count(urc.id) from UserRandomComment urc where urc.user in ?1 and urc.yearNum=?2 and urc.weekOfYear=?3")
	public Double calAverageScoreByUserInAndYearNumAndWeekOfYear(List<User> users,Integer yearNum,Integer weekofYear);
}
