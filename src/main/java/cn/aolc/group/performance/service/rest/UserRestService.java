package cn.aolc.group.performance.service.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.IndicatorRepository;
import cn.aolc.group.performance.dao.UserIconGroupRepository;
import cn.aolc.group.performance.dao.UserIconRepository;
import cn.aolc.group.performance.dao.UserMessageRepository;
import cn.aolc.group.performance.dao.UserNotifyRepository;
import cn.aolc.group.performance.dao.UserRandomCommentRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.tenant.DailyMarkCategoryRepository;
import cn.aolc.group.performance.dao.tenant.TitleGroupRepository;
import cn.aolc.group.performance.dao.tenant.TitleRepository;
import cn.aolc.group.performance.dao.tenant.UserDailyRankRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserIcon;
import cn.aolc.group.performance.jpa.UserIconGroup;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.UserNotify;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.DailyMarkCategory;
import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.jpa.tenant.TitleGroup;
import cn.aolc.group.performance.jpa.tenant.UserDailyRank;
import cn.aolc.group.performance.model.MessageModel;
import cn.aolc.group.performance.model.WeeklyCalendarModel;
import cn.aolc.group.performance.util.PerformanceUtil;

@Service("userRestService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserRestService extends BaseRestService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMessageRepository userMessageRepository;
	
	@Autowired
	private UserNotifyRepository userNotifyRepository;
	
	@Autowired
	private DailyMarkCategoryRepository dailyMarkCategoryRepository;
	
	@Autowired
	private IndicatorRepository indicatorRepository;
	
	@Autowired
	private UserIconRepository userIconRepository;
	
	@Autowired
	private UserRandomCommentRepository userRandomCommentRepository;
	
	@Autowired
	private UserDailyRankRepository userDailyRankRepository;
	
	@Autowired
	private TitleGroupRepository titleGroupRepository;
	
	@Autowired
	private TitleRepository titleRepository;
	
	@Autowired
	private UserIconGroupRepository userIconGroupRepository;
	
	public MessageModel getMessages() throws Exception{
		User user=getContextUser();
		List<UserMessage> umlist=userMessageRepository.findByUserAndIsReadOrderByIdDesc(user, false, new PageRequest(0, 5));
		List<UserNotify> unlist=userNotifyRepository.findByUser(user);
		MessageModel mm=new MessageModel();
		mm.setMessageList(umlist);
		mm.setNotifyList(unlist);
		return mm;
	}
	
	public List<DailyMarkCategory> getDailyMarkCategories() throws Exception{
		//User user=getContextUser();
		return dailyMarkCategoryRepository.findAll();
	}
	
	
	@Cacheable(value="users")
	public User getUserById(Long id) throws Exception{
		return userRepository.findOne(id);
	}
	
	/**
	 * 获取下属员工
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User getGroupUser(Long userId) throws Exception{
		User cuser=userRepository.findOne(getContextUser().getId());
		User user=null;
		if(userId!=null){
			user=userRepository.findOne(userId);
			if(user!=null )if(!isAuthorized(cuser, user)) throw new Exception("没有操作的权限");
		}	    
		
		if(user==null && cuser.getOwnerGroups()!=null && cuser.getOwnerGroups().size()>0){
			List<User> ousers=cuser.getOwnerGroups().get(0).getUsers();
			if(ousers.size()>0)user=ousers.get(0);
		}
		else if(user==null && cuser.getUserGroup()==null){
			user=cuser;
		}
		
		if(user==null){
			throw new Exception("没有可以操作的对象");
		}
		return user;
	}
	
	public Page<User> userRank(Company company,String type,String order,int page,int size) throws Exception{
		if(company==null){
			User user=getContextUser();
			if(user!=null)company=user.getCompany();
		}
		
		if(company==null) throw new Exception("无效的公司");
		Sort.Direction direction=null;
		if(order.equals("asc")){
			direction=Sort.Direction.ASC;
		}
		else{
			direction=Sort.Direction.DESC;
		}
		
		Pageable pageable=new PageRequest(page-1, size,direction,type);
		
		
		return userRepository.findByCompanyAndUserStatusNot(company, UserStatus.Retired, pageable);
		
	}
	
	@Cacheable
	public UserIcon getIcon(Long iconId) throws Exception{
		return userIconRepository.findOne(iconId);
	}
	
	public User selectRandomUser() throws Exception{
		User user=getContextUser();
		if(user==null || user.getCompany()==null) throw new Exception("invalid user");
		WeeklyCalendarModel wcm=PerformanceUtil.getWeeklyCalendar();
		List<UserRandomComment> urclist=userRandomCommentRepository.findByYearNumAndWeekOfYear(wcm.getYearNum(), wcm.getWeekofyear());
		List<User> users=userRepository.findByCompanyAndUserStatusNotAndDepartmentNot(user.getCompany(), UserStatus.Retired, user.getDepartment());
		List<User> retusers=new ArrayList<User>();
		for(User cuser:users){
			boolean isComment=false;
			for(UserRandomComment urc:urclist){
				if(urc.getByUser().getId().equals(user.getId())){
					throw new Exception("已点评");
				}
				if(urc.getUser().getId().equals(cuser.getId())){
					isComment=true;
					break;
				}
			}
			
			if(!isComment && !cuser.getId().equals(user.getId())){
				retusers.add(cuser);
			}
		}
		Random rand=new Random();
		int index=rand.nextInt(retusers.size());
		return retusers.get(index);
	}
	
	public List<User> listUser(Company company) throws Exception{
		if(company==null){
			User user=getContextUser();
			company=user!=null?user.getCompany():null;
		}
		
		return userRepository.findByCompanyAndUserStatusNotOrderByIdAsc(company, UserStatus.Retired);
	}
	
	
	public List<UserDailyRank> getDailyRank(User user,Integer yearNum,Integer monthNum,Integer dayNum,String type) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		String tenantId=user.getCompany().getCode();
		if(type.equals("score")){
			return userDailyRankRepository.findByTenantIdAndYearNumAndMonthNumAndDayNumOrderByScoreRankDesc(tenantId, yearNum, monthNum, dayNum);
		}
		else if(type.equals("coin")){
			return userDailyRankRepository.findByTenantIdAndYearNumAndMonthNumAndDayNumOrderByCoinRankDesc(tenantId, yearNum, monthNum, dayNum);
		}
		else if(type.equals("popular")){
			return userDailyRankRepository.findByTenantIdAndYearNumAndMonthNumAndDayNumOrderByPopularRankDesc(tenantId, yearNum, monthNum, dayNum);
		}
		
		return null;
	}
	
	public List<UserIconGroup> getUserIconGroups() throws Exception{
		return userIconGroupRepository.findAll();
	}
	
	public User getTopUser(Company company) throws Exception{
		if(company==null){
			company=getContextUser().getCompany();
		}
		List<TitleGroup> titleGroups=titleGroupRepository.findByLevelAndTenantId(1,company.getCode());
		if(titleGroups.size()<=0)throw new Exception("no group with level1");
		List<Title> ntitles=titleRepository.findByTitleGroupAndTenantIdOrderByRankAsc(titleGroups.get(0),company.getCode());
		List<User> fusers=userRepository.findByCompanyAndTitles(company, ntitles);
		if(fusers.size()>0){
			return fusers.get(0);
		}
		return null;
	}
	
	public void setIcon(User user,Long iconId) throws Exception{
		UserIcon icon=userIconRepository.findOne(iconId);
		if(user==null){
			user=getContextUser();
		}
		if(icon!=null){
			user.setUserIcon(icon);
			userRepository.save(user);
		}
	}
	
	public void setNickName(User user,String nickName) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		user.setNickName(nickName);
		userRepository.save(user);
	}
	
	public void setPassword(User user,String oldpassword,String newpassword) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		ShaPasswordEncoder spe=new ShaPasswordEncoder(256);
		String enold=spe.encodePassword(oldpassword, null);
		if(!enold.equals(user.getPassword())) throw new Exception("密码不符");
		String enpass=spe.encodePassword(newpassword, null);
		user.setPassword(enpass);
		userRepository.save(user);
	}
	
	public List<User> findUser(Company company,String keyword) throws Exception{
		if(company==null){
			company=getContextUser().getCompany();
		}
		
		return userRepository.findByCompanyAndNameContainingAndUserStatusNotOrderByNameDesc(company, keyword, UserStatus.Retired);
	}
}
