package cn.aolc.group.performance.jpa;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.aolc.group.performance.jpa.enumeration.WorkOvertimeStatus;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

/**
 * 加班
 * @author fonky
 *
 */
@Entity
@Table(name="Work_Overtime")
public class WorkOvertime implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6457272922938844849L;

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
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated",nullable=false)
	private Date updated;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status",nullable=false)
	private WorkOvertimeStatus status; 
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)	
	@JoinColumns(value={@JoinColumn(name="yearNum",referencedColumnName="yearNum",insertable=false,updatable=false),@JoinColumn(name="monthNum",referencedColumnName="monthNum",insertable=false,updatable=false),@JoinColumn(name="dayNum",referencedColumnName="dayNum",insertable=false,updatable=false),@JoinColumn(name="user_id",referencedColumnName="user_id",insertable=false,updatable=false)})
	private DailyBoard dailyBoard;

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public void setId(Long id) {
		this.id=id;
	}

	public Long getId() {
		return this.id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public WorkOvertimeStatus getStatus() {
		return status;
	}

	public void setStatus(WorkOvertimeStatus status) {
		this.status = status;
	}

	public DailyBoard getDailyBoard() {
		return dailyBoard;
	}

	public void setDailyBoard(DailyBoard dailyBoard) {
		this.dailyBoard = dailyBoard;
	}

}
