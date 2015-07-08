package cn.aolc.group.performance.model;
import java.io.Serializable;

import cn.aolc.group.performance.jpa.tenant.TaskType;



public class StaffDailyBoardItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3824736020457618764L;	
	
	private Long id;
	
	private Integer yearNum;
	
	private Integer monthNum;
	
	private Integer dayNum;
	
	private String task;
	
	private Long userId;
	
	private Boolean isEditable;
	
	private Long taskTypeId;
	
	private TaskType taskType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public Integer getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Long getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

}
