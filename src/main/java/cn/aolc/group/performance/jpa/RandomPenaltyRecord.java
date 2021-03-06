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
@Table(name="Random_Penalty_Record")
public class RandomPenaltyRecord implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6252318736450451035L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum",nullable=false)
	private Integer yearNum;
	
	@Column(name="week_of_year",nullable=false)
	private Integer weekOfYear;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="score_num",nullable=false)
	private Integer scoreNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated",nullable=false)
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

	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(Integer scoreNum) {
		this.scoreNum = scoreNum;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
