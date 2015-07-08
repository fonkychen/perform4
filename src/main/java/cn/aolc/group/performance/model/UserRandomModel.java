package cn.aolc.group.performance.model;

import java.io.Serializable;

public class UserRandomModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3770547414997475833L;
	
	private Long randomId;
	
	private Long userId;
	
	private RandomItemModel[] rimodels;
	
	private String description;

	public Long getRandomId() {
		return randomId;
	}

	public void setRandomId(Long randomId) {
		this.randomId = randomId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public RandomItemModel[] getRimodels() {
		return rimodels;
	}

	public void setRimodels(RandomItemModel[] rimodels) {
		this.rimodels = rimodels;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
