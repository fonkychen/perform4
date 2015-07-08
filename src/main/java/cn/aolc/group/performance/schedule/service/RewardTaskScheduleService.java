package cn.aolc.group.performance.schedule.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.UserTaskSliceRepository;
import cn.aolc.group.performance.dao.tenant.UserRewardRepository;
import cn.aolc.group.performance.dao.tenant.UserTaskRepository;
import cn.aolc.group.performance.jpa.UserTaskSlice;
import cn.aolc.group.performance.jpa.enumeration.UserRewardStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskType;
import cn.aolc.group.performance.jpa.tenant.UserReward;
import cn.aolc.group.performance.jpa.tenant.UserTask;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RewardTaskScheduleService {
	
	@Autowired
	private UserRewardRepository userRewardRepository;
	
	@Autowired
	private UserTaskRepository userTaskRepository;	
	
	@Autowired
	private UserTaskSliceRepository userTaskSliceRepository;
	
	public void doDailySchedule() throws Exception{
		//user reward
		updateRewardStatus();
		//create user task slice 
		List<UserTask> utlist=userTaskRepository.findByTaskTypeAndTaskStatus(UserTaskType.Daily, UserTaskStatus.OnGoing);
		for(UserTask ut:utlist){
			createTaskSlice(ut);
		}
	}
	
	public void doWeeklySchedule() throws Exception{
		//create user task slice
		List<UserTask> utlist=userTaskRepository.findByTaskTypeAndTaskStatus(UserTaskType.Weekly, UserTaskStatus.OnGoing);
		for(UserTask ut:utlist){
			createTaskSlice(ut);
		}
	}
	
	public void doMonthlySchedule() throws Exception{
		//create user task slice
		List<UserTask> utlist=userTaskRepository.findByTaskTypeAndTaskStatus(UserTaskType.Monthly, UserTaskStatus.OnGoing);
		for(UserTask ut:utlist){
			createTaskSlice(ut);
		}
	}
	
	private void updateRewardStatus() throws Exception{
		List<UserReward> urlist=userRewardRepository.findByRewardStatusAndEndDateBefore(UserRewardStatus.OnGoing, new Date());
		for(UserReward ur:urlist){
			ur.setRewardStatus(UserRewardStatus.Closed);			
		}
		userRewardRepository.save(urlist);		
	}
	
	private void createTaskSlice(UserTask userTask) throws Exception{
		UserTaskSlice uts=new UserTaskSlice();
		Calendar cal=Calendar.getInstance();
		uts.setCreated(new Date());
		uts.setDayNum(cal.get(Calendar.DATE));
		uts.setMonthNum(cal.get(Calendar.MONTH)+1);
		uts.setSliceStatus(UserTaskSliceStatus.OnGoing);
		uts.setUpdated(new Date());
		uts.setUserTask(userTask);
		uts.setWeekOfYear(cal.get(Calendar.WEEK_OF_YEAR));
		uts.setYearNum(cal.get(Calendar.YEAR));
		userTaskSliceRepository.save(uts);
	}

}
