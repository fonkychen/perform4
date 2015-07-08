package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.MonthlyIndicator;

@Transactional
public interface MonthlyIndicatorRepository extends JpaRepository<MonthlyIndicator, Long>{
	
	@Query("SELECT mi FROM MonthlyIndicator mi WHERE mi.yearNum=?1 AND mi.monthNum=?2 AND mi.indicator in ?3 order by mi.indicator asc")
	public List<MonthlyIndicator> findByYearNumAndMonthNumAndIndicatorIn(Integer yearNum,Integer monthNum,List<Indicator> indicators);
	
	@Query("SELECT mi FROM MonthlyIndicator mi WHERE mi.yearNum=?1 AND mi.indicator in ?2 order by mi.monthNum asc, mi.indicator asc")
	public List<MonthlyIndicator> findByYearNumAndIndicatorInOrderByMonthNumAsc(Integer yearNum,List<Indicator> indicators);
	
	@Query("SELECT mi FROM MonthlyIndicator mi WHERE mi.yearNum=?1 AND mi.monthNum=?2 AND mi.indicator =?3")
	public List<MonthlyIndicator> findByYearNumAndMonthNumAndIndicator(Integer yearNum,Integer monthNum,Indicator indicator);
	
	
}
