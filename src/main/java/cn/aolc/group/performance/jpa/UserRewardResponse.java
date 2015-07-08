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

import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="User_Reward_Response")
public class UserRewardResponse implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2108062911458610468L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="reward_apply_id",nullable=false)
	private UserRewardApply rewardApply;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="content")
	private String content;
	
	@Column(name="created")
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRewardApply getRewardApply() {
		return rewardApply;
	}

	public void setRewardApply(UserRewardApply rewardApply) {
		this.rewardApply = rewardApply;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
