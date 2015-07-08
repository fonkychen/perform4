package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.AchieveMilestone;
import cn.aolc.group.performance.jpa.enumeration.AchieveType;

@Transactional
public interface AchieveMilestoneRepository extends JpaRepository<AchieveMilestone, Long>{
	
	public List<AchieveMilestone> findByAchieveTypeOrderByRankAsc(AchieveType achieveType);
	
	public List<AchieveMilestone> findByAchieveTypeOrderByRankDesc(AchieveType achieveType);
	
	public List<AchieveMilestone> findByIdNotNullOrderByIdAsc();

}
