package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.AchieveEvent;
import cn.aolc.group.performance.jpa.AchieveMilestone;
import cn.aolc.group.performance.jpa.User;

@Transactional
public interface AchieveEventRepository extends JpaRepository<AchieveEvent, Long>{
	
	public List<AchieveEvent> findByUserAndAchieveMilestoneAndYearNum(User user,AchieveMilestone achievemilestone,Integer yearNum);

}
