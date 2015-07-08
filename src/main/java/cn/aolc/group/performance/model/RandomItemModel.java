package cn.aolc.group.performance.model;

import java.io.Serializable;

public class RandomItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6167313800354346585L;
	
	private Long randomCatId;
	
	private Integer starNum;

	public Long getRandomCatId() {
		return randomCatId;
	}

	public void setRandomCatId(Long randomCatId) {
		this.randomCatId = randomCatId;
	}

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

}
