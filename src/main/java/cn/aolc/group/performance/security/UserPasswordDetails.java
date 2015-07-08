package cn.aolc.group.performance.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;

public class UserPasswordDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3045531334515308458L;
	private User user;
	
	public UserPasswordDetails(User user){
		this.user=user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorites=new ArrayList<GrantedAuthority>();
		if(user==null || user.getRoles()==null)	return null;
		for(RoleType role:user.getRoles()){
			GrantedAuthority ga=new GrantedAuthorityImpl("ROLE_"+role.name());
			grantedAuthorites.add(ga);
		}
		return grantedAuthorites;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getEmailAddress();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		
		return !user.getUserStatus().equals(UserStatus.Locked);
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return !(user.getUserStatus().equals(UserStatus.Retired));
	}
	
	public int hashCode(){
		return (new HashCodeBuilder()).append(user.getId()).append(user.getName()).toHashCode();
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof UserPasswordDetails)) return false;
		UserPasswordDetails upd=(UserPasswordDetails)obj;
		if(upd.getUser().getId().equals(getUser().getId()))return true;
		return false;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public void setUser(User user){
		this.user=user;
	}
	
	public static void main(String [] args){
		ShaPasswordEncoder spe=new ShaPasswordEncoder(256);
		String enpassword=spe.encodePassword("test", null);
		System.out.println(enpassword);
	}

}
