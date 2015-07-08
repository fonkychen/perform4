package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.aolc.group.performance.tenant.TenantEntity;


@Entity
@Table(name="Daily_Mark_Category")
public class DailyMarkCategory implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3361188052003348947L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="percentage")
	private Double percentage;
	
	@Column(name="star_tip1")
	private String starTip1;
	
	@Column(name="star_tip2")
	private String starTip2;
	
	@Column(name="star_tip3")
	private String starTip3;
	
	@Column(name="star_tip4")
	private String starTip4;
	
	@Column(name="star_tip5")
	private String starTip5;
	
	@Column(name="TENANT_ID")
	private String tenantId;

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

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getStarTip1() {
		return starTip1;
	}

	public void setStarTip1(String starTip1) {
		this.starTip1 = starTip1;
	}

	public String getStarTip2() {
		return starTip2;
	}

	public void setStarTip2(String starTip2) {
		this.starTip2 = starTip2;
	}

	public String getStarTip3() {
		return starTip3;
	}

	public void setStarTip3(String starTip3) {
		this.starTip3 = starTip3;
	}

	public String getStarTip4() {
		return starTip4;
	}

	public void setStarTip4(String starTip4) {
		this.starTip4 = starTip4;
	}

	public String getStarTip5() {
		return starTip5;
	}

	public void setStarTip5(String starTip5) {
		this.starTip5 = starTip5;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
