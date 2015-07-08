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

import cn.aolc.group.performance.jpa.enumeration.BalanceType;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Coin_History")
public class CoinHistory implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1976007640995708072L;
	
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
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="coin_type")
	private CoinType coinType;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="balance_type")
	private BalanceType balanceType;
	
	@Column(name="reference_id")
	private Long referenceId;
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date created;
	
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

	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

	public BalanceType getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}


}
