package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.dao.UserRewardApplyRepository;
import cn.aolc.group.performance.dao.UserRewardResponseRepository;
import cn.aolc.group.performance.dao.UserTaskSliceApplyRepository;
import cn.aolc.group.performance.dao.UserTaskSliceRepository;
import cn.aolc.group.performance.dao.UserTaskSliceResponseRepository;
import cn.aolc.group.performance.dao.tenant.UserRewardRepository;
import cn.aolc.group.performance.dao.tenant.UserTaskRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRewardApply;
import cn.aolc.group.performance.jpa.UserRewardResponse;
import cn.aolc.group.performance.jpa.UserTaskSlice;
import cn.aolc.group.performance.jpa.UserTaskSliceApply;
import cn.aolc.group.performance.jpa.UserTaskSliceResponse;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.jpa.enumeration.UserRewardApplyStatus;
import cn.aolc.group.performance.jpa.enumeration.UserRewardStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceApplyStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskType;
import cn.aolc.group.performance.jpa.tenant.UserReward;
import cn.aolc.group.performance.jpa.tenant.UserTask;
import cn.aolc.group.performance.sync.UpdateCoinService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class RewardTaskService extends BaseRestService{
	
	@Autowired
	private UserRewardRepository userRewardRepository;
	
	@Autowired
	private UserTaskRepository userTaskRepository;
	
	@Autowired
	private UserRewardApplyRepository userRewardApplyRepository;
	
	@Autowired
	private UserRewardResponseRepository userRewardResponseRepository;
	
	@Autowired
	private UserTaskSliceRepository userTaskSliceRepository;
	
	@Autowired
	private UserTaskSliceApplyRepository userTaskSliceApplyRepository;
	
	@Autowired
	private UserTaskSliceResponseRepository userTaskSliceResponseRepository;
	
	@Autowired
	private UpdateCoinService updateCoinService;
	
	public UserReward getReward(Long id) throws Exception{
		return userRewardRepository.findOne(id);
	}
	
	public UserRewardApply getRewardApply(Long id) throws Exception{
		return userRewardApplyRepository.findOne(id);
	}
	
	public UserReward saveReward(String title,String description,Integer dateGap,Long coinNum) throws Exception{
		User user=getContextUser();
		UserReward ur=new UserReward();
		
		ur.setByUser(user);
		ur.setCoinNum(coinNum);
		ur.setDateGap(dateGap);
		ur.setDescription(description);
		ur.setPublishDate(new Date());
		ur.setRewardStatus(UserRewardStatus.OnGoing);
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, dateGap);
		ur.setEndDate(cal.getTime());
		
		ur.setTenantId(user.getCompany().getCode());
		ur.setTitle(title);
		return userRewardRepository.save(ur);
	}
	
	public Page<UserReward> getRewardList(User user,Integer page,Integer size,String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(size==null){
			size=10;
		}
		if(user==null){
			user=getContextUser();
		}
		Sort sort=new Sort(new Order(Direction.ASC,"rewardStatus"),new Order(order.equals("asc")?Direction.ASC:Direction.DESC, "publishDate"));
		Pageable pageable=new PageRequest(page-1, size, sort);
		return userRewardRepository.findByTenantIdOrderByRewardStatusAscAndPublishDateDesc(user.getCompany().getCode(), pageable);
	}
	
	public UserTask getTask(Long id) throws Exception{
		return userTaskRepository.findOne(id);
	}
	
	public UserTaskSlice getTaskSlice(Long id) throws Exception{
		return userTaskSliceRepository.findOne(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserTaskSliceApply getSliceApply(Long id) throws Exception{
		return userTaskSliceApplyRepository.findOne(id);
	}
	
	@Secured("ROLE_ADMIN")
	public UserTask saveTask(String title,UserTaskType taskType,String description,Long coinNum) throws Exception{
		User user=getContextUser();
		UserTask ut=new UserTask();
		ut.setCoinNum(coinNum);
		ut.setDescription(description);
		ut.setPublishDate(new Date());
		ut.setTaskStatus(UserTaskStatus.OnGoing);
		ut.setTaskType(taskType);
		ut.setTenantId(user.getCompany().getCode());
		ut.setTitle(title);
		ut.setUser(user);
		return userTaskRepository.save(ut);
	}
	
	public Page<UserTask> getTaskList(Company company,Integer page,Integer size,String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(size==null){
			size=10;
		}
		if(company==null){
			company=getContextUser().getCompany();
		}
		Sort sort=new Sort(new Order(Direction.ASC,"taskStatus"),new Order(order.equals("asc")?Direction.ASC:Direction.DESC, "publishDate"));
		Pageable pageable=new PageRequest(page-1, size, sort);
		return userTaskRepository.findByTenantId(company.getCode(), pageable);
	}
	
	public UserRewardApply applyReward(User user,UserReward userReward) throws Exception{
		if(user==null)user=getContextUser();
		if(user.equals(userReward.getByUser())) throw new Exception("自己领取自己的悬赏？");
		
		UserRewardApply apply=new UserRewardApply();
		apply.setApplyStatus(UserRewardApplyStatus.NotProcessed);
		apply.setCreated(new Date());
		apply.setUser(user);
		apply.setUserReward(userReward);
		return userRewardApplyRepository.save(apply);
	}
	
	public UserRewardResponse respondRewardApply(User user,UserRewardApply rewardApply,String content) throws Exception{
		if(user==null)user=getContextUser();
		UserRewardResponse response=new UserRewardResponse();
		response.setContent(content);
		response.setCreated(new Date());
		response.setRewardApply(rewardApply);
		response.setUser(user);
		return userRewardResponseRepository.save(response);
	}
	
	/**
	 * may result in coin changes
	 * @param user
	 * @param rewardApply
	 * @param applyStatus
	 * @return
	 * @throws Exception
	 */
	//@UserCoinAdded(coinType={CoinType.AcceptReward,CoinType.AcceptRewardOut})
	public UserRewardApply setRewardApplyStatus(User user,UserRewardApply rewardApply,UserRewardApplyStatus applyStatus) throws Exception{
		if(user==null)user=getContextUser();
		if(!user.equals(rewardApply.getUserReward().getByUser())) throw new Exception("Not Allowed");
		if(applyStatus.equals(UserRewardApplyStatus.NotProcessed) || !rewardApply.getApplyStatus().equals(UserRewardApplyStatus.NotProcessed))throw new Exception("Invalid Operation");
		if(applyStatus.equals(UserRewardApplyStatus.Accepted)){//check user coin
			if(user.getUserCoins()<rewardApply.getUserReward().getCoinNum()) throw new Exception("No enough coin");			
		}
		rewardApply.setApplyStatus(applyStatus);
		rewardApply=userRewardApplyRepository.save(rewardApply);
		updateCoinService.updateCoinHistory(CoinType.AcceptRewardOut, rewardApply);
		updateCoinService.updateCoinHistory(CoinType.AcceptReward, rewardApply);		
		return rewardApply;
	}
	
	public UserReward closeReward(User user,UserReward userReward) throws Exception{
		if(user==null) user=getContextUser();
		if(!user.equals(userReward.getByUser()) && !user.getRoles().contains(RoleType.ADMIN)) throw new Exception("Not Allowed");
		if(!userReward.getRewardStatus().equals(UserRewardStatus.OnGoing)) throw new Exception("Invalid Operation");
		userReward.setRewardStatus(UserRewardStatus.Closed);
		return userRewardRepository.save(userReward);
	}
	
	public UserTaskSlice createTaskSlice(UserTask task) throws Exception{
		UserTaskSlice ts=new UserTaskSlice();
		Calendar cal=Calendar.getInstance();
		ts.setCreated(new Date());
		ts.setDayNum(cal.get(Calendar.DATE));
		ts.setMonthNum(cal.get(Calendar.MONTH)+1);
		ts.setUpdated(new Date());
		ts.setUserTask(task);
		ts.setWeekOfYear(cal.get(Calendar.WEEK_OF_YEAR));
		ts.setYearNum(cal.get(Calendar.YEAR));
		return userTaskSliceRepository.save(ts);
	}
	
	public UserTaskSliceApply applyTaskSlice(User user,UserTaskSlice taskSlice) throws Exception{
		if(user==null) user=getContextUser();
		UserTaskSliceApply apply=new UserTaskSliceApply();
		apply.setApplyStatus(UserTaskSliceApplyStatus.Not_Processed);
		apply.setCreated(new Date());
		apply.setTaskSlice(taskSlice);
		apply.setUser(user);
		return userTaskSliceApplyRepository.save(apply);
	}
	
	public UserTaskSliceResponse respondSliceApply(User user,UserTaskSliceApply apply,String content) throws Exception{
		if(user==null)user=getContextUser();
		UserTaskSliceResponse response=new UserTaskSliceResponse();
		response.setContent(content);
		response.setCreated(new Date());
		response.setSliceApply(apply);
		response.setUser(user);
		return userTaskSliceResponseRepository.save(response);
	}
	
	@UserCoinAdded(coinType=CoinType.AcceptTask)
	public UserTaskSliceApply setSliceApplyStatus(User user,UserTaskSliceApply apply,UserTaskSliceApplyStatus applyStatus) throws Exception{
		if(user==null) user=getContextUser();
		if(!user.getRoles().contains(RoleType.ADMIN)) throw new Exception("Not Allowed");
		if(!apply.getApplyStatus().equals(UserTaskSliceApplyStatus.Not_Processed)) throw new Exception("Invalid Operation");
		apply.setApplyStatus(applyStatus);
		return userTaskSliceApplyRepository.save(apply);
	}
	
	public UserTask closeUserTask(User user,UserTask userTask) throws Exception{
		if(user==null) user=getContextUser();
		if(!user.getRoles().contains(RoleType.ADMIN)) throw new Exception("Not Allowed");
		userTask.setTaskStatus(UserTaskStatus.Closed);
		List<UserTaskSlice> tslist=userTaskSliceRepository.findByUserTaskAndSliceStatus(userTask, UserTaskSliceStatus.OnGoing);
		for(UserTaskSlice ts:tslist){
			ts.setSliceStatus(UserTaskSliceStatus.Closed);
			List<UserTaskSliceApply> salist=userTaskSliceApplyRepository.findByTaskSliceAndApplyStatus(ts, UserTaskSliceApplyStatus.Not_Processed);
			for(UserTaskSliceApply sa:salist){
				sa.setApplyStatus(UserTaskSliceApplyStatus.Close);
			}
			userTaskSliceApplyRepository.save(salist);
		}
		userTaskSliceRepository.save(tslist);
		
		return userTaskRepository.save(userTask);
	}

}
