package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserTaskSlice;
import cn.aolc.group.performance.jpa.UserTaskSliceApply;
import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceApplyStatus;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserTaskSliceApplyRepository extends JpaRepository<UserTaskSliceApply, Long>{
	public List<UserTaskSliceApply> findByTaskSliceOrderByCreatedDesc(UserTaskSlice taskSlice);
	
	public List<UserTaskSliceApply> findByTaskSliceAndApplyStatus(UserTaskSlice taskSlice,UserTaskSliceApplyStatus applyStatus);
}
