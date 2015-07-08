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
@Table(name="Company_Daily_Report_Item")
public class CompanyDailyReportItem implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545238671753313411L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="company_daily_report_id",nullable=false)
	private CompanyDailyReport companyDailyReport;
	
	@Column(name="description",nullable=false,columnDefinition="text")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="by_user_id")
	private User byUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CompanyDailyReport getCompanyDailyReport() {
		return companyDailyReport;
	}

	public void setCompanyDailyReport(CompanyDailyReport companyDailyReport) {
		this.companyDailyReport = companyDailyReport;
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
