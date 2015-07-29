package cn.aolc.group.performance.service.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.MonthlyCountryStatisRepository;
import cn.aolc.group.performance.dao.MonthlyCountryWealthRepository;
import cn.aolc.group.performance.dao.tenant.CountryRepository;
import cn.aolc.group.performance.jpa.MonthlyCountryStatis;
import cn.aolc.group.performance.jpa.MonthlyCountryWealth;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.jpa.tenant.UserGroup;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CountryService extends BaseRestService{
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private MonthlyCountryStatisRepository monthlyCountryStatisRepository;
	
	@Autowired
	private MonthlyCountryWealthRepository monthlyCountryWealthRepository;
	
		
	public List<MonthlyCountryStatis> getcountrystatis(Integer yearNum,Integer monthNum) throws Exception{
		List<MonthlyCountryStatis> retlist=new ArrayList<MonthlyCountryStatis>();
		if(yearNum==null || monthNum==null){
			Calendar cal=Calendar.getInstance();
	    	cal.add(Calendar.MONTH, -1);    	
	    	if(cal.get(Calendar.DATE) <3){
				cal.add(Calendar.MONTH, -1);
			}
	    	yearNum=cal.get(Calendar.YEAR);
	    	monthNum=cal.get(Calendar.MONTH)+1;
		}
		
		List<Country> countries=countryRepository.findAll();
    	List<MonthlyCountryStatis> clist=monthlyCountryStatisRepository.findByYearNumAndMonthNumAndCountryInOrderByCalScoredDesc(yearNum, monthNum,countries);
    	for(Country country:countries){    		
    		MonthlyCountryStatis rmcs=null;
    		for(MonthlyCountryStatis mcs:clist){
    			if(mcs.getCountry().getId().equals(country.getId())){
    				rmcs=mcs;
    				break;
    			}
    		}    		
    		if(rmcs==null){
    			rmcs=new MonthlyCountryStatis();
    			rmcs.setCountry(country);
    			rmcs.setCalScored(0);
    			rmcs.setYearNum(yearNum);
    			rmcs.setMonthNum(monthNum);
    		}    
    		//init
//    		User user=country.getOwner();
//    		UserGroup ug=user.getOwnerGroup();
//    		if(ug!=null)ug.getUsers().size();
    		//
    		retlist.add(rmcs);
    	}    	
    	Collections.sort(retlist, new Comparator<MonthlyCountryStatis>() {
			public int compare(MonthlyCountryStatis o1, MonthlyCountryStatis o2) {
				if(o2.getCalScored()==null || (o1.getCalScored()!=null && o1.getCalScored()>o2.getCalScored()))
				return -1;
				else return 1;
			}
		});
    	if(retlist.size()<=0)throw new Exception("没有可以排名的国家");    	
    	return retlist;
	}
	
	public List<Country> getCountries(String property,String order) throws Exception{
		Sort sort=new Sort(order.equals("asc")?Direction.ASC:Direction.DESC, property);
		return countryRepository.findAll(sort);
	}
	
	public MonthlyCountryWealth getMonthlyWealth(Long countryId,Integer yearNum,Integer monthNum) throws Exception{
		MonthlyCountryWealth mcw=null;
		if(yearNum==null || monthNum==null){
			Calendar cal=Calendar.getInstance();
			yearNum=cal.get(Calendar.YEAR);
			monthNum=cal.get(Calendar.MONTH)+1;
		}
		Country country=countryRepository.findOne(countryId);
		List<MonthlyCountryWealth> mcwlist=monthlyCountryWealthRepository.findByYearNumAndMonthNumAndCountry(yearNum, monthNum, country);
		if(mcwlist.size()>0){
			mcw=mcwlist.get(0);
		}
		return mcw;
	}
	
	public MonthlyCountryWealth saveCountryWealth(Long wealthId,Double outcome,String description) throws Exception{
		MonthlyCountryWealth mcw=monthlyCountryWealthRepository.findOne(wealthId);
		if(mcw==null) throw new Exception("invalid wealth");
		Double gap=0d;
		if(mcw.getOutcome()!=null){
			gap=mcw.getOutcome()-outcome;
		}
		mcw.setOutcome(outcome);
		mcw.setSettled((mcw.getSettled()==null?0d:mcw.getSettled())+gap);
		mcw.setDescription(description);
		
		if(mcw.getSettled()<0)throw new Exception("超支了");
		Country country=mcw.getCountry();
		country.setWealth(mcw.getSettled());
		countryRepository.save(country);
		return monthlyCountryWealthRepository.save(mcw);
		
	}

}
