package cn.aolc.group.performance.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import cn.aolc.group.performance.jpa.enumeration.AchieveType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Achieve_Milestone")
public class AchieveMilestone implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1189798637565466881L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="achieve_type")
	private AchieveType achieveType;
	
	@Column(name="rank")
	private Integer rank;
	
	@Column(name="num")
	private Integer num;
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Column(name="description")
	private String description;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="icon",length=65500)
	private byte[] icon;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AchieveType getAchieveType() {
		return achieveType;
	}

	public void setAchieveType(AchieveType achieveType) {
		this.achieveType = achieveType;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}
