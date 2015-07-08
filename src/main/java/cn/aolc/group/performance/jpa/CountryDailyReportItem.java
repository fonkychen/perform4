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

@Entity
@Table(name="Country_Daily_Report_Item")
public class CountryDailyReportItem implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4233491441693602323L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="country_daily_report_id",nullable=false)
	private CountryDailyReport countryDailyReport;
	
	@Column(name="description",nullable=false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="by_user_id",nullable=false)
	private User byUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CountryDailyReport getCountryDailyReport() {
		return countryDailyReport;
	}

	public void setCountryDailyReport(CountryDailyReport countryDailyReport) {
		this.countryDailyReport = countryDailyReport;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getByUser() {
		return byUser;
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}

}
