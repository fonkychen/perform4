package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.UserRandomItem;

@Transactional
public interface UserRandomItemRepository extends JpaRepository<UserRandomItem, Long>{

}
