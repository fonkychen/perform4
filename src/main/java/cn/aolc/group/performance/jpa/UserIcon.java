package cn.aolc.group.performance.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.aolc.group.performance.jpa.inter.EntityWithId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="User_Icon")
public class UserIcon implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4846559661834838401L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;	
	
	@Column(name="name")
	private String name;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="picture",length=65500)
	private byte[] picture;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="icon_group_id")
	private UserIconGroup iconGroup;

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

	

	@JsonIgnore
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public UserIconGroup getIconGroup() {
		return iconGroup;
	}

	public void setIconGroup(UserIconGroup iconGroup) {
		this.iconGroup = iconGroup;
	}
	
	

}
