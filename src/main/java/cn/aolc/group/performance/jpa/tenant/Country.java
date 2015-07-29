package cn.aolc.group.performance.jpa.tenant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.jpa.MonthlyCountryStatis;
import cn.aolc.group.performance.jpa.MonthlyCountryWealth;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.tenant.TenantEntity;
@Entity
@Table(name="Country")
public class Country implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 911940610198648812L;
	
	private Long id;
	
	private String no;
	
	private String name;
	
	private Byte[] icon;

	private String color;
	
	private String memberComp;
	
	
	private String tenantId;
	
	private List<User> users;
	
	private User owner;
	
	private List<MonthlyCountryStatis> monthlyCountryStatisList;
	
	//private List<UserGroup> userGroupList;
	
	private List<MonthlyCountryWealth> monthlyCountryWealthList;
	
	private Double wealth;
	
	public boolean equals(Object o){
		if(o==null || !(o instanceof Country)) return false;
		return ((Country)o).getId().equals(id);
	}
	
	public int hashCode() {
		return (new HashCodeBuilder()).append(id).append(name).append(tenantId).hashCode();
	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name="no")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name="name",nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@JsonIgnore
	@Column(name="icon")
	public Byte[] getIcon() {
		return icon;
	}

	public void setIcon(Byte[] icon) {
		this.icon = icon;
	}

	@Column(name="color",length=10)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name="member_comp")
	public String getMemberComp() {
		return memberComp;
	}

	public void setMemberComp(String memberComp) {
		this.memberComp = memberComp;
	}
	
	@Column(name="TENANT_ID")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="country")
	@OrderBy(" id asc ")
	@Where(clause="user_status_id!='2'")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


	
	@OneToOne
	@JoinColumn(name="owner_id")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}


	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="country")
	@OrderBy("id desc")
	public List<MonthlyCountryStatis> getMonthlyCountryStatisList() {
		return monthlyCountryStatisList;
	}

	public void setMonthlyCountryStatisList(List<MonthlyCountryStatis> monthlyCountryStatisList) {
		this.monthlyCountryStatisList = monthlyCountryStatisList;
	}

//
//	@JsonIgnore
//	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="country")
//	@OrderBy(" id asc ")
//	public List<UserGroup> getUserGroupList() {
//		return userGroupList;
//	}
//
//	public void setUserGroupList(List<UserGroup> userGroupList) {
//		this.userGroupList = userGroupList;
//	}

	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="country")
	@OrderBy("updated asc")
	public List<MonthlyCountryWealth> getMonthlyCountryWealthList() {
		return monthlyCountryWealthList;
	}

	public void setMonthlyCountryWealthList(List<MonthlyCountryWealth> monthlyCountryWealthList) {
		this.monthlyCountryWealthList = monthlyCountryWealthList;
	}

	@Column(name="wealth")
	public Double getWealth() {
		return wealth;
	}

	public void setWealth(Double wealth) {
		this.wealth = wealth;
	}

}
