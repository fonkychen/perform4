package cn.aolc.group.performance.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.CompanyRepository;
import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.dao.tenant.UserDailyRankRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.UserDailyRank;
import cn.aolc.group.performance.service.rest.BaseRestService;
import cn.aolc.group.performance.sync.UpdateCoinService;
import cn.aolc.group.performance.sync.UpdatePopularService;
import cn.aolc.group.performance.sync.UpdateScoreService;


/*
 * Confirm yesterday user score and coin gained
 * Updated pre-inputed 
 * update User-Reward & User-Task
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class DailyScheduleService{
	
	@Autowired
	private BaseRestService baseRestService;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository;
	
	@Autowired
	private UserDailyRankRepository userDailyRankRepository;
	
	@Autowired
	private UpdateScoreService updateScoreService;
	
	@Autowired
	private UpdateCoinService updateCoinService;
	
	@Autowired
	private UpdatePopularService updatePopularService;
	
	/**
	 * job?
	 * @param yearNum
	 * @param monthNum
	 * @param dayNum
	 */
	public void checkUserData(Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		List<User> userlist=userRepository.findByUserStatusNot(UserStatus.Retired);
		for(User user:userlist){
			
			syncUserData(user, yearNum, monthNum, dayNum);
		}
	}
	
	public void countUserData(Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		//calculate user daily rank
		countDailyRank(yearNum, monthNum, dayNum);
	}
	
	/**
	 * Dailyboard & UserSelfMark
	 * @param user
	 * @param yearNum
	 * @param monthNum
	 * @param dayNum
	 */
	private void syncUserData(User user,Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
	//	boolean isEnabled=baseRestService.isEnabledForUser(yearNum, monthNum, dayNum, user);
		//check dailyboard
		List<DailyBoard> dblist=dailyBoardRepository.findByUserAndYearNumAndMonthNumAndDayNum(user, yearNum, monthNum, dayNum);
		if(dblist.size()>0){
			updateScoreService.updateScoreHistory(ScoreType.DAILYBOARD_SCORE, dblist.get(0));
			updateCoinService.updateCoinHistory(CoinType.DailyBoard, dblist.get(0));
		}
		//check userselfmark
		List<UserSelfMark> usmlist=userSelfMarkRepository.findByUserAndYearNumAndMonthNumAndDayNum(user, yearNum, monthNum, dayNum);
		if(usmlist.size()>0){
			updateScoreService.updateScoreHistory(ScoreType.SELF_MARK_SCORE, usmlist.get(0));
			updateCoinService.updateCoinHistory(CoinType.SelfMark, usmlist.get(0));
		}
		
		
	}
	
	private void countDailyRank(Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		List<Company> companies=companyRepository.findAll();
		for(Company company:companies){
			List<User> users1=userRepository.findByCompanyAndUserStatusNotOrderByUserScoredDesc(company, UserStatus.Retired);
			List<User> users2=userRepository.findByCompanyAndUserStatusNotOrderByUserCoinsDesc(company, UserStatus.Retired);
			List<User> users3=userRepository.findByCompanyAndUserStatusNotOrderByUserPopularDesc(company, UserStatus.Retired);
			
			for(int i=0;i<users1.size();i++){
				User user=users1.get(i);
				UserDailyRank udr=new UserDailyRank();
				udr.setCoin(user.getUserCoins());
				udr.setDayNum(dayNum);
				udr.setMonthNum(monthNum);
				udr.setPopular(user.getUserPopular());
				udr.setScore(user.getUserScored());
				udr.setTenantId(company.getCode());
				udr.setUser(user);
				udr.setYearNum(yearNum);
				
				udr.setScoreRank(i+1);
				
				udr.setCoinRank(users2.indexOf(user)+1);
				udr.setPopularRank(users3.indexOf(user)+1);
				userDailyRankRepository.save(udr);
			}
		}
	}
	
	//public void update 

}
