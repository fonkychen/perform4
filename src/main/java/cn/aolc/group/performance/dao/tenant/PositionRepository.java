package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.Department;
import cn.aolc.group.performance.jpa.tenant.Position;
@Transactional
public interface PositionRepository extends JpaRepository<Position, Long>{
	
	public List<Position> findByDepartmentAndTenantIdOrderByLevelAsc(Department department,String tenantId);	
	

}
