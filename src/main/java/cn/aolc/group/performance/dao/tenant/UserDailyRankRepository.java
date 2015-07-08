package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.UserDailyRank;

@Transactional(propagation=Propagation.REQUIRED)
public interface UserDailyRankRepository extends JpaRepository<UserDailyRank, Long>{
	
	public List<UserDailyRank> findByTenantIdAndYearNumAndMonthNumAndDayNumOrderByScoreRankDesc(String tenantId,Integer yearNum,Integer monthNum,Integer dayNum);
	
	public List<UserDailyRank> findByTenantIdAndYearNumAndMonthNumAndDayNumOrderByCoinRankDesc(String tenantId,Integer yearNum,Integer monthNum,Integer dayNum);
	
	public List<UserDailyRank> findByTenantIdAndYearNumAndMonthNumAndDayNumOrderByPopularRankDesc(String tenantId,Integer yearNum,Integer monthNum,Integer dayNum);

}
