package cn.aolc.group.performance.weixin.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.aolc.group.performance.jpa.User;

@Entity
@Table(name="Wechat_User_Mapping")
public class WechatUserMapping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7502137535030753259L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false,unique=true)
	private User user;
	
	@Column(name="open_id",nullable=false)
	private String openId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
