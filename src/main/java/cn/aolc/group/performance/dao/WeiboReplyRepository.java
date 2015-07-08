package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WeiboReply;
import cn.aolc.group.performance.jpa.tenant.WeiboTopic;

@Transactional(propagation=Propagation.REQUIRED)
public interface WeiboReplyRepository extends JpaRepository<WeiboReply, Long>{
	
	@Query("select wr from WeiboReply wr where wr.weiboTopic in ?1 order by wr.created desc")
	public List<WeiboReply> findByWeiboTopicInOrderByCreatedDesc(List<WeiboTopic> topics);
	
	public List<WeiboReply> findByUserOrderByCreatedDesc(User user);
	
	public Page<WeiboReply> findByWeiboTopicOrderByCreatedDesc(WeiboTopic weiboTopic,Pageable pageable);
}
