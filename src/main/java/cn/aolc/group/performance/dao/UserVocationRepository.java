package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserVocation;
import cn.aolc.group.performance.jpa.enumeration.VocationType;

@Transactional
public interface UserVocationRepository extends JpaRepository<UserVocation, Long>{
	
	public List<UserVocation> findByYearNumAndUserAndVocationType(Integer yearNum,User user,VocationType vocationType);
	
	@Query("select uv from UserVocation uv where uv.yearNum=?1 and uv.user in ?2 and uv.vocationType=?3")
	public List<UserVocation> findByYearNumAndUserInAndVocationType(Integer yearNum,List<User> userlist,VocationType vocationType);

}
