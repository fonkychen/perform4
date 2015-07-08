package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.PopularHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.PopularType;

@Transactional
public interface PopularHistoryRepository extends JpaRepository<PopularHistory, Long>{
	
	public List<PopularHistory> findByYearNumAndMonthNumAndDayNumAndByUser(Integer yearNum,Integer monthNum,Integer dayNum,User byUser);
	
	public List<PopularHistory> findByYearNumAndMonthNumAndDayNumAndUser(Integer yearNum,Integer monthNum,Integer dayNum,User user);
	
	public List<PopularHistory> findByYearNumAndMonthNumAndDayNum(Integer yearNum,Integer monthNum,Integer dayNum);
	
	public List<PopularHistory> findByYearNum(Integer yearNum);
	
	@Query("select sum(ph.actualPopular) from PopularHistory ph where ph.user=?1 and ph.yearNum=?2")
	public Integer findSumByUserAndYearNum(User user,Integer yearNum);
	
	@Query("select count(ph) from PopularHistory ph where ph.user=?1 and ph.yearNum=?2 and ph.popularType=?3")
	public Integer countByUserAndYearNumAndPopularType(User user,Integer yearNum,PopularType popularType);

	public List<PopularHistory> findByPopularTypeAndReferenceId(PopularType popularType,Long referenceId);
		
}
