package cn.aolc.group.performance.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.apache.commons.lang.builder.HashCodeBuilder;

import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Indicator")
public class Indicator implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5279761309398716023L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(name="weight",nullable=false)
	private Integer weight;
	
	@Column(name="fullfilled")
	private Integer fullfilled;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updated;
	
	@Column(name="is_valid",nullable=false)
	private Boolean isValid;
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="indicator")
	private List<MonthlyIndicator> monthlyIndicators;
	
	public boolean equals(Object o){
		if(!(o instanceof Indicator))return false;
		return ((Indicator)o).getId().equals(getId());
	}
	
	public int hashCode(){
		return (new HashCodeBuilder()).append(id).append(name).append(user.getId()).hashCode();
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getFullfilled() {
		return fullfilled;
	}

	public void setFullfilled(Integer fullfilled) {
		this.fullfilled = fullfilled;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public List<MonthlyIndicator> getMonthlyIndicators() {
		return monthlyIndicators;
	}

	public void setMonthlyIndicators(List<MonthlyIndicator> monthlyIndicators) {
		this.monthlyIndicators = monthlyIndicators;
	}

	
}
