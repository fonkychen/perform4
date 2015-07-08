package cn.aolc.group.performance.util;

import java.util.Calendar;

import cn.aolc.group.performance.model.WeeklyCalendarModel;

public class PerformanceUtil {
	
	/**
	 * always set Friday
	 * 总是星期五
	 * @return
	 */
	public static Calendar getWeekend(){
		Calendar cal=Calendar.getInstance();
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			cal.add(Calendar.DATE, -2);
		}
		else{
			int windex=cal.get(Calendar.DAY_OF_WEEK);
			cal.add(Calendar.DATE, 6-windex);
		}
		return cal;
	}
	
	public static Calendar getFirtdayOfWeek(Calendar cal){
		Calendar tmpcal=(Calendar)cal.clone();		
		int weekindex=cal.get(Calendar.DAY_OF_WEEK);
		tmpcal.add(Calendar.DATE, (weekindex-2)*-1);
		return tmpcal;
	}
	
	public static Calendar getEnddayOfWeek(Calendar cal){
		Calendar tmpcal=(Calendar)cal.clone();		
		int weekindex=cal.get(Calendar.DAY_OF_WEEK);
		tmpcal.add(Calendar.DATE, 8-weekindex);
		return tmpcal;
	}
	
	public static WeeklyCalendarModel getWeeklyCalendar(){
		return getWeeklyCalendar(null);
	}
	
	public static WeeklyCalendarModel getWeeklyCalendar(Integer yearm,Integer weekofyear){
		WeeklyCalendarModel model=new WeeklyCalendarModel();
		model.setYearNum(yearm);
		
		model.setWeekofyear(weekofyear);
		
		int preyear=0,preweekofyear=0,nextyear=0,nextweekofyear=0;
		Calendar scal=Calendar.getInstance();
		scal.setMinimalDaysInFirstWeek(3);//星期五所在的周次
		scal.setWeekDate(yearm, weekofyear, Calendar.MONDAY);
		model.setStartCal((Calendar) scal.clone());
		
		Calendar ecal=(Calendar) scal.clone();
		ecal.add(Calendar.DATE, 6);
		model.setEndCal((Calendar) ecal.clone());
		
		Calendar pcal=(Calendar)scal.clone();
		pcal.add(Calendar.DATE, -3);//上周五
		
		preyear=pcal.get(Calendar.YEAR);
		preweekofyear=pcal.get(Calendar.WEEK_OF_YEAR);
		model.setPreYearNum(preyear);
		model.setPreWeekofyear(preweekofyear);
		
		pcal.add(Calendar.DATE,14);//下周五
		nextyear=pcal.get(Calendar.YEAR);
		nextweekofyear=pcal.get(Calendar.WEEK_OF_YEAR);
		model.setNextYearNum(nextyear);
		model.setNextWeekofyear(nextweekofyear);
		
		return model;
	}
	
	public static WeeklyCalendarModel getWeeklyCalendar(Calendar paramcal){
		Calendar cal=null;
		if(paramcal==null){
			cal=Calendar.getInstance();
		}
		else{
			cal=(Calendar) paramcal.clone();
		}
		
		////should roll to Friday
		int weekindex=cal.get(Calendar.DAY_OF_WEEK);//Sunday is 1;
		if(weekindex == Calendar.SUNDAY){
			cal.add(Calendar.DATE, -2);
		}
		else{
			cal.add(Calendar.DATE, 6-weekindex);
		}		
		return getWeeklyCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR));	
	}

}
