package cn.aolc.group.performance.jpa.tenant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;






import cn.aolc.group.performance.jpa.WorkOvertime;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Calendar_Record")
public class CalendarRecord implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8816646387623517149L;
	
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
	
	@Column(name="enabled")
	private Boolean enabled;
	
//	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
//	@JoinColumns(value={@JoinColumn(name="yearNum",referencedColumnName="yearNum"),@JoinColumn(name="monthNum",referencedColumnName="monthNum"),@JoinColumn(name="dayNum",referencedColumnName="dayNum")})
//	private List<SpecialUserDate> specialUserDates;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumns(value={@JoinColumn(name="yearNum",referencedColumnName="yearNum"),@JoinColumn(name="monthNum",referencedColumnName="monthNum"),@JoinColumn(name="dayNum",referencedColumnName="dayNum")})
	private List<WorkOvertime> workOvertimes;
	
	@Column(name="TENANT_ID")
	private String tenantId;

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

//	public List<SpecialUserDate> getSpecialUserDates() {
//		return specialUserDates;
//	}
//
//	public void setSpecialUserDates(List<SpecialUserDate> specialUserDates) {
//		this.specialUserDates = specialUserDates;
//	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public List<WorkOvertime> getWorkOvertimes() {
		return workOvertimes;
	}

	public void setWorkOvertimes(List<WorkOvertime> workOvertimes) {
		this.workOvertimes = workOvertimes;
	}

}
