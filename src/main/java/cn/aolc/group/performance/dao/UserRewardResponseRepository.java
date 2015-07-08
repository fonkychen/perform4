package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserRewardResponse;

@Transactional
public interface UserRewardResponseRepository extends JpaRepository<UserRewardResponse, Long>{
	
}
