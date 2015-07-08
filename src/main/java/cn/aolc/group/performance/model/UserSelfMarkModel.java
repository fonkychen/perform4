package cn.aolc.group.performance.model;

import java.io.Serializable;

public class UserSelfMarkModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3759817786900834992L;
	
	private CategorySelfMarkModel[] csms;
	
	private Long[] indicators;
	
	private String taskDescription;

	public CategorySelfMarkModel[] getCsms() {
		return csms;
	}

	public void setCsms(CategorySelfMarkModel[] csms) {
		this.csms = csms;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Long[] getIndicators() {
		return indicators;
	}

	public void setIndicators(Long[] indicators) {
		this.indicators = indicators;
	}

}
