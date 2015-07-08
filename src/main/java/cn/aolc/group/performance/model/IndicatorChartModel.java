package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.aolc.group.performance.jpa.User;

public class IndicatorChartModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7461996698756207326L;
	
    private List<User> users;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<Object> categories;
	
	private List<Object> managerItems;
	
	private List<Object> selfItems;
	
	private List<Object> settledItems;

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

	
	public List<Object> getCategories() {
		return categories;
	}

	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}

	public List<Object> getManagerItems() {
		return managerItems;
	}

	public void setManagerItems(List<Object> managerItems) {
		this.managerItems = managerItems;
	}

	public List<Object> getSelfItems() {
		return selfItems;
	}

	public void setSelfItems(List<Object> selfItems) {
		this.selfItems = selfItems;
	}

	public List<Object> getSettledItems() {
		return settledItems;
	}

	public void setSettledItems(List<Object> settledItems) {
		this.settledItems = settledItems;
	}

}
