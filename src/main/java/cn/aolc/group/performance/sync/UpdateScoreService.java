package cn.aolc.group.performance.sync;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.MeritAcceptRepository;
import cn.aolc.group.performance.dao.MonthlyIndicatorStatisRepository;
import cn.aolc.group.performance.dao.RandomPenaltyRecordRepository;
import cn.aolc.group.performance.dao.ScoreHistoryRepository;
import cn.aolc.group.performance.dao.UserRandomCommentRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.dao.WeeklyCommentRepository;
import cn.aolc.group.performance.dao.WeeklyPenaltyRecordRepository;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.MeritAcceptRecord;
import cn.aolc.group.performance.jpa.MonthlyIndicatorStatis;
import cn.aolc.group.performance.jpa.RandomPenaltyRecord;
import cn.aolc.group.performance.jpa.ScoreHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.WeeklyComment;
import cn.aolc.group.performance.jpa.WeeklyPenaltyRecord;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.service.rest.BaseRestService;
import cn.aolc.group.performance.util.exception.SyncDataException;

@Service
public class UpdateScoreService {
	
	@Autowired
	private ScoreHistoryRepository scoreHistoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private WeeklyCommentRepository weeklyCommentRepository;
	
	@Autowired
	private UserRandomCommentRepository userRandomCommentRepository;
		
	@Autowired
	private MonthlyIndicatorStatisRepository monthlyIndicatorStatisRepository;
	
	@Autowired
	private MeritAcceptRepository meritAcceptRepository;
	
	@Autowired
	private RandomPenaltyRecordRepository randomPenaltyRecordRepository;
	
	@Autowired
	private WeeklyPenaltyRecordRepository weeklyPenaltyRecordRepository;
	
	@Autowired
	private BaseRestService baseRestService;
	
