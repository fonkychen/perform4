package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;

public class StaffDailyBoardGroupModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6082986827274866958L;
	
	private Long countryId;
	
	private String countryName;
	
	private List<StaffDailyBoardModel> staffDailyBoardModels;

	

	public List<StaffDailyBoardModel> getStaffDailyBoardModels() {
		return staffDailyBoardModels;
	}

	public void setStaffDailyBoardModels(List<StaffDailyBoardModel> staffDailyBoardModels) {
		this.staffDailyBoardModels = staffDailyBoardModels;
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

}
