package cn.aolc.group.performance.model;

import java.io.Serializable;

public class CategorySelfMarkModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1417433667293059720L;
	
	private Long categoryId;
	
	private Integer starNum;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

}
