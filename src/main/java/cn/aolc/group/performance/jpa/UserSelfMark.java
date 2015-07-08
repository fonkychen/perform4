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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;


@Entity
@Table(name="User_Self_Mark")
public class UserSelfMark implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1880666756264764453L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@Column(name="monthNum")
	private Integer monthNum;
	
	@Column(name="dayNum")
	private Integer dayNum;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
		
	@Column(name="actual_scored")
	private Integer actualScored;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="userSelfMark")	
	@OrderBy(" markCategory asc ")
	private List<SelfDailyMark> selfDailyMarks;
	
	@ManyToMany
	@JoinTable(name="User_Self_Mark_Indicator",joinColumns=@JoinColumn(name="user_self_mark_id",unique=false,nullable=false),inverseJoinColumns=@JoinColumn(name="indicator_id",unique=false,nullable=false))
	private List<Indicator> dailyIndicators;
	
	@Column(name="task_description",columnDefinition="TEXT")
	private String taskDescription;
	
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

	public Integer getActualScored() {
		return actualScored;
	}

	public void setActualScored(Integer actualScored) {
		this.actualScored = actualScored;
	}

	public List<SelfDailyMark> getSelfDailyMarks() {
		return selfDailyMarks;
	}

	public void setSelfDailyMarks(List<SelfDailyMark> selfDailyMarks) {
		this.selfDailyMarks = selfDailyMarks;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public List<Indicator> getDailyIndicators() {
		return dailyIndicators;
	}

	public void setDailyIndicators(List<Indicator> dailyIndicators) {
		this.dailyIndicators = dailyIndicators;
	}

	
}
