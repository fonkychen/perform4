package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserRewardApply;
import cn.aolc.group.performance.jpa.enumeration.UserRewardApplyStatus;
import cn.aolc.group.performance.jpa.tenant.UserReward;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserRewardApplyRepository extends JpaRepository<UserRewardApply, Long>{
	
	public Page<UserRewardApply> findByUserReward(UserReward userReward,Pageable pageable);
	
	public List<UserRewardApply> findByUserRewardAndApplyStatus(UserReward userReward,UserRewardApplyStatus applyStatus);
	
	public List<UserRewardApply> findByApplyStatus(UserRewardApplyStatus applyStatus);

}
