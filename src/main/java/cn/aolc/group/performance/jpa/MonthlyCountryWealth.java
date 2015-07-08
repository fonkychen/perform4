package cn.aolc.group.performance.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.Country;

@Entity
@Table(name="Monthly_Country_Wealth")
public class MonthlyCountryWealth implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1072170718848142063L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@Column(name="monthNum")
	private Integer monthNum;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;
	
	@Column(name="income")
	private Double income;
	
	@Column(name="outcome")
	private Double outcome;
	
	@Column(name="settled")
	private Double settled;
	
	@Column(name="description")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public Integer getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getOutcome() {
		return outcome;
	}

	public void setOutcome(Double outcome) {
		this.outcome = outcome;
	}

	public Double getSettled() {
		return settled;
	}

	public void setSettled(Double settled) {
		this.settled = settled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
