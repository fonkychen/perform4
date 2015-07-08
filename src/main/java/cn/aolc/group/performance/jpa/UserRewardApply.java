package cn.aolc.group.performance.jpa;

import java.util.Date;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.aolc.group.performance.jpa.enumeration.UserRewardApplyStatus;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.UserReward;

@Entity
@Table(name="User_Reward_Apply")
public class UserRewardApply implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -837003873068322672L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_reward_id",nullable=false)
	private UserReward userReward;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created",nullable=false)
	private Date created;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="apply_status",nullable=false)
	private UserRewardApplyStatus applyStatus;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="rewardApply")
	private List<UserRewardResponse> responseList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserReward getUserReward() {
		return userReward;
	}

	public void setUserReward(UserReward userReward) {
		this.userReward = userReward;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public UserRewardApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(UserRewardApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public List<UserRewardResponse> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<UserRewardResponse> responseList) {
		this.responseList = responseList;
	}
	

}
