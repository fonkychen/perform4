package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.aolc.group.performance.jpa.User;

public class ActivityChartModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -440875166089766163L;
	
    private List<User> users;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<ActivityChartItemModel> items;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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

	public List<ActivityChartItemModel> getItems() {
		return items;
	}

	public void setItems(List<ActivityChartItemModel> items) {
		this.items = items;
	}


}
