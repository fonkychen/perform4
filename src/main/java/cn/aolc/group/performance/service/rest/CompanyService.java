package cn.aolc.group.performance.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.aolc.group.performance.dao.CompanyDailyReportRepository;
import cn.aolc.group.performance.dao.CompanyRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.CompanyDailyReport;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.tenant.UserGroup;

@Service
public class CompanyService extends BaseRestService{
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyDailyReportRepository companyDailyReportRepository;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository;
	
	public Company getById(Long id){
		return companyRepository.findOne(id);
	}
	
	public List<UserSelfMark> getCompanyGroupSelfmarks(User user,Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		if(user.getTitle()==null || user.getTitle().getTitleGroup()==null 
				|| user.getTitle().getTitleGroup().getLevel()==null || user.getTitle().getTitleGroup().getLevel()!=1) return new ArrayList<UserSelfMark>();
		List<UserGroup> ugs=user.getOwnerGroups();
		
		if(ugs==null || ugs.size()<=0) return new ArrayList<UserSelfMark>();
		List<User> users=new ArrayList<User>();
		for(UserGroup ug:ugs){
			users.addAll(ug.getUsers());
		}
		users.add(user);
		return userSelfMarkRepository.findByUserInAndYearNumAndMonthNumAndDayNum(users, yearNum, monthNum, dayNum);
	}
	
	public CompanyDailyReport getDailyReport(Company company,Integer yearNum,Integer monthNum,Integer dayNum) throws Exception{
		List<CompanyDailyReport> cdrlist=companyDailyReportRepository.findByYearNumAndMonthNumAndDayNumAndCompany(yearNum, monthNum, dayNum, company);
		if(cdrlist.size()>0){
			return cdrlist.get(0);
		}
		return null;
	}

}
