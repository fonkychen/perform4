package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;

public class StaffDailyBoardModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3830845576278037884L;
//	private Integer yearNum;
//	
//	private Integer monthNum;
//	
//	private Integer dayNum;
	
	private Long userId;
	
	private String userName;
	
	private Long countryId;
	
	private String countryName;
	
	private List<StaffDailyBoardItemModel> staffDailyBoardItemModels;

	
	

	public List<StaffDailyBoardItemModel> getStaffDailyBoardItemModels() {
		return staffDailyBoardItemModels;
	}

	public void setStaffDailyBoardItemModels(
			List<StaffDailyBoardItemModel> staffDailyBoardItemModels) {
		this.staffDailyBoardItemModels = staffDailyBoardItemModels;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

//	public Integer getYearNum() {
//		return yearNum;
//	}
//
//	public void setYearNum(Integer yearNum) {
//		this.yearNum = yearNum;
//	}
//
//	public Integer getMonthNum() {
//		return monthNum;
//	}
//
//	public void setMonthNum(Integer monthNum) {
//		this.monthNum = monthNum;
//	}
//
//	public Integer getDayNum() {
//		return dayNum;
//	}
//
//	public void setDayNum(Integer dayNum) {
//		this.dayNum = dayNum;
//	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
