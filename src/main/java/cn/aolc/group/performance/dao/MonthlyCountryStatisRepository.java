package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.MonthlyCountryStatis;
import cn.aolc.group.performance.jpa.tenant.Country;

@Transactional
public interface MonthlyCountryStatisRepository extends JpaRepository<MonthlyCountryStatis, Long>{
	
	@Query("select mcs from MonthlyCountryStatis mcs where mcs.yearNum=?1 and mcs.monthNum=?2 and mcs.country in ?3 order by mcs.calScored desc")
	public List<MonthlyCountryStatis> findByYearNumAndMonthNumAndCountryInOrderByCalScoredDesc(Integer yearNum,Integer monthNum,List<Country> countries);
	
	public List<MonthlyCountryStatis> findByCountryAndYearNumAndMonthNum(Country country,Integer yearNum,Integer monthNum);
	
	public List<MonthlyCountryStatis> findByYearNumAndMonthNumOrderByCalScoredDesc(Integer yearNum,Integer monthNum);

}
