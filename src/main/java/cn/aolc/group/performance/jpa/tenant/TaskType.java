package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Task_Type")
public class TaskType implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6197927069017341083L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="color",length=10)
	private String color;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	@JsonIgnore
	private TaskTypeGroup taskTypeGroup;
	
	@Column(name="is_valid")	
	private Boolean isValid;

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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public TaskTypeGroup getTaskTypeGroup() {
		return taskTypeGroup;
	}

	public void setTaskTypeGroup(TaskTypeGroup taskTypeGroup) {
		this.taskTypeGroup = taskTypeGroup;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}
