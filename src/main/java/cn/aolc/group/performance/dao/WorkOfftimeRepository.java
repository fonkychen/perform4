package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WorkOfftime;

@Transactional(propagation=Propagation.REQUIRED)
public interface WorkOfftimeRepository extends JpaRepository<WorkOfftime, Long>{
	
	public List<WorkOfftime> findByYearNumAndMonthNumAndDayNum(Integer yearNum,Integer monthNum,Integer dayNum);
	
	public List<WorkOfftime> findByYearNumAndMonthNumAndDayNumAndUser(Integer yearNum,Integer monthNum,Integer dayNum,User user);

}
