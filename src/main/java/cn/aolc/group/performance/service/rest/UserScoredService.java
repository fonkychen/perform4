package cn.aolc.group.performance.service.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.annotation.UserScoreAdded;
import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.IndicatorRepository;
import cn.aolc.group.performance.dao.UserRandomCommentRepository;
import cn.aolc.group.performance.dao.UserRandomItemRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.dao.WeeklyCommentRepository;
import cn.aolc.group.performance.dao.tenant.DailyMarkCategoryRepository;
import cn.aolc.group.performance.dao.tenant.RandomCommentCategoryRepository;
import cn.aolc.group.performance.dao.tenant.TaskTypeGroupRepository;
import cn.aolc.group.performance.dao.tenant.TaskTypeRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.SelfDailyMark;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.jpa.UserRandomItem;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.WeeklyComment;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.tenant.DailyMarkCategory;
import cn.aolc.group.performance.jpa.tenant.RandomCommentCategory;
import cn.aolc.group.performance.jpa.tenant.TaskTypeGroup;
import cn.aolc.group.performance.model.CategorySelfMarkModel;
import cn.aolc.group.performance.model.RandomItemModel;
import cn.aolc.group.performance.model.StaffDailyBoardItemModel;
import cn.aolc.group.performance.model.UserRandomModel;
import cn.aolc.group.performance.model.UserSelfMarkModel;
import cn.aolc.group.performance.model.WeeklyCalendarModel;
import cn.aolc.group.performance.util.PerformanceUtil;
import cn.aolc.group.performance.util.exception.SaveSelfMarkException;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserScoredService extends BaseRestService{
	
	private final static Logger logger=LoggerFactory.getLogger(UserScoredService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WeeklyCommentRepository weeklyCommentRepository;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository;
	
	@Autowired
	private IndicatorRepository indicatorRepository;
	
	@Autowired
	private DailyMarkCategoryRepository dailyMarkCategoryRepository;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private TaskTypeGroupRepository taskTypeGroupRepository;
	
	@Autowired
	private TaskTypeRepository taskTypeRepository;
	
	@Autowired
	private WorkStatusService workStatusService;
	
	@Autowired
	private UserRandomCommentRepository userRandomCommentRepository;
	
	@Autowired
	private RandomCommentCategoryRepository randomCommentCategoryRepository;
	
	@Autowired
	private UserRandomItemRepository userRandomItemRepository;
	
	
	@UserScoreAdded(scoreType=ScoreType.DAILYBOARD_SCORE)
	@UserCoinAdded(coinType=CoinType.DailyBoard)
	public DailyBoard saveDailyBoard(StaffDailyBoardItemModel model) throws Exception{
		User user=getContextUser();
		if(model.getTask()==null || model.getTask().length()>10) throw new Exception("公事榜字数应在1-10个之间");
		DailyBoard db=null;
		if(model.getId()!=null){
			db=dailyBoardRepository.findOne(model.getId());
		}
		if(db==null){
			db=new DailyBoard();
			db.setCreateDate(new Date());
		}
		
		//logger.debug("DailyBoardInterface DailyBoard task "+model.getTask());
		db.setYearNum(model.getYearNum());
		db.setMonthNum(model.getMonthNum());
		db.setDayNum(model.getDayNum());
		db.setUpdateDate(new Date());
		db.setUser(user);
		db.setTask(model.getTask());
		db.setTaskType(taskTypeRepository.findOne(model.getTaskTypeId()));
		//if(db.getCreateDate()==null)db.setCreateDate(new Date());
		db=dailyBoardRepository.save(db);
		
		workStatusService.updateStatus(user, model.getYearNum(), model.getMonthNum(), model.getDayNum());
		return db;
	}
	
//	public List<DailyBoard> getDailyBoardByCurrentCompanyAndWeekOfYear(Integer yearNum,Integer weekOfYear) throws Exception{
//		User user=getContextUser();
//		return getDailyBoardByUsersAndWeekOfYear(user.getCompany().getUsers(), yearNum, weekOfYear);
//	}
	
	public List<DailyBoard> getDailyBoardByUsersAndWeekOfYear(List<User> users,Integer yearNum,Integer weekOfYear){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();	
		cal.setWeekDate(yearNum, weekOfYear, Calendar.MONDAY);
		String startdate=sdf.format(cal.getTime());
		
		cal.add(Calendar.DATE, 6);
		String enddate=sdf.format(cal.getTime());
		
		return dailyBoardRepository.findByUserInAndDateBetween(users, startdate, enddate);
	}
	
	public List<TaskTypeGroup> getTaskTypeGroups(){
		return taskTypeGroupRepository.findAll();
	}
	
	public WeeklyComment getCommentByWeeklyOfYear(User user,Integer yearNum,Integer weeklyOfYear) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		 
		List<WeeklyComment> comments=weeklyCommentRepository.findByUserAndYearNumAndWeekOfYear(user, yearNum, weeklyOfYear);
		if(comments.size()>0){
			return comments.get(0);
		}
		return null;
	}
	
	public WeeklyComment getLatestComment(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		
		Pageable pageable=new PageRequest(0,1);
		List<WeeklyComment> wclist=weeklyCommentRepository.findByUserOrderByYearNumDescWeekOfYearDesc(user, pageable);
		if(wclist.size()>0){
			return wclist.get(0);
		}
		return null;		
	}
	
	/**
	 * score will sync next Monday
	 * need to check authority
	 * @param id
	 * @param userId
	 * @param scored
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public WeeklyComment saveComment(Long userId,Integer scored,String description) throws Exception{
		WeeklyComment comment=null;
		User user=getContextUser();
		User cuser=userRepository.findOne(userId);
		if(!isAuthorized(user, cuser)) throw new Exception("没有权限进行此操作");
		Calendar cal=PerformanceUtil.getWeekend();
		List<WeeklyComment> comments=weeklyCommentRepository.findByUserAndYearNumAndWeekOfYear(cuser, cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR));
		if(comments.size()>0){
			comment=comments.get(0);
		}
		
		if(comment==null){
			comment=new WeeklyComment();			
			comment.setByUser(user);	
			comment.setUser(cuser);
			comment.setWeekOfYear(cal.get(Calendar.WEEK_OF_YEAR));
			comment.setYearNum(cal.get(Calendar.YEAR));
		}
		
		comment.setUpdated(new Date());
		comment.setTaskDescription(description);
		comment.setActualScored(scored);
		comment.setScoreNum(scored);
		return weeklyCommentRepository.save(comment);
	} 
	
	public UserSelfMark getSelfMark() throws Exception{
		Calendar cal=Calendar.getInstance();
		List<UserSelfMark> userSelfMarks=userSelfMarkRepository.findByUserAndYearNumAndMonthNumAndDayNum(getContextUser(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
		if(userSelfMarks.size()>0){
			return userSelfMarks.get(0);
		}
		return null;
	}
	
	@UserScoreAdded(scoreType = ScoreType.SELF_MARK_SCORE)
	@UserCoinAdded(coinType = CoinType.SelfMark)
	public UserSelfMark saveSelfMark(UserSelfMarkModel model) throws Exception{
		UserSelfMark selfmark=null;
		Calendar cal=Calendar.getInstance();
		User user=getContextUser();
		if(!isSelfMarkEnabled(cal, user))throw new Exception("公事榜没有录入或者类型不允许");
		if(model.getTaskDescription()==null || model.getTaskDescription().equals("")) throw new Exception("自评描述不能为空");
		List<UserSelfMark> selfmarks=userSelfMarkRepository.findByUserAndYearNumAndMonthNumAndDayNum(user, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
		if(selfmarks.size()>0){
			selfmark=selfmarks.get(0);			
		}
		
		if(selfmark==null){
			selfmark=new UserSelfMark();
			selfmark.setUser(user);
			selfmark.setYearNum(cal.get(Calendar.YEAR));
			selfmark.setMonthNum(cal.get(Calendar.MONTH)+1);
			selfmark.setDayNum(cal.get(Calendar.DATE));
		}
		selfmark.setTaskDescription(model.getTaskDescription());
		Long[] indicatorIds=model.getIndicators();
		if(indicatorIds!=null && indicatorIds.length>0){
			List<Indicator> indicators=new ArrayList<Indicator>();
			for(Long indicatorId:indicatorIds){
				indicators.add(indicatorRepository.findOne(indicatorId));
			}
			
			selfmark.setDailyIndicators(indicators);
		}
		
		if(model.getCsms()==null || model.getCsms().length<=0) throw new SaveSelfMarkException("不要忘记了给自己评个分");
		List<SelfDailyMark> sdmlist=selfmark.getSelfDailyMarks();
		Double actualScored=0d;
		List<SelfDailyMark> selfDailyMarks=new ArrayList<SelfDailyMark>();
		for(CategorySelfMarkModel csm:model.getCsms()){
			DailyMarkCategory category=dailyMarkCategoryRepository.findOne(csm.getCategoryId());
			if(category==null)continue;
			SelfDailyMark sdm=null;
			if(sdmlist!=null){
				for(SelfDailyMark tsdm:sdmlist){
					if(tsdm.getMarkCategory()!=null && tsdm.getMarkCategory().getId().equals(csm.getCategoryId())){
						sdm=tsdm;
						break;
					}
				}
			}
			
			if(sdm==null)sdm=new SelfDailyMark();
			sdm.setMarkCategory(category);
			sdm.setUserSelfMark(selfmark);
			sdm.setStarNum(csm.getStarNum());			
			selfDailyMarks.add(sdm);
			actualScored=actualScored+category.getPercentage()*(20*csm.getStarNum());
		}
		if(actualScored<=0) throw new SaveSelfMarkException("不要忘记了给自己评个分");
		Company company=user.getCompany();
		if(company!=null && company.getDailySelfMarkScore()!=null && company.getDailySelfMarkScore()>0){
			actualScored=actualScored*company.getDailySelfMarkScore()/100;
		}
		else{
			actualScored=0d;
		}
		selfmark.setUpdated(new Date());
		selfmark.setActualScored(Double.valueOf(actualScored).intValue());	
		selfmark.setSelfDailyMarks(selfDailyMarks);
		userSelfMarkRepository.save(selfmark);
		return selfmark;
	}
	
	public List<UserSelfMark> getSelfMarks(User user,Date startdate,Date enddate) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		List<User> users=new ArrayList<User>();
		users.add(user);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String startDate=sdf.format(startdate);
		String endDate=sdf.format(enddate);
		return userSelfMarkRepository.findByUserInAndDateBetween(users, startDate, endDate);
	}
	
	public boolean isSelfMarkEnabled(Calendar cal,User user){
		List<DailyBoard> dblist=dailyBoardRepository.findByUserAndYearNumAndMonthNumAndDayNum(user, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
		if(dblist.size()>0){
			DailyBoard db=dblist.get(0);
			
			return db.getTaskType().getIsValid();
		}
		return false;
	}
	
	public synchronized void saveRandom(UserRandomModel urmodel) throws Exception{
		if(urmodel.getUserId()==null) throw new Exception("invalid user id");
		User user=userRepository.findOne(urmodel.getUserId());
		User byuser=getContextUser();

		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
		if(urmodel.getRimodels()==null || urmodel.getRimodels().length<=0)throw new Exception("评分没有成功哦！");
		int precheckstar=0;
		for(RandomItemModel rim:urmodel.getRimodels()){
			precheckstar=precheckstar+rim.getStarNum();
		}
		if(precheckstar==0)throw new Exception("请给该同事评定分数!");
		
		UserRandomComment urc=null;
		if(urmodel.getRandomId()!=null){
			urc=userRandomCommentRepository.findOne(urmodel.getRandomId());
		}
		List<UserRandomComment> urclist1=userRandomCommentRepository.findByYearNumAndWeekOfYearAndUser(wcm.getYearNum(), wcm.getWeekofyear(), user);
		List<UserRandomComment> urclist2=userRandomCommentRepository.findByYearNumAndWeekOfYearAndByUser(wcm.getYearNum(), wcm.getWeekofyear(), byuser);
		if(urc!=null && (!urc.getByUser().getId().equals(byuser.getId()) || !urc.getUser().getId().equals(user.getId()))){
			throw new Exception("用户不符");
		}
		else if(urc ==null && (urclist1.size()>0 || urclist2.size()>0)){
			throw new Exception("不允许再次评分");
		}

		
		
		if(urc==null){
			urc=new UserRandomComment();
			urc.setByUser(byuser);
			urc.setUser(user);
		}
		
		urc.setTaskDescription(urmodel.getDescription());
		urc.setUpdated(new Date());
		urc.setYearNum(wcm.getYearNum());
		urc.setWeekOfYear(wcm.getWeekofyear());
		urc=userRandomCommentRepository.save(urc);
		List<UserRandomItem> urilist=urc.getRandomItems();
		Double actualScored=0d;
		
		for(RandomItemModel ri:urmodel.getRimodels()){
			RandomCommentCategory rcc=randomCommentCategoryRepository.findOne(ri.getRandomCatId());
			if(rcc==null)continue;
			UserRandomItem uri=null;
			if(urilist!=null){
				for(UserRandomItem curi:urilist){
					if(curi.getCommentCategory()!=null && curi.getCommentCategory().getId().equals(rcc.getId())){
						uri=curi;
						break;
					}
				}
			}			
			if(uri==null)uri=new UserRandomItem();
			uri.setCommentCategory(rcc);
			uri.setRandomComment(urc);
			uri.setStarNum(ri.getStarNum());
			userRandomItemRepository.save(uri);
			actualScored=actualScored+rcc.getPercentage()*(20*uri.getStarNum());
		}
		Company company=user.getCompany();
		if(company!=null && company.getDailySelfMarkScore()!=null && company.getDailySelfMarkScore()>0){
			actualScored=actualScored*company.getRandomCommentScore()/100;
		}
		else{
			actualScored=0d;
		}
		
		urc.setActualScored(Double.valueOf(actualScored).intValue());	
		urc=userRandomCommentRepository.save(urc);
	}
	
	
	public UserRandomComment getRandom(User user,Integer yearNum,Integer weekOfYear) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(yearNum==null || weekOfYear==null){//set default to last week
			
			WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar(Calendar.getInstance());
			yearNum=wcm.getYearNum();
			weekOfYear=wcm.getWeekofyear();
		}
		List<UserRandomComment> urclist= userRandomCommentRepository.findByYearNumAndWeekOfYearAndUser(yearNum, weekOfYear, user);
		if(urclist.size()>0){
			return urclist.get(0);
		}
		return null;
	}
	
	public UserRandomComment getCommentRandom(User user,Integer yearNum,Integer weekOfYear) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(yearNum==null || weekOfYear==null){//set default to last week
			
			WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
			yearNum=wcm.getYearNum();
			weekOfYear=wcm.getWeekofyear();
		}
		List<UserRandomComment> urclist= userRandomCommentRepository.findByYearNumAndWeekOfYearAndByUser(yearNum, weekOfYear, user);
		if(urclist.size()>0){
			return urclist.get(0);
		}
		return null;
	}
	
	public Page<UserRandomComment> getRandom(User user,Integer yearNum,int page,int size) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(yearNum==null){
			Calendar cal=Calendar.getInstance();
			yearNum=cal.get(Calendar.YEAR);
		}
		Pageable pageable=new PageRequest(page-1, size);
		return userRandomCommentRepository.findByYearNumAndUserOrderByWeekOfYearDesc(yearNum, user, pageable);
	}
	
	public List<RandomCommentCategory> getRandomCategory() throws Exception{
		return randomCommentCategoryRepository.findAll();
	}

}
