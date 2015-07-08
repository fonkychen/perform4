package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.aolc.group.performance.jpa.LuckyTable;
import cn.aolc.group.performance.jpa.User;

public interface LuckyTableRepository extends JpaRepository<LuckyTable, Long>{
	
	public List<LuckyTable> findByYearNumAndMonthNumAndDayNumAndUser(Integer yearNum,Integer monthNum,Integer dayNum,User user);

}
