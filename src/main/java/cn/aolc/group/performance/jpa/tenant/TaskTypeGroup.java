package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
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

import cn.aolc.group.performance.tenant.TenantEntity;


@Entity
@Table(name="Task_Type_Group")
public class TaskTypeGroup implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1926138792299441035L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name",nullable=false,unique=true)
	private String name;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@OneToMany(mappedBy="taskTypeGroup",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@OrderBy("id asc")
	private List<TaskType> taskTypes;

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

	public List<TaskType> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<TaskType> taskTypes) {
		this.taskTypes = taskTypes;
	}
	

}
