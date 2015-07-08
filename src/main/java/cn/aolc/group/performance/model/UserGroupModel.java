package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;


public class UserGroupModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4564529808218952471L;
	
	private Long groupId;

	private Long ownerId;
	
	private List<Long> userIds;
	
	private List<UserGroupModel> children;
	
	private UserGroupModel parent;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<UserGroupModel> getChildren() {
		return children;
	}

	public void setChildren(List<UserGroupModel> children) {
		this.children = children;
	}

	public UserGroupModel getParent() {
		return parent;
	}

	public void setParent(UserGroupModel parent) {
		this.parent = parent;
	}

}
