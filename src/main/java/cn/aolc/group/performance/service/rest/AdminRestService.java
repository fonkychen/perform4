package cn.aolc.group.performance.service.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;

@Service("adminRestService")
@Transactional(propagation=Propagation.REQUIRED)
@Secured({ "ROLE_ADMIN" })
public class AdminRestService extends BaseRestService{
	
	public void save(User user) throws Exception{
		
	}
	
//	public User create() throws Exception{
//		
//	}

}
