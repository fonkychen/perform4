package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.CoinHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.BalanceType;
import cn.aolc.group.performance.jpa.enumeration.CoinType;

@Transactional(propagation=Propagation.REQUIRED)
public interface CoinHistoryRepository extends JpaRepository<CoinHistory, Long>{
	
	public List<CoinHistory> findByCoinTypeAndReferenceId(CoinType coinType,Long referenceId);
//	
//	public List<CoinHistory> findByUserAndYearNumAndMonthNumAndDayNumAndCoinTypeAndBalanceTypeAndReferenceId(User user,Integer yearNum,Integer monthNum,Integer dayNum,CoinType coinType,BalanceType balanceType,Long referenceId);
//	
//	public List<CoinHistory> findByYearNumAndMonthNumAndDayNumAndCoinTypeAndBalanceTypeAndReferenceId(Integer yearNum,Integer monthNum,Integer dayNum,CoinType coinType,BalanceType balanceType,Long referenceId);
//	
	@Query("select sum(ch.coinNum) from CoinHistory ch where ch.user=?1 and ch.yearNum=?2 and ch.monthNum=?3")
	public Long findSumScoreByUserAndYearNumAndMonthNum(User user,Integer yearNum,Integer monthNum);
	
	@Query("select sum(ch.coinNum) from CoinHistory ch where ch.user=?1 and ch.yearNum=?2 ")
	public Long findSumScoreByUserAndYearNum(User user,Integer yearNum);
	
	@Query("select sum(ch.coinNum) from CoinHistory ch where ch.user=?1 and ch.yearNum=?2 and ch.monthNum=?3 and ch.dayNum=?4 and ch.balanceType=?5")
	public Long findSumScoreByUserAndYearNumAndMonthNumAndDayNumAndBalanceType(User user,Integer yearNum,Integer monthNum,Integer dayNum,BalanceType balanceType);
	
	public List<CoinHistory> findByUserAndYearNumAndMonthNumOrderByUpdatedDesc(User user,Integer yearNum,Integer monthNum);
	
	public Page<CoinHistory> findByUserAndYearNumOrderByUpdatedDesc(User user,Integer yearNum,Pageable pageable);
	

}
