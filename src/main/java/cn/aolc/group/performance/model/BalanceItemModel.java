package cn.aolc.group.performance.model;

import java.io.Serializable;

public class BalanceItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6291242914209217681L;
	
	
	private String incomeDetail;
	
	private Long income;
	
	private String outcomeDetail;
	
	private Long outcome;

	public String getIncomeDetail() {
		return incomeDetail;
	}

	public void setIncomeDetail(String incomeDetail) {
		this.incomeDetail = incomeDetail;
	}

	public Long getIncome() {
		return income;
	}

	public void setIncome(Long income) {
		this.income = income;
	}

	public String getOutcomeDetail() {
		return outcomeDetail;
	}

	public void setOutcomeDetail(String outcomeDetail) {
		this.outcomeDetail = outcomeDetail;
	}

	public Long getOutcome() {
		return outcome;
	}

	public void setOutcome(Long outcome) {
		this.outcome = outcome;
	}


}
