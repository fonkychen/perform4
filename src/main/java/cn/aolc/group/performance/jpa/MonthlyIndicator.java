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

@Entity
@Table(name="Monthly_Indicator")
public class MonthlyIndicator implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1488912633462647832L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@Column(name="monthNum")
	private Integer monthNum;
	
	@ManyToOne
	@JoinColumn(name="indicator_id")
	private Indicator indicator;
	
	@ManyToOne
	@JoinColumn(name="by_user_id")
	private User byUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Column(name="progress")
	private Integer progress;
	
	@Column(name="self_progress")
	private Integer selfProgress;
	
	@Column(name="manager_progress")
	private Integer managerProgress;
	

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

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
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

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getSelfProgress() {
		return selfProgress;
	}

	public void setSelfProgress(Integer selfProgress) {
		this.selfProgress = selfProgress;
	}

	public Integer getManagerProgress() {
		return managerProgress;
	}

	public void setManagerProgress(Integer managerProgress) {
		this.managerProgress = managerProgress;
	}

}
