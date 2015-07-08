package cn.aolc.group.performance.jpa.tenant;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WeiboReply;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Weibo_Topic")
public class WeiboTopic implements TenantEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8703711023180055742L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;	
	
	@Column(name="content")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="degree")
	private Double degree;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="User_Favor_Weibo_Topic",joinColumns=@JoinColumn(name="weibo_topic_id",nullable=false),inverseJoinColumns=@JoinColumn(name="user_id",nullable=false))
	private List<User> favorUsers;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="weiboTopic")	
	private List<WeiboReply> weiboReplys;
	
	private Integer favorNum;
	
	private Integer replyNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getDegree() {
		return degree;
	}

	public void setDegree(Double degree) {
		this.degree = degree;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenentId) {
		this.tenantId=tenentId;
	}

	@JsonIgnore
	public List<User> getFavorUsers() {
		return favorUsers;
	}

	public void setFavorUsers(List<User> favorUsers) {
		this.favorUsers = favorUsers;
	}

	@JsonIgnore
	public List<WeiboReply> getWeiboReplys() {
		return weiboReplys;
	}

	public void setWeiboReplys(List<WeiboReply> weiboReplys) {
		this.weiboReplys = weiboReplys;
	}

	public Integer getFavorNum() {
		return favorNum;
	}

	public void setFavorNum(Integer favorNum) {
		this.favorNum = favorNum;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}

}
