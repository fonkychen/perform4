package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;

public class IndicatorChartItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3823307423275649752L;
	
	private String name;
	
	//private List<Object> categories;
	
	private List<Object> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<Object> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(List<Object> categories) {
//		this.categories = categories;
//	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

}
