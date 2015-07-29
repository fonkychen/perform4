package cn.aolc.group.performance.sync;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.PopularHistoryRepository;
import cn.aolc.group.performance.dao.UserPopularEventRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.PopularHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserPopularEvent;
import cn.aolc.group.performance.jpa.enumeration.PopularType;
import cn.aolc.group.performance.jpa.enumeration.UserPopularAction;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.util.exception.SyncDataException;

@Service
public class UpdatePopularService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPopularEventRepository userPopularEventRepository;
	
	@Autowired
	private PopularHistoryRepository popularHistoryRepository;
	
	public void updatePopular(PopularType popularType,EntityWithId reference) throws Exception{
		Calendar cal=Calendar.getInstance();
		PopularHistory ph=null;
		List<PopularHistory> phlist=popularHistoryRepository.findByPopularTypeAndReferenceId(popularType, reference.getId());
		if(phlist.size()>0){
			ph=phlist.get(0);
		}
		
		
		if(ph==null){
			ph=new PopularHistory();
			ph.setYearNum(cal.get(Calendar.YEAR));
			ph.setMonthNum(cal.get(Calendar.MONTH)+1);
			ph.setDayNum(cal.get(Calendar.DATE));
			ph.setPopularType(popularType);
			ph.setCreated(new Date());
			ph.setReferenceId(reference.getId());
			ph.setActualPopular(0);
		}
		int popular=0;
		User user=null;
		if(popularType.equals(PopularType.Egg)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getUser();
			ph.setUser(user);
			ph.setByUser(event.getByUser());
			popular=-1;
		}
		else if(popularType.equals(PopularType.Flower)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getUser();
			ph.setUser(user);
			ph.setByUser(event.getByUser());
			popular=2;
		}
		
		else if(popularType.equals(PopularType.PopularAction)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getUser();
			ph.setUser(user);
			ph.setByUser(event.getByUser());
			popular=event.getPopularAction().equals(UserPopularAction.ThrowEgg)?-1:2;
		}
		
		if(user==null) return;
		synchronized (user) {
			int gap=popular;
			
			if(ph.getActualPopular()!=null){
				gap=gap-ph.getActualPopular();
			}
			
			ph.setActualPopular(popular);
			
			popularHistoryRepository.save(ph);
			
			user.setUserPopular((user.getUserPopular()!=null?user.getUserPopular():0)+gap);
			userRepository.save(user);
		}
		
	}
	
//	public void updatePopular(PopularType pt,EntityWithId reference) throws Exception{
//		if(ph.getPopularType()==null || ph.getReferenceId()==null){
//			throw new Exception("invalid parameter");
//		}
//		updatePopular(ph.getPopularType(), ph.getReferenceId());
//	}

}
