package cn.aolc.group.performance.model;

import java.io.Serializable;

public class AchieveModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953382133472268703L;
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private String achieveName;
	
	private String achieveType;
	
	private Long coinnum;
	
	private Integer record;
	
	private Integer num;
	
	private Boolean isOn;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAchieveName() {
		return achieveName;
	}

	public void setAchieveName(String achieveName) {
		this.achieveName = achieveName;
	}

	public String getAchieveType() {
		return achieveType;
	}

	public void setAchieveType(String achieveType) {
		this.achieveType = achieveType;
	}

	public Long getCoinnum() {
		return coinnum;
	}

	public void setCoinnum(Long coinnum) {
		this.coinnum = coinnum;
	}

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Boolean getIsOn() {
		return isOn;
	}

	public void setIsOn(Boolean isOn) {
		this.isOn = isOn;
	}
	
	

}
