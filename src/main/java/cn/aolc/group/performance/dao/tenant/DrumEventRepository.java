package cn.aolc.group.performance.dao.tenant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.DrumStatusType;
import cn.aolc.group.performance.jpa.tenant.DrumEvent;

@Transactional
public interface DrumEventRepository extends JpaRepository<DrumEvent, Long>{
	
	public Page<DrumEvent> findByYearNumAndTenantId(Integer yearNum,String tenantId,Pageable pageable);
	
	public Page<DrumEvent> findByStatusTypeNotAndTenantIdOrderByUpdatedDesc(DrumStatusType statusType,String tenantId,Pageable pageable);
	
	public Long countByUserAndYearNum(User user,Integer yearNum);
	
	public Long countByUserAndYearNumAndStatusType(User user,Integer yearNum,DrumStatusType statusType);

}
