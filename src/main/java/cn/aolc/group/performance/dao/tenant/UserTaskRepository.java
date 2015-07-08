package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.enumeration.UserTaskStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskType;
import cn.aolc.group.performance.jpa.tenant.UserTask;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserTaskRepository extends JpaRepository<UserTask, Long>,JpaSpecificationExecutor<UserTask>{
	public Page<UserTask> findByTenantId(String tenantId,Pageable pageable);
	
	public List<UserTask> findByTaskTypeAndTaskStatus(UserTaskType taskType,UserTaskStatus taskStatus);
}
