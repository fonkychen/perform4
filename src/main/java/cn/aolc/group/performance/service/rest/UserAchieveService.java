package cn.aolc.group.performance.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.AchieveMilestoneRepository;
import cn.aolc.group.performance.jpa.AchieveMilestone;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserAchieveService extends BaseRestService{
	@Autowired
	private AchieveMilestoneRepository achieveMilestoneRepository;
	
	public AchieveMilestone get(Long id) throws Exception{
		return achieveMilestoneRepository.findOne(id);
	}

}
