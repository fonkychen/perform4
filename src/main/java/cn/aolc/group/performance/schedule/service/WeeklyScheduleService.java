package cn.aolc.group.performance.schedule.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.CompanyRepository;
import cn.aolc.group.performance.dao.RandomPenaltyRecordRepository;
import cn.aolc.group.performance.dao.UserRandomCommentRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.WeeklyCommentRepository;
import cn.aolc.group.performance.dao.WeeklyPenaltyRecordRepository;
import cn.aolc.group.performance.dao.WeeklyTitleSalaryRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.RandomPenaltyRecord;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.jpa.WeeklyComment;
import cn.aolc.group.performance.jpa.WeeklyPenaltyRecord;
import cn.aolc.group.performance.jpa.WeeklyTitleSalary;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.sync.UpdateCoinService;
import cn.aolc.group.performance.sync.UpdateScoreService;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class WeeklyScheduleService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WeeklyCommentRepository weeklyCommentRepository;
	
	@Autowired
	private WeeklyPenaltyRecordRepository weeklyPenaltyRecordRepository;
	
	@Autowired
	private WeeklyTitleSalaryRepository weeklyTitleSalaryRepository;
	
	@Autowired
	private UserRandomCommentRepository userRandomCommentRepository;
	
	@Autowired
	private RandomPenaltyRecordRepository randomPenaltyRecordRepository;
	
	@Autowired
	private UpdateScoreService updateScoreService;
	
	@Autowired
	private UpdateCoinService updateCoinService;
	
	private Map<Company, Integer> companyWeeklyScoreMap=new HashMap<Company, Integer>();
	private Map<Company, Integer> companyRandomScoreMap=new HashMap<Company, Integer>();
	
	public synchronized void checkUserData(Integer yearNum,Integer weekOfYear) throws Exception{
		List<Company> companys=companyRepository.findAll();
		
		companyWeeklyScoreMap.clear();
		companyRandomScoreMap.clear();
		for(Company company:companys){
			Double ascored=weeklyCommentRepository.calAverageScoredByUserInAndYearNumAndWeekOfYear(company.getUsers(), yearNum, weekOfYear);
			
			int scoreNum=0;
			if(ascored!=null){
				scoreNum=ascored.intValue();
			}
			companyWeeklyScoreMap.put(company, scoreNum);
			
			Double arandom=userRandomCommentRepository.calAverageScoreByUserInAndYearNumAndWeekOfYear(company.getUsers(), yearNum, weekOfYear);
			int randomScore=0;
			if(arandom!=null){
				randomScore=arandom.intValue();
			}
			companyRandomScoreMap.put(company, randomScore);
		}
		
		List<User> users=userRepository.findByUserStatusNot(UserStatus.Retired);
		for(User user:users){
			syncUserdata(user, yearNum, weekOfYear);
		}
	}
	
	private void syncUserdata(User user,Integer yearNum,Integer weekOfYear) throws Exception{
		
		
		//weeklycomment
		List<WeeklyComment> wclist=weeklyCommentRepository.findByUserAndYearNumAndWeekOfYear(user, yearNum, weekOfYear);
		if(wclist.size() >0){
			updateScoreService.updateScoreHistory(ScoreType.WEEKLY_COMMENT_SCORE, wclist.get(0));
		}
		else{
			doDefaultWeeklyComment(user, yearNum, weekOfYear, companyWeeklyScoreMap.get(user.getCompany()), companyWeeklyScoreMap.get(user.getCompany()));
		}
		// weeklypenalty
		if(wclist.size()<=0 && user.getUserGroup()!=null && user.getUserGroup().getOwner()!=null){
			updatePenaltyRecord(user.getUserGroup().getOwner(), yearNum, weekOfYear);
		}
		//randomcomment
		List<UserRandomComment> urlist=userRandomCommentRepository.findByYearNumAndWeekOfYearAndUser(yearNum, weekOfYear, user);
		if(urlist.size()>0){
			updateScoreService.updateScoreHistory(ScoreType.RANDOM_COMMENT_SCORE, urlist.get(0));
		}
		else{
			//default comment
			doDefaultRandomComment(user, yearNum, weekOfYear, companyRandomScoreMap.get(user.getCompany()));
		}
		
		//random penalty
		
		List<UserRandomComment> urlist2=userRandomCommentRepository.findByYearNumAndWeekOfYearAndByUser(yearNum, weekOfYear, user);
		if(urlist2.size()<=0){
			updateRandomPenalty(user, yearNum, weekOfYear);
		}
		
		//weekly title salary
		updateWeelyTitleSalary(user, yearNum, weekOfYear);
		
	}
	
	private void updatePenaltyRecord(User user,Integer yearNum,Integer weekOfYear) throws Exception{
		List<WeeklyPenaltyRecord> wplist=weeklyPenaltyRecordRepository.findByUserAndYearNumAndWeekOfYear(user, yearNum, weekOfYear);
		if(wplist.size()<=0){
			WeeklyPenaltyRecord wpr=new WeeklyPenaltyRecord();
			wpr.setDescription("");
			wpr.setScoreNum(-20);
			wpr.setUpdated(new Date());
			wpr.setUser(user);
			wpr.setWeekOfYear(weekOfYear);
			wpr.setYearNum(yearNum);
			wpr=weeklyPenaltyRecordRepository.save(wpr);
			updateScoreService.updateScoreHistory(ScoreType.WEEKLY_PENALTY_SCORE, wpr);
		}
	}
	
	
	private void updateRandomPenalty(User user,Integer yearNum,Integer weekOfYear) throws Exception{
		List<RandomPenaltyRecord> rprlist=randomPenaltyRecordRepository.findByUserAndYearNumAndWeekOfYear(user, yearNum, weekOfYear);
		if(rprlist.size()<=0){
			RandomPenaltyRecord rpr=new RandomPenaltyRecord();
			rpr.setScoreNum(-4);
			rpr.setUpdated(new Date());
			rpr.setUser(user);
			rpr.setWeekOfYear(weekOfYear);
			rpr.setYearNum(yearNum);
			rpr=randomPenaltyRecordRepository.save(rpr);
			updateScoreService.updateScoreHistory(ScoreType.RANDOM_PENALTY_SCORE, rpr);
		}
	}
	
	private void doDefaultWeeklyComment(User user,Integer yearNum,Integer weekOfYear,Integer scoreNum,Integer scored) throws Exception{
		WeeklyComment wc=new WeeklyComment();
		wc.setActualScored(scored);
		wc.setScoreNum(scoreNum);
		wc.setTaskDescription("系统默认评分");
		wc.setUpdated(new Date());
		wc.setUser(user);
		wc.setWeekOfYear(weekOfYear);
		wc.setYearNum(yearNum);
		wc=weeklyCommentRepository.save(wc);
		updateScoreService.updateScoreHistory(ScoreType.WEEKLY_COMMENT_SCORE, wc);
	}
	
	private void doDefaultRandomComment(User user,Integer yearNum,Integer weekOfYear,Integer actualScored) throws Exception{
		UserRandomComment comment=new UserRandomComment();
		comment.setActualScored(actualScored);
		comment.setTaskDescription("系统默认评分");
		comment.setUpdated(new Date());
		comment.setUser(user);
		comment.setWeekOfYear(weekOfYear);
		comment.setYearNum(yearNum);
		comment=userRandomCommentRepository.save(comment);
		updateScoreService.updateScoreHistory(ScoreType.RANDOM_COMMENT_SCORE, comment);
	}
	
	private void updateWeelyTitleSalary(User user,Integer yearNum,Integer weekOfYear) throws Exception{
		Title title=user.getTitle();
		if(title==null || title.getSalaryCoin()==null) return;
		WeeklyTitleSalary wts=null;
		List<WeeklyTitleSalary> wtslist=weeklyTitleSalaryRepository.findByYearNumAndWeekOfYearAndUser(yearNum, weekOfYear, user);
		if(wtslist.size()>0){
			wts=wtslist.get(0);
		}
		
		if(wts==null){
			wts=new WeeklyTitleSalary();
			
			
			wts.setUser(user);
			wts.setWeekOfYear(weekOfYear);
			wts.setYearNum(yearNum);
		}
		wts.setTitle(title);
		wts.setSalary(title.getSalaryCoin());
		wts=weeklyTitleSalaryRepository.save(wts);
		updateCoinService.updateCoinHistory(CoinType.TitleSalary, wts);
	}

}
