package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.aolc.group.performance.jpa.User;

public class ScoreChartModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4610934632389658247L;
	
	private List<User> userlist;
	
	private Date startDate;
	    
	private Date endDate;
		
	private String groupBy;
		
	private List<ScoreChartItemModel> lineItems;
	
	private List<ScoreChartItemModel> pieItems;
	
	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public List<ScoreChartItemModel> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<ScoreChartItemModel> lineItems) {
		this.lineItems = lineItems;
	}

	public List<ScoreChartItemModel> getPieItems() {
		return pieItems;
	}

	public void setPieItems(List<ScoreChartItemModel> pieItems) {
		this.pieItems = pieItems;
	}

	
	
}
