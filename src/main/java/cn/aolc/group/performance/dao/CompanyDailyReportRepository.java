package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.CompanyDailyReport;

@Transactional(propagation=Propagation.REQUIRED)
public interface CompanyDailyReportRepository extends JpaRepository<CompanyDailyReport, Long>{
	
	public List<CompanyDailyReport> findByYearNumAndMonthNumAndDayNumAndCompany(Integer yearNum,Integer monthNum,Integer dayNum,Company company);
	

}
