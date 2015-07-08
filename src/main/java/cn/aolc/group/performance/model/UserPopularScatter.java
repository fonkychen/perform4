package cn.aolc.group.performance.model;

import java.io.Serializable;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.UserPopularAction;

public class UserPopularScatter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7891410713992549387L;

	private User user;
	
	private UserPopularAction popularAction;
	
	private Long count;
	
	public UserPopularScatter(){}
	
	public UserPopularScatter(User user,UserPopularAction popularAction,Long count){
		this.user=user;
		this.popularAction=popularAction;
		this.setCount(count);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public UserPopularAction getPopularAction() {
		return popularAction;
	}

	public void setPopularAction(UserPopularAction popularAction) {
		this.popularAction = popularAction;
	}

	
}
