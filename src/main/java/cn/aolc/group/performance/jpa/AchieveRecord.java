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

import cn.aolc.group.performance.jpa.enumeration.AchieveType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Achieve_Record")
public class AchieveRecord implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371853394614092234L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="achieve_type")
	private AchieveType achieveType;
	
	@Column(name="statis")
	private Integer statis;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public AchieveType getAchieveType() {
		return achieveType;
	}

	public void setAchieveType(AchieveType achieveType) {
		this.achieveType = achieveType;
	}

	public Integer getStatis() {
		return statis;
	}

	public void setStatis(Integer statis) {
		this.statis = statis;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
