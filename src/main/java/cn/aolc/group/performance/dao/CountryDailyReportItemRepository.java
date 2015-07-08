package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.CountryDailyReportItem;

@Transactional(propagation=Propagation.REQUIRED)
public interface CountryDailyReportItemRepository extends JpaRepository<CountryDailyReportItem, Long>{

}
