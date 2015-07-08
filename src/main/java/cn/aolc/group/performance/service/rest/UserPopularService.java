package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.annotation.UserPopularAdded;
import cn.aolc.group.performance.dao.PopularHistoryRepository;
import cn.aolc.group.performance.dao.UserPopularEventRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserPopularEvent;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.PopularType;
import cn.aolc.group.performance.jpa.enumeration.UserPopularAction;
import cn.aolc.group.performance.model.UserPopularScatter;
import cn.aolc.group.performance.util.exception.PopularLimitException;
import cn.aolc.group.performance.util.exception.PopularRepeatException;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserPopularService extends BaseRestService{
	
	@Autowired
	private PopularHistoryRepository popularHistoryRepository;
	
	@Autowired
	private UserPopularEventRepository userPopularEventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserPopularScatter> getScatter(List<User> users,Integer yearNum){
		if(yearNum==null){
			Calendar cal=Calendar.getInstance();
			yearNum=cal.get(Calendar.YEAR);
		}
		return userPopularEventRepository.countByUserInAndYearNum(users, yearNum);
	}
	
	@UserPopularAdded(popularType=PopularType.PopularAction)
	@UserCoinAdded(coinType=CoinType.Popular)
	public UserPopularEvent favorUser(Long userId,Integer type,User byUser) throws Exception{
		User user=userRepository.findOne(userId);
		if(user==null)throw new Exception("invalid user");
		if(byUser==null) byUser=getContextUser();
		
		if(user.equals(byUser)) throw new Exception("无效的操作");
		Calendar cal=Calendar.getInstance();
		Long count=userPopularEventRepository.countByYearNumAndMonthNumAndDayNumAndByUser(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), byUser);
		if(count!=null && count>=3) throw new PopularLimitException("三次机会已经用光了，明天再来吧");
		Long count2=userPopularEventRepository.countByYearNumAndMonthNumAndDayNumAndUserAndByUser(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE),user, byUser);
		if(count2!=null && count2>0) throw new PopularRepeatException("不能对同一个人重复操作");
		UserPopularEvent event=new UserPopularEvent();
		event.setByUser(byUser);
		event.setDayNum(cal.get(Calendar.DATE));
		event.setMonthNum(cal.get(Calendar.MONTH)+1);
		event.setPopularAction(type.equals(0)?UserPopularAction.ThrowEgg:UserPopularAction.SendFlower);
		event.setUpdated(new Date());
		event.setUser(user);
		event.setYearNum(cal.get(Calendar.YEAR));
		return userPopularEventRepository.save(event);
	}
	

}
