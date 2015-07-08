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

import cn.aolc.group.performance.jpa.enumeration.VocationType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="User_Vocation")
public class UserVocation implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8227854887197240899L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="vocation_type")
	private VocationType vocationType;
	
	@Column(name="limit_days")
	private Integer limitDays;
	
	@Column(name="actualDays")
	private Integer actualDays;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="userVocation")	
	private List<UserVocationEvent> vocationEvents;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VocationType getVocationType() {
		return vocationType;
	}

	public void setVocationType(VocationType vocationType) {
		this.vocationType = vocationType;
	}

	public Integer getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}

	public Integer getActualDays() {
		return actualDays;
	}

	public void setActualDays(Integer actualDays) {
		this.actualDays = actualDays;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public List<UserVocationEvent> getVocationEvents() {
		return vocationEvents;
	}

	public void setVocationEvents(List<UserVocationEvent> vocationEvents) {
		this.vocationEvents = vocationEvents;
	}

}
