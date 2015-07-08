package cn.aolc.group.performance.jpa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="User_Icon_Group")
public class UserIconGroup implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2175407674706537539L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="iconGroup")
	private List<UserIcon> userIconList;

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

	public List<UserIcon> getUserIconList() {
		return userIconList;
	}

	public void setUserIconList(List<UserIcon> userIconList) {
		this.userIconList = userIconList;
	}

}
