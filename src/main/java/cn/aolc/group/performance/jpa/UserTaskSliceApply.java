package cn.aolc.group.performance.jpa;

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

import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceApplyStatus;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

/**
 * 
 * @author fonky
 *
 *Use this entity to store information that related to applying user task slice 
 */
@Entity
@Table(name="User_Task_Slice_Apply")
public class UserTaskSliceApply implements EntityWithId{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5960904864903810873L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="task_slice_id",nullable=false)
	private UserTaskSlice taskSlice;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="apply_status")
	private UserTaskSliceApplyStatus applyStatus;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="sliceApply")
	private List<UserTaskSliceResponse> sliceResponseList;

	public Long getId() {
		return id; 
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserTaskSlice getTaskSlice() {
		return taskSlice;
	}

	public void setTaskSlice(UserTaskSlice taskSlice) {
		this.taskSlice = taskSlice;
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

	public UserTaskSliceApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(UserTaskSliceApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public List<UserTaskSliceResponse> getSliceResponseList() {
		return sliceResponseList;
	}

	public void setSliceResponseList(List<UserTaskSliceResponse> sliceResponseList) {
		this.sliceResponseList = sliceResponseList;
	}

}
