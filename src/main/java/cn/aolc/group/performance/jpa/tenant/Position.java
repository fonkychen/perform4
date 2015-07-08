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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.tenant.TenantEntity;
@Entity
@Table(name="Position")
public class Position implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 980360937248920084L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="level")
	private Integer level;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@OneToMany(mappedBy="position",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id asc ")	
	@Where(clause="user_status_id!='2'")
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
