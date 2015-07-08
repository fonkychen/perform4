package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.RandomPenaltyRecord;
import cn.aolc.group.performance.jpa.User;

@Transactional(propagation=Propagation.REQUIRED)
public interface RandomPenaltyRecordRepository extends JpaRepository<RandomPenaltyRecord, Long>{
	
	public List<RandomPenaltyRecord> findByUserAndYearNumAndWeekOfYear(User user,Integer yearNum,Integer weekOfYear);

}
