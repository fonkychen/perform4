package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.MonthlyIndicatorStatis;
import cn.aolc.group.performance.jpa.User;

@Transactional
public interface MonthlyIndicatorStatisRepository extends JpaRepository<MonthlyIndicatorStatis, Long>{
	public List<MonthlyIndicatorStatis> findByYearNumAndMonthNumAndUser(Integer yearNum,Integer monthNum,User user);
	public List<MonthlyIndicatorStatis> findByYearNumAndUserOrderByMonthNumAsc(Integer yearNum,User user);
}
