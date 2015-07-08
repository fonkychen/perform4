package cn.aolc.group.performance.dao.tenant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.tenant.MallOrder;

@Transactional
public interface MallOrderRepository extends JpaRepository<MallOrder, Long>{
	
	public Page<MallOrder> findByOrderUserAndTenantIdOrderByUpdatedDesc(User orderUser,String tenantId,Pageable pageable);
	
	public Page<MallOrder> findByTenantIdOrderByCheckedAsc(String tenantId,Pageable pageable);
	
	//public Page<MallOrder> findByIdNotNull(Pageable pageable,Sort sort);

}
