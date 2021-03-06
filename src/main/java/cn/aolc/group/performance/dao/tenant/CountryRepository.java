package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.Country;

@Transactional
public interface CountryRepository extends JpaRepository<Country, Long>{
	
	public List<Country> findByTenantIdOrderByIdAsc(String tenantId);
	
	public List<Country> findByTenantIdOrderByWealthDesc(String tenantId);

}
