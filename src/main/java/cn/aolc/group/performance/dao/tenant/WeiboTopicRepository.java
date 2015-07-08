package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.tenant.WeiboTopic;

@Transactional(propagation=Propagation.REQUIRED)
public interface WeiboTopicRepository extends JpaRepository<WeiboTopic, Long>{
	
	@Query("select wt from WeiboTopic wt order by wt.created desc")
	public Page<WeiboTopic> findByCreatedDesc(Pageable pageable);
	
	@Query("select wt from WeiboTopic wt order by wt.degree desc,wt.created desc")
	public Page<WeiboTopic> findByDegreeDescAndCreatedDesc(Pageable pageable);
	
	public Page<WeiboTopic> findByUserOrderByCreatedDesc(User user,Pageable pageable);
	
	public Page<WeiboTopic> findByUserOrderByDegreeDesc(User user,Pageable pageable);
	
	@Query("select wt from WeiboTopic wt where wt in ?1 and ?2 member of wt.favorUsers")
	public List<WeiboTopic> findByTopicInAndUserFavored(List<WeiboTopic> topics,User user);

}
