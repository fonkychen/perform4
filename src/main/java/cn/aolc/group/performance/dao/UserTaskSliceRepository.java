package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserTaskSlice;
import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceStatus;
import cn.aolc.group.performance.jpa.tenant.UserTask;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserTaskSliceRepository extends JpaRepository<UserTaskSlice, Long>{
	
	public Page<UserTaskSlice> findByUserTask(UserTask userTask,Pageable pageable);	
	
	public List<UserTaskSlice> findByUserTaskAndSliceStatus(UserTask userTask,UserTaskSliceStatus sliceStatus);

}
