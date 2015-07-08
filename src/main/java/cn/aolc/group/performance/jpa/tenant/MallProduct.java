package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;






import cn.aolc.group.performance.jpa.enumeration.MallProductStatus;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Mall_Product")
public class MallProduct implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7874231688852971845L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="icon",length=65500)
	private byte[] icon;
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Column(name="remain_num")
	private Integer remainNum;
	
	@Column(name="is_entity")
	private Boolean isEntity;
	
	@Column(name="service_name")
	private String serviceName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="product_status")
	private MallProductStatus productStatus;

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

	public Long getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public Boolean getIsEntity() {
		return isEntity;
	}

	public void setIsEntity(Boolean isEntity) {
		this.isEntity = isEntity;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public MallProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(MallProductStatus productStatus) {
		this.productStatus = productStatus;
	}

}
