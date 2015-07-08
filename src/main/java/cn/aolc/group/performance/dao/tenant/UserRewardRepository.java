package cn.aolc.group.performance.dao.tenant;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.UserRewardStatus;
import cn.aolc.group.performance.jpa.enumeration.UserRewardType;
import cn.aolc.group.performance.jpa.tenant.UserReward;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserRewardRepository extends JpaRepository<UserReward, Long>{
//	
//	public Page<UserReward> findByTenantIdOrderByPublishDateDesc(String tenantId,Pageable pageable);
//	
//	public List<UserReward> findByRewardStatusAndEndDateBeforeAndTenantId(UserRewardStatus rewardStatus,Date endDate,String tenantId);
//	
//	public List<UserReward> findByRewardStatusAndEndDateBefore(UserRewardStatus rewardStatus,Date endDate);
//	
//	@Query("select count(ur.id) from UserReward ur where ur.rewardStatus=?1 and ur.tenantId=?2")
//	public Integer countByRewardStatus(UserRewardStatus rewardStatus,String tenantId);
//	
//	public List<UserReward> findByRewardStatusAndByUser(UserRewardStatus rewardStatus,User byUser);
	
	@Query(countQuery="select count(ur.id) from UserReward ur where ur.tenantId=?1",value="select ur from UserReward ur where ur.tenantId=?1 order by ur.rewardStatus asc,ur.publishDate desc")
	public Page<UserReward> findByTenantIdOrderByRewardStatusAscAndPublishDateDesc(String tenantId,Pageable pageable);
	
	public List<UserReward> findByRewardStatusAndEndDateBefore(UserRewardStatus rewardStatus,Date endDate);
	
}
