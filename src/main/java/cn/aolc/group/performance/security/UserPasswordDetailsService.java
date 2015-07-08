package cn.aolc.group.performance.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.User;

public class UserPasswordDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		List<User> userlist=userRepository.findByEmailAddressIgnoreCase(arg0);
		if(userlist==null || userlist.size()<=0) throw new UsernameNotFoundException("with email address: "+arg0);
		User user=userlist.get(0);
//		if(user.getUserStatus().equals(cn.aolc.group.performance.jpa.enumeration.UserStatus.Retired)){
//			throw new UsernameNotFoundException("expired");
//		}
		
		return new UserPasswordDetails(user);
		
	}

}
