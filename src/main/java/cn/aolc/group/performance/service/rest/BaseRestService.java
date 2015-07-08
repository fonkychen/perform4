 package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.WorkOfftimeRepository;
import cn.aolc.group.performance.dao.WorkOvertimeRepository;
import cn.aolc.group.performance.dao.tenant.CalendarRecordRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WorkOfftime;
import cn.aolc.group.performance.jpa.WorkOvertime;
import cn.aolc.group.performance.jpa.enumeration.WorkOvertimeStatus;
import cn.aolc.group.performance.jpa.tenant.CalendarRecord;
import cn.aolc.group.performance.security.UserPasswordDetails;

/**
 * 可以不在请求线程中访问此类
 * @author fonky
 *
 */
@Service("baseRestService")
public class BaseRestService implements ApplicationContextAware{
	
	@Autowired
	protected  MessageSource localeMessages;
	
	protected ApplicationContext applicationContext;
	
	@Autowired
	private WorkOvertimeRepository workOvertimeRepository;
	
	@Autowired
	private CalendarRecordRepository calendarRecordRepository;
	
	@Autowired
	private WorkOfftimeRepository workOfftimeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 获取当前线程的用户
	 * @return
	 * @throws Exception
	 */
	public User getContextUser() throws Exception{
		if(SecurityContextHolder.getContext().getAuthentication()==null) return null;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserPasswordDetails){
			return userRepository.findOne(((UserPasswordDetails)principal).getUser().getId());
		}
		return null;
	}


	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	
	public boolean isAuthorized(User superUser,User user){
		if(superUser.getOwnerGroup()==null)return false;
		//for freeman
		if(user.getUserGroup()==null && superUser.equals(user)) return true;
		
		if(user.getUserGroup().equals(superUser.getOwnerGroup())) return true;
		
		boolean isauthorized=false;
		
		List<User> susers=superUser.getOwnerGroup().getUsers();
		for(User suser:susers){
			if(!isauthorized){
				isauthorized=isauthorized || isAuthorized(suser, user);
			}
			else{
				break;
			}
			
		}
		return isauthorized;
		
	}
	
	public WorkOvertime findWorkOvertime(Integer yearNum,Integer monthNum,Integer dayNum,User user){
		List<WorkOvertime> wolist=workOvertimeRepository.findByYearNumAndMonthNumAndDayNumAndUser(yearNum, monthNum, dayNum, user);
		if(wolist.size()>0){
			return wolist.get(0);
		}
		return null;
	}
	
	/**
	 * 获取公司工作日历
	 * @param yearNum
	 * @param monthNum
	 * @param dayNum
	 * @param company
	 * @return
	 */
	public CalendarRecord getCalendarRecord(Integer yearNum,Integer monthNum,Integer dayNum,Company company){
		List<CalendarRecord> crlist=calendarRecordRepository.findByYearNumAndMonthNumAndDayNumAndTenantId(yearNum, monthNum, dayNum, company.getCode());
		if(crlist.size()>0){
			CalendarRecord cr=crlist.get(0);
			return cr;
		}
		return null;
	}
	
	/**
	 * 是否是公司正常上班日
	 * @param yearNum
	 * @param monthNum
	 * @param dayNum
	 * @param company
	 * @return
	 */
	//@Caching
	public boolean isWorkTime(Integer yearNum,Integer monthNum,Integer dayNum,Company company){
		CalendarRecord cr=getCalendarRecord(yearNum, monthNum, dayNum, company);
		if(cr!=null && cr.getEnabled()!=null){
			return cr.getEnabled();
		}
		Calendar cal=Calendar.getInstance();
		cal.set(yearNum, monthNum-1, dayNum);
		return !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}
	
	/**
	 * 先检查请假记录
	 * 再检查正常上班日
	 * 最后检查是否加班
	 * @param yearNum
	 * @param monthNum
	 * @param dayNum
	 * @param user
	 * @return
	 */
	public boolean isEnabledForUser(Integer yearNum,Integer monthNum,Integer dayNum,User user){
	    
	    
	   
	    
	    if(isWorkTime(yearNum, monthNum, dayNum, user.getCompany())){//工作日
	    	List<WorkOfftime> workOfftimes=workOfftimeRepository.findByYearNumAndMonthNumAndDayNumAndUser(yearNum, monthNum, dayNum, user);
	 	    if(workOfftimes.size()>0){
	 	    	WorkOfftime wo=workOfftimes.get(0);
	 	    	return !wo.getIsTrue();
	 	    }
	 	    else{
	 	    	return true;
	 	    }
	    }
	    else{//假日
	    	List<WorkOvertime> workOvertimes=workOvertimeRepository.findByYearNumAndMonthNumAndDayNumAndUser(yearNum, monthNum, dayNum, user);
			if(workOvertimes.size()>0){
				WorkOvertime wo=workOvertimes.get(0);
				
				return wo.getStatus().equals(WorkOvertimeStatus.CONFIRMED);
			}
			else{
				return false;
			}
	    }
	    
			
		
	}

}
