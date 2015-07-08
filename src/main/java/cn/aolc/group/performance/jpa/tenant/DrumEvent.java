package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;
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






import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.DrumStatusType;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Drum_Event")
public class DrumEvent implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4848490715042370492L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="event")
	private String event;
	
	@Column(name="yearNum")
	private Integer yearNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status_type")
	private DrumStatusType statusType;  
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
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

	public DrumStatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(DrumStatusType statusType) {
		this.statusType = statusType;
	}

}
