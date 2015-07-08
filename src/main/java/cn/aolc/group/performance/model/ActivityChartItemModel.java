package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;


public class ActivityChartItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5982611250871103142L;
	

	private String name;
	
	private List<Object> categories;
	
	private List<Object> values;
	
	private double actualCount=0;
	
	private double countRate=0d; 
	
	private double supposedcount=0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getActualCount() {
		return actualCount;
	}

	public void setActualCount(double actualCount) {
		this.actualCount = actualCount;
	}

	public double getCountRate() {
		return countRate;
	}

	public void setCountRate(double countRate) {
		this.countRate = countRate;
	}

	public double getSupposedcount() {
		return supposedcount;
	}

	public void setSupposedcount(double supposedcount) {
		this.supposedcount = supposedcount;
	}

	
}
