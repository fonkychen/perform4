package cn.aolc.group.performance.jpa.tenant;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;






import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.UserTaskStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskType;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="User_Task")
public class UserTask implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5875526316433884037L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="description")
	private String description;
	
	
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="publish_date")
	private Date publishDate;
	

	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="title")
	private String title;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="task_type")
	private UserTaskType taskType;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="task_status")
	private UserTaskStatus taskStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserTaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(UserTaskType taskType) {
		this.taskType = taskType;
	}

	public UserTaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(UserTaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

}
