package cn.aolc.group.performance.model;

import java.io.Serializable;

public class UserEditModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 94099565254014986L;
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String nickName;
	
	private String workerId;
	
	private Long countryId;
	
	private Long positionId;
	
	private Long titleGroupId;
	
	private Long capabilityId;
	
	private Long qualificationId;
	
	private Long userStatusId;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getTitleGroupId() {
		return titleGroupId;
	}

	public void setTitleGroupId(Long titleGroupId) {
		this.titleGroupId = titleGroupId;
	}

	public Long getCapabilityId() {
		return capabilityId;
	}

	public void setCapabilityId(Long capabilityId) {
		this.capabilityId = capabilityId;
	}

	public Long getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Long qualificationId) {
		this.qualificationId = qualificationId;
	}

	public Long getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(Long userStatusId) {
		this.userStatusId = userStatusId;
	}

}
