package cn.aolc.group.performance.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class KpiDaoAuthenticationProvider extends DaoAuthenticationProvider{
//	 @Autowired
//	 private  UserRepository userRepository;
//	 
//	 @Autowired
//	 private KpiMailSenderService mailService;
	
	 protected void additionalAuthenticationChecks(UserDetails userDetails,
	            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	        Object salt = null;

	        if (getSaltSource() != null) {
	            salt = getSaltSource().getSalt(userDetails);
	        }

	        if (authentication.getCredentials() == null) {
	            logger.debug("Authentication failed: no credentials provided");

	            throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
	        }
	        
	        

	        String presentedPassword = authentication.getCredentials().toString();

	        if (!getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
	            logger.debug("Authentication failed: password does not match stored value");
                //set failcount
//	            if(userDetails instanceof UserPasswordDetails){
//	               Long userid=((UserPasswordDetails)userDetails).getUser().getId();
//	               User user=userRepository.findOne(userid);
//	               if(user.getFailCount()!=null &&user.getFailCount()>=4){
//	            	   user.setFailCount(0);
//	            	   user.setUserStatus(UserStatus.Locked);
//	            	   mailService.sendLockNotify(user.getName(), user.getEmailAddress(), "http://www.kpi365.com", "http://www.kpi365.com");
//	               }
//	               else{
//	            	   user.setFailCount((user.getFailCount()==null?0:user.getFailCount())+1);
//	               }
//	               userRepository.save(user);
//	               userRepository.flush();
//	            }
	            throw new BadCredentialsException(messages.getMessage(
	                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
	        }
	        
	        else{
	        	if(userDetails instanceof UserPasswordDetails){
//	        		Long userid=((UserPasswordDetails)userDetails).getUser().getId();
//		            User user=userRepository.findOne(userid);
//		            user.setFailCount(0);
//		            userRepository.save(user);
//		            userRepository.flush();
	        	}
	        }
	    }

}
