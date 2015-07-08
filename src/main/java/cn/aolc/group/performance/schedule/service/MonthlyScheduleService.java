package cn.aolc.group.performance.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.CompanyRepository;
import cn.aolc.group.performance.dao.MonthlyCountryStatisRepository;
import cn.aolc.group.performance.dao.MonthlyCountryWealthRepository;
import cn.aolc.group.performance.dao.MonthlyIndicatorRepository;
import cn.aolc.group.performance.dao.MonthlyIndicatorStatisRepository;
import cn.aolc.group.performance.dao.QuarterlyWealthBonusRepository;
import cn.aolc.group.performance.dao.ScoreHistoryRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.tenant.CountryRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.MonthlyCountryStatis;
import cn.aolc.group.performance.jpa.MonthlyCountryWealth;
import cn.aolc.group.performance.jpa.MonthlyIndicator;
import cn.aolc.group.performance.jpa.MonthlyIndicatorStatis;
import cn.aolc.group.performance.jpa.QuarterlyWealthBonus;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.sync.UpdateCoinService;
import cn.aolc.group.performance.sync.UpdateScoreService;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class MonthlyScheduleService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private ScoreHistoryRepository scoreHistoryRepository;
	
	@Autowired
	private MonthlyIndicatorRepository monthlyIndicatorRepository;
	
	@Autowired
	private MonthlyIndicatorStatisRepository monthlyIndicatorStatisRepository;
	
	@Autowired
	private QuarterlyWealthBonusRepository quarterlyWealthBonusRepository;
	
	@Autowired
	private MonthlyCountryStatisRepository monthlyCountryStatisRepository;
	
	@Autowired
	private MonthlyCountryWealthRepository monthlyCountryWealthRepository;
	
	@Autowired
	private UpdateScoreService updateScoreService;
	
	@Autowired
	private UpdateCoinService updateCoinService;
	
	public void checkUserData(Integer yearNum,Integer monthNum) throws Exception{
		List<User> users=userRepository.findByUserStatusNot(UserStatus.Retired);
		for(User user:users){
			syncUserData_MonthlyIndicator(user, yearNum, monthNum);
			syncUserData_QuarterlyBonus(user, yearNum, monthNum);
		}
	}
	
	public void checkCountryData(Integer yearNum,Integer monthNum) throws Exception{
		List<Company> companies=companyRepository.findAll();
		for(Company company:companies){
			List<Country> countries=countryRepository.findByTenantIdOrderByIdAsc(company.getCode());
			for(Country country:countries){
				syncCountryData_MonthlyStatis(country,yearNum,monthNum);
			}
			
			List<MonthlyCountryStatis> mcslist=monthlyCountryStatisRepository.findByYearNumAndMonthNumAndCountryInOrderByCalScoredDesc(yearNum, monthNum, countries);
			for(int i=0;i<mcslist.size();i++){
				MonthlyCountryStatis mcs=mcslist.get(i);
				syncCountryData_MonthlyWealth(mcs.getCountry(), yearNum, monthNum, i+1);
			}
			
		}
		
	}
	
	private void syncUserData_MonthlyIndicator(User user,Integer yearNum,Integer monthNum) throws Exception{
		int userscore=0;		
		Integer tsh=scoreHistoryRepository.findSumScoreByUserAndYearNumAndMonthNum(user, yearNum, monthNum);
		if(tsh==null) tsh=0;
		double tfull=0.0d;
		List<Indicator> indicators=user.getIndicatorList();
		for(Indicator indi:indicators){
			MonthlyIndicator mi=null;
			List<MonthlyIndicator> mis=monthlyIndicatorRepository.findByYearNumAndMonthNumAndIndicator(yearNum, monthNum, indi);
			if(mis.size()>0){
				mi=mis.get(0);
			}
			
			if(mi!=null){
				tfull=tfull+(mi.getProgress()*indi.getWeight()/100);
			}			
		}		
		userscore=Double.valueOf(tsh*(tfull-80)/100*0.2).intValue();
		MonthlyIndicatorStatis mis=null;
		List<MonthlyIndicatorStatis> mislist=monthlyIndicatorStatisRepository.findByYearNumAndMonthNumAndUser(yearNum, monthNum, user);
		if(mislist.size()>0){
			mis=mislist.get(0);
		}
		if(mis==null){
			mis=new MonthlyIndicatorStatis();
			mis.setMonthNum(monthNum);				
			mis.setUser(user);
			mis.setYearNum(yearNum);
		}
		mis.setFullfilled(Double.valueOf(tfull).intValue());
		mis.setScore(userscore);
		mis=monthlyIndicatorStatisRepository.save(mis);
		updateScoreService.updateScoreHistory(ScoreType.MONTHLY_INDICATOR_SCORE, mis);
	}
	
	private void syncUserData_QuarterlyBonus(User user,Integer yearNum,Integer monthNum) throws Exception{
		if(monthNum%3 !=0) return;
		int quarter=monthNum/3;
		int tscore=0;
		Long totalScored=scoreHistoryRepository.calQuarterlySumScoreByYearNumAndQuarterNumAndUser(yearNum, quarter, user);
		if(totalScored!=null)tscore=totalScored.intValue();
		long coin=Double.valueOf(tscore*0.05).longValue();
		List<QuarterlyWealthBonus> wblist=quarterlyWealthBonusRepository.findByUserAndYearNumAndQuarter(user, yearNum, quarter);
		QuarterlyWealthBonus wb=null;
		if(wblist.size() >0){
			wb=wblist.get(0);
		}
		
		if(wb==null){
			wb=new QuarterlyWealthBonus();
			wb.setQuarter(quarter);
			wb.setRank(0);
			
			wb.setUser(user);
			wb.setYearNum(yearNum);
		}		
		wb.setScore(tscore);
		wb.setCoinNum(coin);
		wb.setUpdated(new Date());
		wb=quarterlyWealthBonusRepository.save(wb);
		updateCoinService.updateCoinHistory(CoinType.QuarterlyWealthBonus, wb);
	}
	
	private void syncCountryData_MonthlyStatis(Country country,Integer yearNum,Integer monthNum) throws Exception{
		MonthlyCountryStatis mcs=null;
		List<MonthlyCountryStatis> mcslist=monthlyCountryStatisRepository.findByCountryAndYearNumAndMonthNum(country, yearNum, monthNum);
		if(mcslist.size()>0){
			mcs=mcslist.get(0);
		}
		else{
			mcs=new MonthlyCountryStatis();
		}
		List<User> userlist= country.getUsers();
		int totalScore=0;
		int countscore=0;
		for(User user:userlist){
			Integer thisscore=scoreHistoryRepository.findSumScoreByUserAndYearNumAndMonthNum(user, yearNum, monthNum);
			totalScore=totalScore+(thisscore==null?0:thisscore);
			countscore++;
		}
		mcs.setCountry(country);
		mcs.setYearNum(yearNum);
		mcs.setMonthNum(monthNum);
		mcs.setCalScored(0);
		if(countscore>0){
			mcs.setCalScored(totalScore/countscore);
		}		
		monthlyCountryStatisRepository.save(mcs);
	}
	
	private void syncCountryData_MonthlyWealth(Country country,Integer yearNum,Integer monthNum,int rank) throws Exception{
		int yearm=0,monthm=0;
		if(monthNum==12){
			yearm=yearNum+1;
			monthm=1;
		}
		else{
			yearm=yearNum;
			monthm=monthNum+1;
		}
		MonthlyCountryWealth mcw=null;
		MonthlyCountryWealth lastmcw=null;
		List<MonthlyCountryWealth> mcwlist1=monthlyCountryWealthRepository.findByYearNumAndMonthNumAndCountry(yearNum, monthNum, country);
		List<MonthlyCountryWealth> mcwlist2=monthlyCountryWealthRepository.findByYearNumAndMonthNumAndCountry(yearm, monthm, country);
		
		if(mcwlist1.size()>0){
			lastmcw=mcwlist1.get(0);
		}
		
		if(mcwlist2.size()>0){
			mcw=mcwlist2.get(0);
		}
		
		if(mcw==null){
			mcw=new MonthlyCountryWealth();
			mcw.setCountry(country);
			mcw.setYearNum(yearm);
			mcw.setMonthNum(monthm);				
		}
		if(mcw.getDescription()==null){
			mcw.setDescription("自动结算");
		}
		if(mcw.getIncome()==null){
			mcw.setIncome(0d);
			if(rank==1){
				mcw.setIncome((country.getUsers().size()+2)*150d);
			}
			else if(rank==2){
				mcw.setIncome((country.getUsers().size()+2)*50d);
			}
			
		}
		if(mcw.getOutcome()==null){
			mcw.setOutcome(0d);
		}
		if(mcw.getSettled()==null){
			mcw.setSettled(0d);
		}
		if(lastmcw!=null && lastmcw.getSettled()!=null){
			mcw.setSettled(lastmcw.getSettled()+mcw.getIncome()-mcw.getOutcome());
		}
		else{
			mcw.setSettled(mcw.getIncome()-mcw.getOutcome());
		}
		mcw.setUpdated(new Date());
		monthlyCountryWealthRepository.save(mcw);		
		country.setWealth(mcw.getSettled());
		countryRepository.save(country);
	}

}
