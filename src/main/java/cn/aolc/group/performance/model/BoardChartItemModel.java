package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;

import cn.aolc.group.performance.jpa.tenant.TaskType;

public class BoardChartItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4322820668426294063L;
	
	private TaskType taskType;
	
	private List<Object> categories;
	
	private List<Object> values;
	
	private Integer totalCount=0;

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public List<Object> getCategories() {
		return categories;
	}

	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
