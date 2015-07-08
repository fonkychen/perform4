package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.aolc.group.performance.jpa.AttendEvent;
import cn.aolc.group.performance.jpa.User;

public interface AttendEventRepository extends JpaRepository<AttendEvent, Long>,JpaSpecificationExecutor<AttendEvent>{
	
	public List<AttendEvent> findByYearNumAndMonthNumAndDayNumAndUser(Integer yearNum,Integer monthNum,Integer dayNum,User user);

}
