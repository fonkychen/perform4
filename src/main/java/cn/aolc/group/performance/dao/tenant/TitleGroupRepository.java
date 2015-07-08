package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.TitleGroup;
@Transactional
public interface TitleGroupRepository extends JpaRepository<TitleGroup, Long>{
	
	public List<TitleGroup> findByLevelNotNullAndTenantIdOrderByLevelDesc(String tenantId);
	
	public List<TitleGroup> findByLevelAndTenantId(Integer level,String tenantId);

}
