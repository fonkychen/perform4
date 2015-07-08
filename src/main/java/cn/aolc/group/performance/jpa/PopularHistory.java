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



import cn.aolc.group.performance.jpa.enumeration.PopularType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Popular_History")
public class PopularHistory implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501935402284828483L;
	
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
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="popular_type")
	private PopularType popularType;
	
	@ManyToOne
	@JoinColumn(name="by_user_id")
	private User byUser;
	
	@Column(name="actual_popular")
	private Integer actualPopular;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Column(name="referenceId")
	private Long referenceId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;

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


	public PopularType getPopularType() {
		return popularType;
	}


	public void setPopularType(PopularType popularType) {
		this.popularType = popularType;
	}


	public User getByUser() {
		return byUser;
	}


	public void setByUser(User byUser) {
		this.byUser = byUser;
	}


	public Integer getActualPopular() {
		return actualPopular;
	}


	public void setActualPopular(Integer actualPopular) {
		this.actualPopular = actualPopular;
	}


	public Date getUpdated() {
		return updated;
	}


	public void setUpdated(Date updated) {
		this.updated = updated;
	}


	public Long getReferenceId() {
		return referenceId;
	}


	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}

}
