package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WeeklyPenaltyRecord;

@Transactional(propagation=Propagation.REQUIRED)
public interface WeeklyPenaltyRecordRepository extends JpaRepository<WeeklyPenaltyRecord, Long>{
	
	public List<WeeklyPenaltyRecord> findByUserAndYearNumAndWeekOfYear(User user,Integer yearNum,Integer weekOfYear);

}
