package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.MeritAcceptRecord;
import cn.aolc.group.performance.jpa.User;

@Transactional(propagation=Propagation.REQUIRED)
public interface MeritAcceptRepository extends JpaRepository<MeritAcceptRecord, Long>{
	
	public Long countByUserAndYearNum(User user,Integer yearNum);

}
