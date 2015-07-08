package cn.aolc.group.performance.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.aolc.group.performance.jpa.PopularEggGuard;
import cn.aolc.group.performance.jpa.User;

public interface PopularEggGuardRepository extends JpaRepository<PopularEggGuard, Long>{
	
	public List<PopularEggGuard> findByUserAndExpiredAfter(User user,Date expired);

}
