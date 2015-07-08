package cn.aolc.group.performance.service.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.IndicatorRepository;
import cn.aolc.group.performance.dao.MonthlyCountryStatisRepository;
import cn.aolc.group.performance.dao.MonthlyIndicatorRepository;
import cn.aolc.group.performance.dao.MonthlyIndicatorStatisRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.MonthlyIndicator;
import cn.aolc.group.performance.jpa.MonthlyIndicatorStatis;
import cn.aolc.group.performance.jpa.User;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserIndicatorService extends BaseRestService{
	private final static Logger logger=LoggerFactory.getLogger(UserIndicatorService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IndicatorRepository indicatorRepository;
	
	@Autowired
	private MonthlyIndicatorRepository monthlyIndicatorRepository;
	
	@Autowired
	private MonthlyIndicatorStatisRepository monthlyIndicatorStatisRepository;
	
	@Autowired
	private MonthlyCountryStatisRepository monthlyCountryStatisRepository;
	
	public MonthlyIndicator saveSelfProgress(Long indicatorId,Integer selfProgress) throws Exception{
		Indicator indicator=indicatorRepository.findOne(indicatorId);
		User user=getContextUser();
		if(user == null || !user.equals(indicator.getUser())) throw new Exception("没有权限");
		Calendar cal=Calendar.getInstance();
		MonthlyIndicator mi=null;
		List<MonthlyIndicator> milist=monthlyIndicatorRepository.findByYearNumAndMonthNumAndIndicator(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, indicator);
		if(milist.size()>0){
			mi=milist.get(0);
		}
		
		if(mi==null){
			mi=new MonthlyIndicator();
			mi.setIndicator(indicator);
			mi.setMonthNum(cal.get(Calendar.MONTH)+1);
			mi.setYearNum(cal.get(Calendar.YEAR));
			mi.setByUser(user);
		}
		
		mi.setSelfProgress(selfProgress);
		if(user.getUserGroup()!=null){
			mi.setProgress(Double.valueOf(selfProgress*0.3+(mi.getManagerProgress()==null?0:(mi.getManagerProgress()*0.7))).intValue());
		}
		else{
			mi.setProgress(selfProgress);
		}
		
		mi.setUpdated(new Date());
		return monthlyIndicatorRepository.save(mi);
	}
	
	public MonthlyIndicator saveManProgress(Long userId,Long indicatorId,String name,Integer weight,Integer manProgress) throws Exception{
		Indicator indicator=null;
		User user=userRepository.findOne(userId);
		User byUser=getContextUser();
		if(user==null || !isAuthorized(byUser, user)) throw new Exception("无效的用户");
		int origweight=0;
		if(indicatorId!=null && indicatorId>0){
			indicator=indicatorRepository.findOne(indicatorId);
			origweight=indicator.getWeight();
		}
		
		
		Integer totalweight=indicatorRepository.findSumTotalWeightByUserAndIsValid(user, true);
		if(totalweight==null) totalweight=0;
		if(totalweight+weight-origweight>100){
			throw new cn.aolc.group.performance.util.exception.IndicatorWeightExceedException("权重总和不能超过一百");
		}

		if(indicator==null){
			indicator=new Indicator();			
		}
		indicator.setIsValid(true);
		indicator.setUser(user);
		indicator.setName(name);
		indicator.setWeight(weight);
		indicator.setUpdated(new Date());
		indicator=indicatorRepository.save(indicator);


		
		Calendar cal=Calendar.getInstance();
		MonthlyIndicator mi=null;
		List<MonthlyIndicator> milist=monthlyIndicatorRepository.findByYearNumAndMonthNumAndIndicator(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, indicator);
		if(milist.size()>0){
			mi=milist.get(0);
		}
		
		if(mi==null){
			mi=new MonthlyIndicator();
			mi.setIndicator(indicator);
			mi.setMonthNum(cal.get(Calendar.MONTH)+1);
			mi.setYearNum(cal.get(Calendar.YEAR));
			mi.setByUser(user);
			mi.setSelfProgress(0);
		}
		
		mi.setManagerProgress(manProgress);
		mi.setProgress(Double.valueOf(manProgress*0.7+(mi.getSelfProgress()==null?0:(mi.getSelfProgress()*0.3))).intValue());
		mi.setUpdated(new Date());
		return monthlyIndicatorRepository.save(mi);
	}
	
	public List<MonthlyIndicator> getMonthlyIndicatorsByUser(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		List<Indicator> indicators=indicatorRepository.findByUserAndIsValidOrderByIdAsc(user, true);
		Calendar cal=Calendar.getInstance();
		if(cal.get(Calendar.DATE) <3){
			cal.add(Calendar.MONTH, -1);
		}
		List<MonthlyIndicator> milist=new ArrayList<MonthlyIndicator>();
		if(indicators.size()>0){
			milist=monthlyIndicatorRepository.findByYearNumAndMonthNumAndIndicatorIn(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)+1), indicators);
		}
		
		
		List<MonthlyIndicator> retlist=new ArrayList<MonthlyIndicator>();
		for(Indicator indicator:indicators){
			MonthlyIndicator mi=null;
			for(MonthlyIndicator tmp:milist){
				if(indicator.equals(tmp.getIndicator())){
					mi=tmp;
					break;
				}
			}
			
			if(mi==null){
				mi=new MonthlyIndicator();
				mi.setIndicator(indicator);
				mi.setYearNum(cal.get(Calendar.YEAR));
				mi.setMonthNum(cal.get(Calendar.MONTH)+1);
				mi.setSelfProgress(0);
				mi.setManagerProgress(0);
				mi.setProgress(0);
			}
			retlist.add(mi);
		}
		
		
		return retlist;
	}
	
	public List<MonthlyIndicatorStatis> getMonthlyIndicatorStatisByUser(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		List<MonthlyIndicatorStatis> mislist=new ArrayList<MonthlyIndicatorStatis>();
		Calendar cal=Calendar.getInstance();
		if(cal.get(Calendar.DATE) <3){
			cal.add(Calendar.MONTH, -1);
		}
		int yearm=cal.get(Calendar.YEAR);
		int monthm=cal.get(Calendar.MONTH)+1;
		List<MonthlyIndicatorStatis> mlist=monthlyIndicatorStatisRepository.findByYearNumAndUserOrderByMonthNumAsc(yearm, user);
		int i=1;
		while(i<=12){
			MonthlyIndicatorStatis mis=null;
			for(MonthlyIndicatorStatis mi:mlist){
				if(mi.getMonthNum().equals(i)){
					mis=mi;
					break;
				}
			}
			
			if(mis==null){
				mis=new MonthlyIndicatorStatis();
				mis.setYearNum(yearm);
				mis.setMonthNum(i);
				if(i<monthm)mis.setScore(0);
				mis.setFullfilled(0);
			}
			
			mislist.add(mis);
			i++;
		}
		
		return mislist;
	}
	
	public Map<Integer, List<MonthlyIndicator>> getGrouplyMonthlyIndicatorByUser(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		Map<Integer, List<MonthlyIndicator>> map=new HashMap<Integer, List<MonthlyIndicator>>();
		List<Indicator> indicators=indicatorRepository.findByUserAndIsValidOrderByIdAsc(user, true);
		Calendar cal=Calendar.getInstance();
		if(cal.get(Calendar.DATE) <3){
			cal.add(Calendar.MONTH, -1);
		}
		int yearm=cal.get(Calendar.YEAR);
		int monthm=cal.get(Calendar.MONTH)+1;
		List<MonthlyIndicator> milist=new ArrayList<MonthlyIndicator>();
		if(indicators.size()>0){
			milist=monthlyIndicatorRepository.findByYearNumAndIndicatorInOrderByMonthNumAsc(yearm, indicators);
		}
		
		
		
		int i=1;		
		while(i<=monthm){
			List<MonthlyIndicator> mlist=map.get(i);
			if(mlist==null){
				mlist=new ArrayList<MonthlyIndicator>();
				map.put(i, mlist);
			}
			
			for(Indicator indicator:indicators){
				
				MonthlyIndicator mind=null;
				
				for(MonthlyIndicator mi:milist){
					if(mi.getMonthNum().equals(i) && mi.getIndicator().equals(indicator)){
						mind=mi;
						break;
					}
				}
				
				if(mind==null){
					mind=new MonthlyIndicator();
					mind.setYearNum(yearm);
					mind.setMonthNum(monthm);					
				}
				if(mind.getManagerProgress()==null)mind.setManagerProgress(0);
				if(mind.getProgress()==null) mind.setProgress(0);
				if(mind.getSelfProgress()==null) mind.setSelfProgress(0);
				
				mlist.add(mind);
				
			}			
			
			i++;
		}
		
	
		
		return map;
	}
	
	
	public List<Indicator> getUserIndicators(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		
		return indicatorRepository.findByUserAndIsValidOrderByIdAsc(user, true);
	}

}
