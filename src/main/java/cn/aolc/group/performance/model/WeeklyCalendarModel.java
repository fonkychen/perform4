package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.Calendar;

public class WeeklyCalendarModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4268771426665749702L;

	private Integer yearNum;
	
	private Integer weekofyear;
	
	private Calendar startCal;
	
	private Calendar endCal;
	
	private Integer preYearNum;
	
	private Integer preWeekofyear;
	
	private Integer nextYearNum;
	
	private Integer nextWeekofyear;

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public Integer getWeekofyear() {
		return weekofyear;
	}

	public void setWeekofyear(Integer weekofyear) {
		this.weekofyear = weekofyear;
	}

	public Calendar getStartCal() {
		return startCal;
	}

	public void setStartCal(Calendar startCal) {
		this.startCal = startCal;
	}

	public Calendar getEndCal() {
		return endCal;
	}

	public void setEndCal(Calendar endCal) {
		this.endCal = endCal;
	}

	public Integer getPreYearNum() {
		return preYearNum;
	}

	public void setPreYearNum(Integer preYearNum) {
		this.preYearNum = preYearNum;
	}

	public Integer getPreWeekofyear() {
		return preWeekofyear;
	}

	public void setPreWeekofyear(Integer preWeekofyear) {
		this.preWeekofyear = preWeekofyear;
	}

	public Integer getNextYearNum() {
		return nextYearNum;
	}

	public void setNextYearNum(Integer nextYearNum) {
		this.nextYearNum = nextYearNum;
	}

	public Integer getNextWeekofyear() {
		return nextWeekofyear;
	}

	public void setNextWeekofyear(Integer nextWeekofyear) {
		this.nextWeekofyear = nextWeekofyear;
	}
	

}
