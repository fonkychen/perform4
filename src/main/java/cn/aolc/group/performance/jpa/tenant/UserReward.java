package cn.aolc.group.performance.jpa.tenant;

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

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.UserRewardStatus;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="User_Reward")
public class UserReward implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1116468693651760081L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Column(name="date_gap")
	private Integer dateGap;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="publish_date")
	private Date publishDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
//	@ElementCollection(targetClass=User.class,fetch=FetchType.EAGER)
//	@JoinTable(name="Reward_User",joinColumns=@JoinColumn(name="reward_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
//	private List<User> assignUsers;
	
	@ManyToOne
	@JoinColumn(name="by_user_id")
	private User byUser;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="reward_status")
	private UserRewardStatus rewardStatus;
//	
//	@Enumerated(EnumType.ORDINAL)
//	@Column(name="reward_type")
//	private UserRewardType rewardType;
//	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}

	public Integer getDateGap() {
		return dateGap;
	}

	public void setDateGap(Integer dateGap) {
		this.dateGap = dateGap;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}	

	public User getByUser() {
		return byUser;
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}

	public UserRewardStatus getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(UserRewardStatus rewardStatus) {
		this.rewardStatus = rewardStatus;
	}	
	
}
