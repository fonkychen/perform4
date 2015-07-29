package cn.aolc.group.performance.schedule;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.model.WeeklyCalendarModel;
import cn.aolc.group.performance.schedule.service.DailyScheduleService;
import cn.aolc.group.performance.schedule.service.MonthlyScheduleService;
import cn.aolc.group.performance.schedule.service.RewardTaskScheduleService;
import cn.aolc.group.performance.schedule.service.WeeklyScheduleService;
import cn.aolc.group.performance.util.PerformanceUtil;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SyncDataSchedule {
	private final static Logger logger=LoggerFactory.getLogger(SyncDataSchedule.class);
	
	@Value("${server.ismaster}")
	private int isMaster;
	
	@Autowired
	private DailyScheduleService dailyScheduleService;
	
	@Autowired
	private WeeklyScheduleService weeklyScheduleService;
	
	@Autowired
	private MonthlyScheduleService monthlyScheduleService;
	
	@Autowired
	private RewardTaskScheduleService rewardTaskScheduleService;

	public boolean isMaster() {
		return isMaster>0;
	}
		
	@Scheduled(cron="0 10 0 * * ?")
	public void runDailySchedule() throws Exception{
		if(!isMaster())return;
		//logger.debug("start runDailySchedule");
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(3);
		int yearm=cal.get(Calendar.YEAR);
		int monthm=cal.get(Calendar.MONTH)+1;
		int daym=cal.get(Calendar.DATE);
		
		Calendar yesterday=Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		int yyearm=yesterday.get(Calendar.YEAR);
		int ymonthm=yesterday.get(Calendar.MONTH)+1;
		int ydaym=yesterday.get(Calendar.DATE);
		runDailySchedule(yyearm, ymonthm, ydaym);
		
		
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
			WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
			runWeeklySchedule(wcm.getPreYearNum(), wcm.getPreWeekofyear());
		}
		
		if(daym==3){
			Calendar lastmonth=Calendar.getInstance();
			lastmonth.add(Calendar.DATE, -3);
			int lyear=lastmonth.get(Calendar.YEAR);
			int lmonth=lastmonth.get(Calendar.MONTH)+1;
			
			runMonthlySchedule(lyear, lmonth);
		}
		countDailySchedule(yyearm, ymonthm, ydaym);
		
		//calculate this day
		runDailySchedule(yearm, monthm, daym);
		
		//logger.debug("end runDailySchedule");
		
	}
	
	public void runDailySchedule(Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		dailyScheduleService.checkUserData(yearNum, monthNum, dayNum);
		rewardTaskScheduleService.doDailySchedule();
	}
	
	public void countDailySchedule(Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		dailyScheduleService.countUserData(yearNum, monthNum, dayNum);		
	}
	
	public void runWeeklySchedule(Integer yearNum,Integer weekOfYear) throws Exception{
		weeklyScheduleService.checkUserData(yearNum, weekOfYear);
		rewardTaskScheduleService.doWeeklySchedule();
	}
	
	public void runMonthlySchedule(Integer yearNum,Integer monthNum) throws Exception{
		monthlyScheduleService.checkUserData(yearNum, monthNum);
		monthlyScheduleService.checkCountryData(yearNum, monthNum);
		rewardTaskScheduleService.doMonthlySchedule();
	}

}
