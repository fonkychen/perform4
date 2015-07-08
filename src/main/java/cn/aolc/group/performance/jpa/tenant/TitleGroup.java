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

import org.codehaus.jackson.annotate.JsonIgnore;

import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Title_Group")
public class TitleGroup implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1468741965015635758L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;	
	
	@Column(name="name")	
	private String name;
	
	@Column(name="level")
	private Integer level;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="titleGroup")
	@OrderBy(" rank asc ")
	private List<Title> titles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
	
	@com.fasterxml.jackson.annotation.JsonIgnore
	public List<Title> getTitles() {
		return titles;
	}

	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}

}
