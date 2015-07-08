package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;

public class BalanceDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5738523431664871346L;
	
	private String date;
	
	private List<BalanceItemModel> itemList;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}
