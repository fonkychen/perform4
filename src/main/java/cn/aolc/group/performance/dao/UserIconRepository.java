package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserIcon;

@Transactional
public interface UserIconRepository extends JpaRepository<UserIcon, Long>{

}
