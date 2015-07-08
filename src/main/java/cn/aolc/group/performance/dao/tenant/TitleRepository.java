package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.jpa.tenant.TitleGroup;
@Transactional(propagation=Propagation.REQUIRED)
public interface TitleRepository extends JpaRepository<Title, Long>{
	
	public List<Title> findByTitleGroupAndTenantIdOrderByRankAsc(TitleGroup titleGroup,String tenantId);
	
	public List<Title> findByTitleGroupAndBottomLessThanEqualAndTenantIdOrderByRankDesc(TitleGroup titleGroup,Integer userScored,String tenantId);
	
	public List<Title> findByTitleGroupAndRankAndTenantId(TitleGroup titleGroup,Integer rank,String tenantId);
	
	public List<Title> findByTitleGroupAndRank(TitleGroup titleGroup,Integer rank);
	
}
