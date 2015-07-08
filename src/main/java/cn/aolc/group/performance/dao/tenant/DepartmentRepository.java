package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.Department;
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	
	public List<Department> findByTenantIdOrderByIdAsc(String tenantId);

}
