package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WeeklyTitleSalary;

@Transactional(propagation=Propagation.REQUIRED)
public interface WeeklyTitleSalaryRepository extends JpaRepository<WeeklyTitleSalary, Long>{
	
	public List<WeeklyTitleSalary> findByYearNumAndWeekOfYear(Integer yearNum,Integer weekOfYear);
	
	public List<WeeklyTitleSalary> findByYearNumAndWeekOfYearAndUser(Integer yearNum,Integer weekOfYear,User user);

}
