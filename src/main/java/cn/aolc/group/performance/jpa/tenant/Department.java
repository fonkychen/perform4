package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
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
@Table(name="Department")
//@IdClass(IdentityPk.class)
public class Department implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3479921017693682850L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" level asc")
	private List<Position> positions;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
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
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
