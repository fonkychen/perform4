package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.CountryDailyReport;
import cn.aolc.group.performance.jpa.tenant.Country;

@Transactional(propagation=Propagation.REQUIRED)
public interface CountryDailyReportRepository extends JpaRepository<CountryDailyReport, Long>{
	
	public List<CountryDailyReport> findByYearNumAndMonthNumAndDayNumAndCountry(Integer yearNum,Integer monthNum,Integer dayNum,Country country);
	
	@Query("select cdr from CountryDailyReport cdr where cdr.yearNum=?1 and cdr.monthNum=?2 and cdr.dayNum=?3 and cdr.country in ?4")
	public List<CountryDailyReport> findByYearNumAndMonthNumAndDayNumAndCountryIn(Integer yearNum,Integer monthNum,Integer dayNum,List<Country> countries);

}
