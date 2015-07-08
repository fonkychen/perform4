package cn.aolc.group.performance.dao.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.UserGroup;

@Transactional
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>{

}
