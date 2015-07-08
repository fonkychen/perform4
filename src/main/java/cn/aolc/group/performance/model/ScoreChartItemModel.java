package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;

public class ScoreChartItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4610934632389658247L;
	
	private ScoreType scoreType;
	
	private List<Object> categories;
	
	private List<Object> values;
	
	private Integer totalCount=0;
	
	public ScoreType getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
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
