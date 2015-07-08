package cn.aolc.group.performance.jpa.tenant;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;






import org.hibernate.annotations.Where;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Capability")
public class Capability implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5724145662695950946L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name",nullable=false,unique=true)
	private String name;
	
	@Column(name="level")
	private Integer level;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@OneToMany(mappedBy="capability",cascade=CascadeType.ALL,fetch=FetchType.LAZY)	
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
