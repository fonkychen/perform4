package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.User;

public interface IndicatorRepository extends JpaRepository<Indicator, Long>{
	
	public List<Indicator> findByUserAndIsValidOrderByIdAsc(User user,Boolean isValid);
	
	@Query("select sum(i.weight) from Indicator i where i.user=?1 and i.isValid=?2")
	public Integer findSumTotalWeightByUserAndIsValid(User user,Boolean isValid);

}
