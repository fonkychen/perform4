package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.AchieveType;

@Transactional
public interface AchieveRecordRepository extends JpaRepository<AchieveRecord, Long>{
	
	//public List<AchieveRecord> findByAchieveTypeAndUser(AchieveType achieveType,User user);
	
	public List<AchieveRecord> findByAchieveTypeAndUserAndYearNum(AchieveType achieveType,User user,Integer yearNum);
	
	public List<AchieveRecord> findByUserAndYearNum(User user,Integer yearNum);

}
