package cn.aolc.group.performance.jpa.tenant;

import java.io.Serializable;
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





import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.tenant.TenantEntity;

@Entity
@Table(name="Mall_Order")
public class MallOrder implements TenantEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8472138394663863078L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private MallProduct mallProduct;
	
	@ManyToOne
	@JoinColumn(name="order_user_id")
	private User orderUser;
	
	@Column(name="coin_num")
	private Long coinNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Column(name="checked")
	private Boolean checked;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="checked_time")
	private Date checkedTime;
	
	@Column(name="TENANT_ID")
	private String tenantId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MallProduct getMallProduct() {
		return mallProduct;
	}

	public void setMallProduct(MallProduct mallProduct) {
		this.mallProduct = mallProduct;
	}

	public User getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(User orderUser) {
		this.orderUser = orderUser;
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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
