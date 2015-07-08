package cn.aolc.group.performance.service.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.WorkOfftimeRepository;
import cn.aolc.group.performance.dao.WorkOvertimeRepository;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WorkOfftime;
import cn.aolc.group.performance.jpa.WorkOvertime;
import cn.aolc.group.performance.jpa.enumeration.WorkOvertimeStatus;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class WorkStatusService extends BaseRestService{
	@Autowired
	private WorkOfftimeRepository workOfftimeRepository;
	
	@Autowired
	private WorkOvertimeRepository workOvertimeRepository;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void updateStatus(User user,Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		List<WorkOfftime> offs=workOfftimeRepository.findByYearNumAndMonthNumAndDayNumAndUser(yearNum, monthNum, dayNum, user);
		List<WorkOvertime> overs=workOvertimeRepository.findByYearNumAndMonthNumAndDayNumAndUser(yearNum, monthNum, dayNum, user);
		List<DailyBoard> dblist=dailyBoardRepository.findByUserAndYearNumAndMonthNumAndDayNum(user, yearNum, monthNum, dayNum);
		WorkOfftime offtime=null;
	    WorkOvertime overtime=null;
	    DailyBoard db=null;
	    if(offs.size()>0){
	    	offtime=offs.get(0);
	    }
	    if(overs.size()>0){
	    	overtime=overs.get(0);
	    }
	    if(dblist.size()>0){
	    	db=dblist.get(0);
	    }
		if(isWorkTime(yearNum, monthNum, dayNum, user.getCompany())){//工作日
			if(db==null || db.getTaskType()==null){
				//not processed
			}
			else if(db.getTaskType().getIsValid()){//正常
				if(offtime!=null){
					offtime.setIsTrue(false);
					offtime.setUpdated(new Date());
					workOfftimeRepository.save(offtime);
				}
			}
			else{//请假
				if(offtime==null){
					offtime=new WorkOfftime();
					offtime.setDayNum(dayNum);
					offtime.setMonthNum(monthNum);
					offtime.setUser(user);
					offtime.setYearNum(yearNum);
				}
				offtime.setIsTrue(true);
				offtime.setUpdated(new Date());
				workOfftimeRepository.save(offtime);
			}
		}
		else{//假日
			
			if(db==null || db.getTaskType()==null){
				
			}
		    else if(db.getTaskType().getIsValid()){//加班
		    	if(overtime==null){
					overtime=new WorkOvertime();
					overtime.setDayNum(dayNum);
					overtime.setMonthNum(monthNum);					
					overtime.setUser(user);
					overtime.setYearNum(yearNum);
				}
		    	if(!user.equals(getContextUser()) && isAuthorized(getContextUser(), user)){
		    		overtime.setStatus(WorkOvertimeStatus.CONFIRMED);
		    		
		    	}
		    	else {
		    		overtime.setStatus(WorkOvertimeStatus.NOT_PROCESSED);
		    	}
			   	
			   	
			   	
			   	overtime.setUpdated(new Date());
			   	workOvertimeRepository.save(overtime);
			    
			}
			else{//假日 不工作 
				if(overtime!=null){
					if(!user.equals(getContextUser()) && isAuthorized(getContextUser(), user)){
						overtime.setStatus(WorkOvertimeStatus.REJECTED);
						
					}
					else{
						overtime.setStatus(WorkOvertimeStatus.ABANDON);
					}
					overtime.setUpdated(new Date());
					workOvertimeRepository.save(overtime);
				}
			}
		}
	}
	
	public List<WorkOvertime> getOvertimeToConfirm(User user) throws Exception{
		User nuser=null;
		if(user==null){
			user=getContextUser();
			nuser=userRepository.findOne(user.getId());
		}
		if(nuser==null || nuser.getOwnerGroup()==null) throw new Exception("没有操作的对象");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		return workOvertimeRepository.findByDateAfterAndUserIn(date, nuser.getOwnerGroup().getUsers());
	}
	
	public List<WorkOvertime> getOvertimeToConfirm(Integer yearNum,Integer monthNum,User user) throws Exception{
		User nuser=null;
		if(user==null){
			user=getContextUser();
			nuser=userRepository.findOne(user.getId());
		}
		if(nuser==null || nuser.getOwnerGroup()==null) throw new Exception("没有操作的对象");
		return workOvertimeRepository.findByYearNumAndMonthNumAndUserIn(yearNum, monthNum, nuser.getOwnerGroup().getUsers());
	}
	
	public Page<WorkOvertime> getOvertimeToConfirm(User user,Integer pageNum) throws Exception{
		Pageable pageable=new PageRequest((pageNum-1), 10);
		User nuser=null;
		if(user==null){
			user=getContextUser();
			nuser=userRepository.findOne(user.getId());
		}
		if(nuser==null || nuser.getOwnerGroup()==null) throw new Exception("没有操作的对象");
		return workOvertimeRepository.findByUserIn(nuser.getOwnerGroup().getUsers(), pageable);
		
	}
	
	public WorkOvertime setOvertimeStatus(Long id,WorkOvertimeStatus status) throws Exception{
		WorkOvertime overtime=workOvertimeRepository.findOne(id);
		if(overtime==null) throw new Exception("没有可以操作的对象");
		if(!overtime.getStatus().equals(WorkOvertimeStatus.NOT_PROCESSED)) throw new Exception("不允许的操作");
		overtime.setStatus(status);
		return workOvertimeRepository.save(overtime);
	}

}