	//@Async
	public void updateScoreHistory(ScoreType scoreType,EntityWithId reference) throws Exception{
		User user=null;
		Calendar cal=Calendar.getInstance();
		List<ScoreHistory> list=scoreHistoryRepository.findByScoreTypeAndReferenceId(scoreType, reference.getId());
		ScoreHistory sh=null;
		if(list.size()>0){
			sh=list.get(0);
		}
		if(sh==null){
			sh=new ScoreHistory();
			//sh.setUser(user);
			sh.setYearNum(cal.get(Calendar.YEAR));
			sh.setMonthNum(cal.get(Calendar.MONTH)+1);
			sh.setDayNum(cal.get(Calendar.DATE));
			sh.setScoreType(scoreType);
			sh.setReferenceId(reference.getId());
			sh.setCreated(new Date());		
			sh.setScore(0);
		}
		Integer scoreNum=0;
		
		//boolean isEnabled=baseRestService.isEnabledForUser(yearNum, monthNum, dayNum, user);
		
		if(sh.getScoreType().equals( ScoreType.DAILYBOARD_SCORE)){
			if(!(reference instanceof DailyBoard)) throw new SyncDataException("invalid reference");
			DailyBoard db=(DailyBoard)reference;
			user=db.getUser();
			Calendar ccal=Calendar.getInstance();
			//ccal.setTime(db.getCreateDate());
			ccal.set(db.getYearNum(), db.getMonthNum()-1, db.getDayNum(), 0, 0, 1);
			boolean isEnabled=baseRestService.isEnabledForUser(db.getYearNum(),db.getMonthNum(),db.getDayNum(), user);
			if(!isEnabled){
				scoreNum=0;
			}
			//db.getCreateDate();
			else if(!(ccal.get(Calendar.YEAR) == sh.getYearNum() && (ccal.get(Calendar.MONTH)+1)== sh.getMonthNum() && ccal.get(Calendar.DATE) ==sh.getDayNum())){
				if(ccal.after(cal)){//not happend
					//scoreNum=0;
					return;
				}
				else{//before
					scoreNum=user.getCompany().getDailyBoardScore()==null?0:user.getCompany().getDailyBoardScore();
				}
			}
			else{//today
				int hour=cal.get(Calendar.HOUR_OF_DAY);
				scoreNum=user.getCompany().getDailyBoardScore()==null?0:user.getCompany().getDailyBoardScore();
				if(hour<10){
					
				}
				else if(hour<12){
					scoreNum=scoreNum-1;
				}
				else if(hour<18){
					scoreNum=scoreNum-2;
				}
				else{
					scoreNum=scoreNum-3;
				}
			}
			
			if(db.getTaskType().getIsValid()==null || !db.getTaskType().getIsValid()){
				scoreNum=0;
			}
			if(scoreNum<0)scoreNum=0;
		}
		else if(sh.getScoreType() .equals( ScoreType.SELF_MARK_SCORE)){
			if(!(reference instanceof UserSelfMark)) throw new SyncDataException("invalid reference");
			UserSelfMark usm=(UserSelfMark)reference;
			if(usm!=null){
				user=usm.getUser();
				boolean isEnabled=baseRestService.isEnabledForUser(usm.getYearNum(), usm.getMonthNum(), usm.getDayNum(), user);
				if(!isEnabled)scoreNum =0;
				else scoreNum=usm.getActualScored()==null?0:usm.getActualScored();
			}
			
		}
		else if(sh.getScoreType().equals(ScoreType.RANDOM_COMMENT_SCORE)){//set date to Friday
			if(!(reference instanceof UserRandomComment)) throw new SyncDataException("invalid reference");
			
			UserRandomComment urc=(UserRandomComment)reference;			
			if(urc!=null){
				Calendar tmpcal=Calendar.getInstance();
				tmpcal.setWeekDate(urc.getYearNum(), urc.getWeekOfYear(), Calendar.FRIDAY);
				sh.setYearNum(urc.getYearNum());
				sh.setMonthNum(tmpcal.get(Calendar.MONTH)+1);
				sh.setDayNum(tmpcal.get(Calendar.DATE));
				user=urc.getUser();
				
				scoreNum=urc.getActualScored()==null?0:urc.getActualScored();
			}			
		}
		else if(sh.getScoreType() .equals( ScoreType.WEEKLY_COMMENT_SCORE)){ //set date to Friday
			if(!(reference instanceof WeeklyComment)) throw new SyncDataException("invalid reference");
			WeeklyComment wc=(WeeklyComment)reference;
			if(wc!=null){
				Calendar tmpcal=Calendar.getInstance();
				tmpcal.setWeekDate(wc.getYearNum(), wc.getWeekOfYear(), Calendar.FRIDAY);
				sh.setYearNum(wc.getYearNum());
				sh.setMonthNum(tmpcal.get(Calendar.MONTH)+1);
				sh.setDayNum(tmpcal.get(Calendar.DATE));
				user=wc.getUser();
				scoreNum=wc.getActualScored()==null?0:wc.getActualScored();
			}			
		}
		else if(scoreType.equals(ScoreType.MERIT_ACCEPT_SCORE)){
			if(!(reference instanceof MeritAcceptRecord))throw new SyncDataException("invalid reference");
			MeritAcceptRecord mar=(MeritAcceptRecord)reference;
			if(mar!=null){
				user=mar.getUser();
				scoreNum=mar.getScoreNum();
			}			
		}
		else if(scoreType.equals(ScoreType.RANDOM_PENALTY_SCORE)){//set date to Friday
			if(!(reference instanceof RandomPenaltyRecord)) throw new SyncDataException("invalid reference");
			RandomPenaltyRecord rpr=(RandomPenaltyRecord)reference;
			if(rpr!=null){
				Calendar tmpcal=Calendar.getInstance();
				tmpcal.setWeekDate(rpr.getYearNum(),rpr.getWeekOfYear(), Calendar.FRIDAY);
				sh.setYearNum(rpr.getYearNum());
				sh.setMonthNum(tmpcal.get(Calendar.MONTH)+1);
				sh.setDayNum(tmpcal.get(Calendar.DATE));
				user=rpr.getUser();
				scoreNum=rpr.getScoreNum();
			}			
		}
		else if(scoreType.equals(ScoreType.WEEKLY_PENALTY_SCORE)){//set date to Friday
			if(!(reference instanceof WeeklyPenaltyRecord)) throw new SyncDataException("invalid reference");
			WeeklyPenaltyRecord wpr=(WeeklyPenaltyRecord)reference;
			if(wpr!=null){
				Calendar tmpcal=Calendar.getInstance();
				tmpcal.setWeekDate(wpr.getYearNum(),wpr.getWeekOfYear(), Calendar.FRIDAY);
				sh.setYearNum(wpr.getYearNum());
				sh.setMonthNum(tmpcal.get(Calendar.MONTH)+1);
				sh.setDayNum(tmpcal.get(Calendar.DATE));
				user=wpr.getUser();
				scoreNum=wpr.getScoreNum();
			}			
		}
		
        else if(scoreType.equals(ScoreType.MONTHLY_INDICATOR_SCORE)){			
        	if(!(reference instanceof MonthlyIndicatorStatis)) throw new SyncDataException("invalid reference");
			MonthlyIndicatorStatis mis=(MonthlyIndicatorStatis)reference;
			if(mis!=null){
				Calendar tmpcal=Calendar.getInstance();
				tmpcal.add(Calendar.DATE, -3);
				sh.setYearNum(tmpcal.get(Calendar.YEAR));
				sh.setMonthNum(tmpcal.get(Calendar.MONTH)+1);
				sh.setDayNum(tmpcal.get(Calendar.DATE));
				user=mis.getUser();
				scoreNum=mis.getScore();
			}			
		}
		

		if(user==null){
			throw new SyncDataException("invalid user");
		}
		sh.setUser(user);
		int gapScore=scoreNum;
		if(sh.getScore()!=null ){
			gapScore=gapScore-sh.getScore();
		}
		sh.setScore(scoreNum);
		
		scoreHistoryRepository.save(sh);

        user.setUserScored((user.getUserScored()==null?0:user.getUserScored())+gapScore);
		
		userRepository.save(user);
		
	}
	
//	public void updateScoreHistory(ScoreType st,EntityWithId reference) throws Exception{
//		
//		if(st==null || reference==null){
//			throw new SyncDataException("invalid parameter");
//		}
//		
//		updateScoreHistory( st,reference);
//	}
	

}
