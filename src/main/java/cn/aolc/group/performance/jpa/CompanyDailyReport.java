package cn.aolc.group.performance.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Company_Daily_Report")
public class CompanyDailyReport implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 810840036258208044L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum",nullable=false)
	private Integer yearNum;
	
	@Column(name="monthNum",nullable=false)
	private Integer monthNum;
	
	@Column(name="dayNum",nullable=false)
	private Integer dayNum;
	
	@ManyToOne
	@JoinColumn(name="company_id",nullable=false)
	private Company company;
	
	@Column(name="summary",columnDefinition="text")
	private String summary;
	
	@ManyToOne
	@JoinColumn(name="by_user_id")
	private User byUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@OneToMany(mappedBy="companyDailyReport",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<CompanyDailyReportItem> items;

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

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public User getByUser() {
		return byUser;
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public List<CompanyDailyReportItem> getItems() {
		return items;
	}

	public void setItems(List<CompanyDailyReportItem> items) {
		this.items = items;
	}
	
	

}
