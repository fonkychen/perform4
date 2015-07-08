package cn.aolc.group.performance.weixin.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.weixin.jpa.WechatUserMapping;


@Transactional
public interface WechatUserMappingRepository extends JpaRepository<WechatUserMapping, Long>{
	
	public List<WechatUserMapping> findByOpenId(String openId);
	
	public List<WechatUserMapping> findByUser(User user);

}
