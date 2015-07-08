package cn.aolc.group.performance.jpa;

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

import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.WeiboTopic;

@Entity
@Table(name="Weibo_Reply")
public class WeiboReply implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1197880767518987723L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="content")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="weibo_topic_id")
	private WeiboTopic weiboTopic;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="weibo_reply_id")
	private WeiboReply replyTo;
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="User_Favor_Weibo_Reply",joinColumns=@JoinColumn(name="weibo_reply_id",nullable=false),inverseJoinColumns=@JoinColumn(name="user_id",nullable=false))
	private List<User> favorUsers;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="replyTo")
	private List<WeiboReply> replyBy;
	
	@Column(name="favor_num")
	private Integer favorNum;
	
	@Column(name="reply_num")
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

	public WeiboTopic getWeiboTopic() {
		return weiboTopic;
	}

	public void setWeiboTopic(WeiboTopic weiboTopic) {
		this.weiboTopic = weiboTopic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public WeiboReply getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(WeiboReply replyTo) {
		this.replyTo = replyTo;
	}

	@JsonIgnore
	public List<User> getFavorUsers() {
		return favorUsers;
	}

	public void setFavorUsers(List<User> favorUsers) {
		this.favorUsers = favorUsers;
	}

	@JsonIgnore
	public List<WeiboReply> getReplyBy() {
		return replyBy;
	}

	public void setReplyBy(List<WeiboReply> replyBy) {
		this.replyBy = replyBy;
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
