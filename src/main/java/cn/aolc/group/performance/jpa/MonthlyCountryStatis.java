package cn.aolc.group.performance.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.Country;

@Entity
@Table(name="Monthly_Country_Statis")
public class MonthlyCountryStatis implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2035113983707589439L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@Column(name="monthNum")
	private Integer monthNum;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;
	
	@Column(name="cal_scored")
	private Integer calScored;
	
	@Column(name="cal_coin")
	private Long calCoin;
	
	@Column(name="cal_popular")
	private Integer calPopular;

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

	public Integer getCalScored() {
		return calScored;
	}

	public void setCalScored(Integer calScored) {
		this.calScored = calScored;
	}

	public Long getCalCoin() {
		return calCoin;
	}

	public void setCalCoin(Long calCoin) {
		this.calCoin = calCoin;
	}

	public Integer getCalPopular() {
		return calPopular;
	}

	public void setCalPopular(Integer calPopular) {
		this.calPopular = calPopular;
	}
	
	

}
