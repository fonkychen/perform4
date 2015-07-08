package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserTaskSliceResponse;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserTaskSliceResponseRepository extends JpaRepository<UserTaskSliceResponse, Long>{

}
