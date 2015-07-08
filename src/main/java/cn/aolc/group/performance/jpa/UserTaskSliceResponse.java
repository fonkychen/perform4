package cn.aolc.group.performance.jpa;

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

import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="User_Task_Slice_Response")
public class UserTaskSliceResponse implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6487798891587261779L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="slice_apply_id")
	private UserTaskSliceApply sliceApply;
	
	@Column(name="content",nullable=false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserTaskSliceApply getSliceApply() {
		return sliceApply;
	}

	public void setSliceApply(UserTaskSliceApply sliceApply) {
		this.sliceApply = sliceApply;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	 

}
