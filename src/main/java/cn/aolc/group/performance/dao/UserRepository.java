package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.jpa.enumeration.UserStatus;
import cn.aolc.group.performance.jpa.tenant.Country;
import cn.aolc.group.performance.jpa.tenant.Department;
import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.jpa.tenant.UserGroup;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u WHERE u.company=?1 AND u.userStatus!=?2 AND ?3 MEMBER OF u.roles")
	public List<User> findByCompanyAndUserStatusNotAndMemberOfRoles(Company company,UserStatus userStatus,RoleType role);
	
	public List<User> findByCompanyAndUserStatusNotAndDepartmentNot(Company company,UserStatus userStatus,Department department);
	
	public List<User> findByEmailAddressIgnoreCase(String emailAddress);
	
	public List<User> findByCompanyAndUserStatusOrderByDepartmentAsc(Company company,UserStatus userStatus);
	
	public List<User> findByCompanyAndUserStatus(Company company,UserStatus userStatus);
	
	public List<User> findByCompanyOrderByIdDesc(Company company);
	
	public List<User> findByDepartmentAndUserStatusNotOrderByCountryAsc(Department department,UserStatus userStatus);
	
	public List<User> findByCompanyAndUserStatusNotOrderByIdAsc(Company company,UserStatus userStatus);
	
	public List<User> findByCompanyAndUserStatusNotOrderByCountryAsc(Company company,UserStatus userStatus); 
	
	public List<User> findByCompanyAndUserStatusNotOrderByUserScoredDesc(Company company,UserStatus userStatus);
	
	public List<User> findByCompanyAndUserStatusNotOrderByUserPopularDesc(Company company,UserStatus userStatus);
	
	public List<User> findByCompanyAndUserStatusNotOrderByUserCoinsDesc(Company company,UserStatus userStatus);
	
	@Query("select u from User u where u.company=?1 and u.title in ?2 order by u.id asc")
	public List<User> findByCompanyAndTitles(Company company,List<Title> titles);
	
	public List<User> findByCompanyAndCountryAndUserStatusNotOrderByIdDesc(Company company,Country country,UserStatus userStatus);
	
	public List<User> findByCompanyAndNickName(Company company,String nickName);
	
	public List<User> findByCompanyOrderByWorkerIdDesc(Company company);
	
	public List<User> findByCompanyAndNameContainingAndUserStatusNotOrderByNameDesc(Company company,String name,UserStatus userStatus);
//	
	public Page<User> findByCompanyAndUserStatusNot(Company company,UserStatus userStatus,Pageable pageable);
	

	
	public List<User> findByUserStatusNot(UserStatus userstatus);
	
	public List<User> findByUserGroupAndUserStatusNot(UserGroup userGroup,UserStatus userStatus);
	
	public List<User> findByCompanyAndCountryAndUserStatusNot(Company company,Country country,UserStatus userStatus);
	
	@Query("select u from User u where u.userStatus!=?1 and u.userGroup in ?2 order by u.id asc")
	public List<User> findByUserStatusNotAndUserGroupIn(UserStatus userStatus,List<UserGroup> userGroups);
}
