package cn.aolc.group.performance.jpa.tenant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="User_Daily_Rank")
public class UserDailyRank implements TenantEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040135408356182604L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="yearNum",nullable=false)
	private Integer yearNum;
	
	@Column(name="monthNum",nullable=false)
	private Integer monthNum;
	
	@Column(name="dayNum",nullable=false)
	private Integer dayNum;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="score_rank")
	private Integer scoreRank;
	
	@Column(name="coin_rank")
	private Integer coinRank;
	
	@Column(name="popular_rank")
	private Integer popularRank;
	
	@Column(name="score")
	private Integer score;
	
	@Column(name="coin")
	private Long coin;
	
	@Column(name="popular")
	private Integer popular;
	
	@Column(name="TENANT_ID")
	private String tenantId;

	public void setId(Long id) {
		this.id=id;
	}

	public Long getId() {
		return this.id;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenentId) {
		this.tenantId=tenentId;
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

	public Integer getScoreRank() {
		return scoreRank;
	}

	public void setScoreRank(Integer scoreRank) {
		this.scoreRank = scoreRank;
	}

	public Integer getCoinRank() {
		return coinRank;
	}

	public void setCoinRank(Integer coinRank) {
		this.coinRank = coinRank;
	}

	public Integer getPopularRank() {
		return popularRank;
	}

	public void setPopularRank(Integer popularRank) {
		this.popularRank = popularRank;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getCoin() {
		return coin;
	}

	public void setCoin(Long coin) {
		this.coin = coin;
	}

	public Integer getPopular() {
		return popular;
	}

	public void setPopular(Integer popular) {
		this.popular = popular;
	}

}
