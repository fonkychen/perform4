package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.Qualification;

@Transactional
public interface QualificationRepository extends JpaRepository<Qualification, Long>{
	public List<Qualification> findByTenantIdOrderByLevelAsc(String tenantId);	
}
