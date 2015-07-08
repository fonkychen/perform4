package cn.aolc.group.performance.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;










import cn.aolc.group.performance.jpa.enumeration.AttendEventType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Attend_Event")
public class AttendEvent implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1805440085815206912L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@Column(name="monthNum")
	private Integer monthNum;
	
	@Column(name="dayNum")
	private Integer dayNum;	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="type")
	private AttendEventType type;
//	@Column(name="TENANT_ID")
//	private String tenantId;

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

	public Integer getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public AttendEventType getType() {
		return type;
	}

	public void setType(AttendEventType type) {
		this.type = type;
	}
	
//	public String getTenantId() {
//		return tenantId;
//	}
//
//	public void setTenantId(String tenantId) {
//		this.tenantId = tenantId;
//	}

}
