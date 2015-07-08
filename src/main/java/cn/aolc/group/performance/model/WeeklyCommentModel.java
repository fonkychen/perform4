package cn.aolc.group.performance.model;

import java.io.Serializable;

public class WeeklyCommentModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3726230955494905865L;
		
	private Long userId;
	
	private Integer scoreNum;
	
	private String taskDescription;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(Integer scoreNum) {
		this.scoreNum = scoreNum;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

}
