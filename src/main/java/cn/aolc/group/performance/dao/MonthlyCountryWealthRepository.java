package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.MonthlyCountryWealth;
import cn.aolc.group.performance.jpa.tenant.Country;

@Transactional
public interface MonthlyCountryWealthRepository extends JpaRepository<MonthlyCountryWealth, Long>{
	
	@Query("select mcw from MonthlyCountryWealth mcw where mcw.yearNum=?1 and mcw.monthNum=?2 and mcw.country in ?3 order by mcw.settled desc")
	public List<MonthlyCountryWealth> findByYearNumAndMonthNumAndCountryInOrderBySettledDesc(Integer yearNum,Integer monthNum,List<Country> countries);
	
	public List<MonthlyCountryWealth> findByYearNumAndMonthNumAndCountry(Integer yearNum,Integer monthNum,Country country);

}
