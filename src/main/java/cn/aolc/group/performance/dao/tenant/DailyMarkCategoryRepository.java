package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.DailyMarkCategory;
@Transactional
public interface DailyMarkCategoryRepository extends JpaRepository<DailyMarkCategory, Long>{
	
	public List<DailyMarkCategory> findByTenantIdOrderByIdDesc(String tenantId);

}
