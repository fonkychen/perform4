package cn.aolc.group.performance.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.aolc.group.performance.jpa.enumeration.VocationEventType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="User_Vocation_Event")
public class UserVocationEvent implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6558856624781418573L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="vocation_id")
	private UserVocation userVocation;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="event_type")
	private VocationEventType eventType;
	
	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;
	
	@Column(name="description")
	private String description;
	
	@Column(name="day_gap")
	private Integer dayGap;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserVocation getUserVocation() {
		return userVocation;
	}

	public void setUserVocation(UserVocation userVocation) {
		this.userVocation = userVocation;
	}

	public VocationEventType getEventType() {
		return eventType;
	}

	public void setEventType(VocationEventType eventType) {
		this.eventType = eventType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDayGap() {
		return dayGap;
	}

	public void setDayGap(Integer dayGap) {
		this.dayGap = dayGap;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
