package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.enumeration.MallProductStatus;
import cn.aolc.group.performance.jpa.tenant.MallProduct;

@Transactional
public interface MallProductRepository extends JpaRepository<MallProduct, Long>{
	
	public List<MallProduct> findByProductStatusAndTenantIdOrderByCoinNumDesc(MallProductStatus productStatus,String tenantId);
	
	public Page<MallProduct> findByTenantIdOrderByCoinNumDesc(String tenantId,Pageable pageable);
	
	public List<MallProduct> findByProductStatusOrderByCoinNumDesc(MallProductStatus productStatus);

}
