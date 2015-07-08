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
@Table(name="Title")
public class Title implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1730435344188242949L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name",nullable=false,unique=true)
	private String name;
	
	@Column(name="bottom")
	private Integer bottom;
	
	@Column(name="upper")
	private Integer upper;
	
	@Column(name="rank")
	private Integer rank;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@Column(name="salary_coin")
	private Long salaryCoin;
	
	@ManyToOne
	@JoinColumn(name="title_group_id")
	private TitleGroup titleGroup;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="title")	
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

	public Integer getBottom() {
		return bottom;
	}

	public void setBottom(Integer bottom) {
		this.bottom = bottom;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getSalaryCoin() {
		return salaryCoin;
	}

	public void setSalaryCoin(Long salaryCoin) {
		this.salaryCoin = salaryCoin;
	}

	public TitleGroup getTitleGroup() {
		return titleGroup;
	}

	public void setTitleGroup(TitleGroup titleGroup) {
		this.titleGroup = titleGroup;
	}

	@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	
}
