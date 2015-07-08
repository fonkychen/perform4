package cn.aolc.group.performance.service.mallproduct;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aolc.group.performance.dao.PopularEggGuardRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.PopularEggGuard;
import cn.aolc.group.performance.jpa.User;

@Component(value="popularEggGuardService")
public class EggGuardService implements MallProductService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PopularEggGuardRepository popularEggGuardRepository;

	public void doService(User user) {
		//User user=userRepository.findOne(userId);
		PopularEggGuard pg=null;
		List<PopularEggGuard> list=popularEggGuardRepository.findByUserAndExpiredAfter(user, new Date());
		if(list.size()>0){
			pg=list.get(0);
		}
		
		if(pg==null){
			pg=new PopularEggGuard();
			pg.setCreated(new Date());
			pg.setExpired(new Date());
			pg.setUser(user);			
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(pg.getExpired());
		cal.add(Calendar.DATE, 7);
		pg.setExpired(cal.getTime());
		
		popularEggGuardRepository.save(pg);
	}

	public String getName() {		
		return "防砸蛋服务";
	}

	public String getServiceName() {
		
		return "popularEggGuardService";
	}


}
