package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.AchieveMilestoneRepository;
import cn.aolc.group.performance.dao.AchieveRecordRepository;
import cn.aolc.group.performance.jpa.AchieveMilestone;
import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.AchieveType;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AchieveRestService extends BaseRestService{
	@Autowired
	private AchieveMilestoneRepository achieveMilestoneRepository;
	
	@Autowired
	private AchieveRecordRepository achieveRecordRepository;
	
	public List<AchieveRecord> getAchieveRecords(Integer yearNum,User user,AchieveType achieveType) throws Exception{
		if(yearNum==null){
			Calendar cal=Calendar.getInstance();
			yearNum=cal.get(Calendar.YEAR);
		}
		if(user==null){
			user=getContextUser();
		}
		if(achieveType==null) return achieveRecordRepository.findByUserAndYearNum(user, yearNum);
		else return achieveRecordRepository.findByAchieveTypeAndUserAndYearNum(achieveType, user, yearNum);
	}
	
	public List<AchieveMilestone> getAchieveMilestones(AchieveType achieveType) throws Exception{
		if(achieveType==null){
			return achieveMilestoneRepository.findByIdNotNullOrderByIdAsc();
		}
		else return achieveMilestoneRepository.findByAchieveTypeOrderByRankAsc(achieveType);
	}

}
