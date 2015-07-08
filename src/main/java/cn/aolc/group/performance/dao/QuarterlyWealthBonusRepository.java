package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.QuarterlyWealthBonus;
import cn.aolc.group.performance.jpa.User;

@Transactional(propagation=Propagation.REQUIRED)
public interface QuarterlyWealthBonusRepository extends JpaRepository<QuarterlyWealthBonus, Long>{
	
	public List<QuarterlyWealthBonus> findByUserAndYearNumAndQuarter(User user,Integer yearNum,Integer quarter);

}
