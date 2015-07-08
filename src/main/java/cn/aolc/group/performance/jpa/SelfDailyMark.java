package cn.aolc.group.performance.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.DailyMarkCategory;

@Entity
@Table(name="Self_Daily_Mark")
public class SelfDailyMark implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 582499486945683638L;
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	

	@ManyToOne
	@JoinColumn(name="mark_category_id")
	private DailyMarkCategory markCategory;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_self_mark_id")
	private UserSelfMark userSelfMark;
	
	@Column(name="start_num")
	private Integer starNum;	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public DailyMarkCategory getMarkCategory() {
		return markCategory;
	}

	public void setMarkCategory(DailyMarkCategory markCategory) {
		this.markCategory = markCategory;
	}

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

	public UserSelfMark getUserSelfMark() {
		return userSelfMark;
	}

	public void setUserSelfMark(UserSelfMark userSelfMark) {
		this.userSelfMark = userSelfMark;
	}

}
