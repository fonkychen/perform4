package cn.aolc.group.performance.dao.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.aolc.group.performance.jpa.tenant.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>,JpaSpecificationExecutor<Activity>{
	
	//public Page<Activity> findByIdNotNullOrderByPublishDateDesc(Pageable pageable);

}
