package cn.aolc.group.performance.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.Capability;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.jpa.tenant.Department;
import cn.aolc.group.performance.jpa.tenant.Position;
import cn.aolc.group.performance.jpa.tenant.Qualification;
import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.jpa.tenant.UserGroup;


@Entity
@Table(name="User")
public class User implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6318400048156172816L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="worker_id")
	private String workerId;
	
	@Column(name="email_address",length=50,nullable=false,unique=true)
	private String emailAddress;
	
	@Column(name="name",length=50,nullable=false)
	private String name;
	
	@Column(name="nick_name",length=50)
	private String nickName;
	
	@Column(name="password",length=300,nullable=false)
	private String password;
		
	@ManyToOne
	@JoinColumn(name="title_id")
	private Title title;
		
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;
	
	@OneToOne(mappedBy="owner",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Country ownerCountry;
	
	@ManyToOne
	@JoinColumn(name="position_id")
	private Position position;
	
	@ManyToOne
	@JoinColumn(name="capability_id")
	private Capability capability;
	
	@ManyToOne
	@JoinColumn(name="qualification_id")
	private Qualification qualification;
	
	@ManyToOne
	@JoinColumn(name="user_icon_id")
	private UserIcon userIcon;
	
	@Column(name="user_scored")
	private Integer userScored;
	
	@Column(name="user_coins")
	private Long userCoins;
	
	@Column(name="user_popular")
	private Integer userPopular;
	
	@ManyToOne
	@JoinColumn(name="user_group_id")
	private UserGroup userGroup;
	
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="owner_group_id")
//	private UserGroup ownerGroup;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="owner")
	private List<UserGroup> ownerGroups;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="user_status_id")
	private UserStatus userStatus;
	
	@Column(name="fail_count")
	private Integer failCount;
		
	@Enumerated(EnumType.ORDINAL)
	@ElementCollection(targetClass=RoleType.class,fetch=FetchType.EAGER)
	@JoinTable(name="User_Role",joinColumns=@JoinColumn(name="user_id"))
	@Column(name="role_id")
	private List<RoleType> roles;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("id desc ")
	private List<DailyBoard> dailyBoardList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy("id desc ")
	private List<ScoreHistory> scoreHistoryList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id desc ")
	private List<SpecialUserDate> specialUserDateList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id desc ")
	private List<UserSelfMark> userSelfMarkList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<WeeklyComment> weeklyCommentList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id asc ")
	private List<Indicator> indicatorList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id desc")
	private List<PopularHistory> popularHistoryList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id desc")
	private List<CoinHistory> coinHistoryList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id desc")	
	private List<UserMessage> userMessageList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<UserVocation> userVocationList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AchieveEvent> achieveEventList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<AchieveRecord> achieveRecordList;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<UserNotify> userNotifyList;
	
	public boolean equals(Object o){
		if(o==null || !(o instanceof User)) return false;
		return ((User)o).getId().equals(getId());
	}
	
	public int hashCode() {
		return (new HashCodeBuilder()).append(id).append(workerId).append(emailAddress).hashCode();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	@JsonIgnore
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@JsonIgnore
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@JsonIgnore
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}


	@JsonIgnore
	public Country getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(Country ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	@JsonIgnore
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@JsonIgnore
	public Capability getCapability() {
		return capability;
	}

	public void setCapability(Capability capability) {
		this.capability = capability;
	}

	@JsonIgnore
	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public UserIcon getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(UserIcon userIcon) {
		this.userIcon = userIcon;
	}

	public Integer getUserScored() {
		return userScored;
	}

	public void setUserScored(Integer userScored) {
		this.userScored = userScored;
	}

	public Long getUserCoins() {
		return userCoins;
	}

	public void setUserCoins(Long userCoins) {
		this.userCoins = userCoins;
	}

	public Integer getUserPopular() {
		return userPopular;
	}

	public void setUserPopular(Integer userPopular) {
		this.userPopular = userPopular;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	@JsonIgnore
	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
//
//	//@JsonIgnore
//	public UserGroup getOwnerGroup() {
//		return ownerGroup;
//	}
//
//	public void setOwnerGroup(UserGroup ownerGroup) {
//		this.ownerGroup = ownerGroup;
//	}

	public List<UserGroup> getOwnerGroups() {
		return ownerGroups;
	}

	public void setOwnerGroups(List<UserGroup> ownerGroups) {
		this.ownerGroups = ownerGroups;
	}

	@JsonIgnore
	public List<RoleType> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleType> roles) {
		this.roles = roles;
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

	@JsonIgnore
	public List<DailyBoard> getDailyBoardList() {
		return dailyBoardList;
	}

	public void setDailyBoardList(List<DailyBoard> dailyBoardList) {
		this.dailyBoardList = dailyBoardList;
	}


	@JsonIgnore
	public List<ScoreHistory> getScoreHistoryList() {
		return scoreHistoryList;
	}

	public void setScoreHistoryList(List<ScoreHistory> scoreHistoryList) {
		this.scoreHistoryList = scoreHistoryList;
	}


	@JsonIgnore
	public List<SpecialUserDate> getSpecialUserDateList() {
		return specialUserDateList;
	}

	public void setSpecialUserDateList(List<SpecialUserDate> specialUserDateList) {
		this.specialUserDateList = specialUserDateList;
	}


	@JsonIgnore
	public List<UserSelfMark> getUserSelfMarkList() {
		return userSelfMarkList;
	}

	public void setUserSelfMarkList(List<UserSelfMark> userSelfMarkList) {
		this.userSelfMarkList = userSelfMarkList;
	}


	@JsonIgnore
	public List<WeeklyComment> getWeeklyCommentList() {
		return weeklyCommentList;
	}

	public void setWeeklyCommentList(List<WeeklyComment> weeklyCommentList) {
		this.weeklyCommentList = weeklyCommentList;
	}


	@JsonIgnore
	public List<Indicator> getIndicatorList() {
		return indicatorList;
	}

	public void setIndicatorList(List<Indicator> indicatorList) {
		this.indicatorList = indicatorList;
	}


	@JsonIgnore
	public List<PopularHistory> getPopularHistoryList() {
		return popularHistoryList;
	}

	public void setPopularHistoryList(List<PopularHistory> popularHistoryList) {
		this.popularHistoryList = popularHistoryList;
	}

	@JsonIgnore
	public List<CoinHistory> getCoinHistoryList() {
		return coinHistoryList;
	}

	public void setCoinHistoryList(List<CoinHistory> coinHistoryList) {
		this.coinHistoryList = coinHistoryList;
	}

	@JsonIgnore
	public List<UserMessage> getUserMessageList() {
		return userMessageList;
	}

	public void setUserMessageList(List<UserMessage> userMessageList) {
		this.userMessageList = userMessageList;
	}

	@JsonIgnore
	public List<UserVocation> getUserVocationList() {
		return userVocationList;
	}

	public void setUserVocationList(List<UserVocation> userVocationList) {
		this.userVocationList = userVocationList;
	}

	@JsonIgnore
	public List<AchieveEvent> getAchieveEventList() {
		return achieveEventList;
	}

	public void setAchieveEventList(List<AchieveEvent> achieveEventList) {
		this.achieveEventList = achieveEventList;
	}

	@JsonIgnore
	public List<AchieveRecord> getAchieveRecordList() {
		return achieveRecordList;
	}

	public void setAchieveRecordList(List<AchieveRecord> achieveRecordList) {
		this.achieveRecordList = achieveRecordList;
	}

	@JsonIgnore
	public List<UserNotify> getUserNotifyList() {
		return userNotifyList;
	}

	public void setUserNotifyList(List<UserNotify> userNotifyList) {
		this.userNotifyList = userNotifyList;
	}
	


}


