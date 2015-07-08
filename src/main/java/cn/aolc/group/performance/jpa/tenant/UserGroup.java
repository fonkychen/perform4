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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="User_Group")
public class UserGroup implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8561259128927461513L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private UserGroup parentGroup;
	
	@OneToMany(cascade=CascadeType.DETACH,fetch=FetchType.LAZY,mappedBy="parentGroup")
	private List<UserGroup> childrenGroups;
	
	@OneToMany(cascade=CascadeType.DETACH,fetch=FetchType.LAZY,mappedBy="userGroup")
	@Where(clause="user_status_id!='2'")
	private List<User> users;
	
	@OneToOne(mappedBy="ownerGroup",cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	//@JoinColumn(name="owner_id")
	private User owner;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@JsonIgnore
	public UserGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(UserGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	@JsonIgnore
	public List<UserGroup> getChildrenGroups() {
		return childrenGroups;
	}

	public void setChildrenGroups(List<UserGroup> childrenGroups) {
		this.childrenGroups = childrenGroups;
	}

	//@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@JsonIgnore
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof UserGroup)) return false;
		
		return getId().equals(((UserGroup)o).getId());
	}
	
	public int hashCode(){
		HashCodeBuilder builder=new HashCodeBuilder();
		return builder.append(getId()).append(getTenantId()).toHashCode();
	}

}
