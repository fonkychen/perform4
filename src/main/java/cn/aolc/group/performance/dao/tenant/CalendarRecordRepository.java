package cn.aolc.group.performance.dao.tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.tenant.CalendarRecord;
@Transactional
public interface CalendarRecordRepository extends JpaRepository<CalendarRecord, Long>{
	
	public List<CalendarRecord> findByYearNumAndMonthNumAndDayNumAndTenantId(Integer yearNum,Integer monthNum,Integer dayNum,String tenantId);

}
