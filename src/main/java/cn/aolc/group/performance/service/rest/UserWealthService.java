package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.dao.AttendEventRepository;
import cn.aolc.group.performance.dao.CoinHistoryRepository;
import cn.aolc.group.performance.dao.LuckyTableRepository;
import cn.aolc.group.performance.jpa.AttendEvent;
import cn.aolc.group.performance.jpa.CoinHistory;
import cn.aolc.group.performance.jpa.LuckyTable;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.AttendEventType;
import cn.aolc.group.performance.jpa.enumeration.BalanceType;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.util.exception.AlreadyAttendException;
import cn.aolc.group.performance.util.exception.AlreadyLotteryException;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserWealthService extends BaseRestService{
	
	@Autowired
	private AttendEventRepository attendEventRepository;
	
	@Autowired
	private LuckyTableRepository luckyTableRepository;
	
	@Autowired
	private CoinHistoryRepository coinHistoryRepository;
	
	private static final int[] Probility={100,147,100,450,150,50,2,1};
	private static final int TotalProbility=1000;
	private static final long[] CoinNums={-5,-3,0,2,5,10,100,500};
	
	private final static Logger logger=LoggerFactory.getLogger(UserWealthService.class);
	
	@UserCoinAdded(coinType =CoinType.Attend)
	public AttendEvent attend(User user,boolean isfix) throws Exception{
		if(user==null) user=getContextUser();
		Calendar cal=Calendar.getInstance();
		if(getAttend(user)!=null)throw new AlreadyAttendException("already attend");
		AttendEvent ae=new AttendEvent();
		ae.setYearNum(cal.get(Calendar.YEAR));
		ae.setMonthNum(cal.get(Calendar.MONTH)+1);
		ae.setDayNum(cal.get(Calendar.DATE));
		ae.setUser(user);
		ae.setUpdated(new Date());		
		
		//ae.setTenantId(user.getCompany().getCode());
		if(isfix){
			ae.setCoinNum(2l);
			ae.setType(AttendEventType.FIX_COIN);
		}
		else{
			Random rand=new Random();
			ae.setCoinNum(rand.nextInt(16)-5l);
			ae.setType(AttendEventType.RANDOM_COIN);
		}		
		ae=attendEventRepository.save(ae);	
		return ae;
	}
	
	public AttendEvent getAttend(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		Calendar cal=Calendar.getInstance();
		List<AttendEvent> aelist=attendEventRepository.findByYearNumAndMonthNumAndDayNumAndUser(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), user);
		if(aelist.size()>0){
			return aelist.get(0);
		}
		return null;
		
	}
	
	@UserCoinAdded(coinType=CoinType.LuckyTable)
	public LuckyTable lottery(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(getLuckyTable(user)!=null) throw new AlreadyLotteryException("已经抽过奖了");
        Random rand=new Random();		
		int index=rand.nextInt(TotalProbility)+1;
		int sum=0;
		int i=0;
		while((sum = sum +Probility[i])<index){			
			i++;
		}
		Calendar cal=Calendar.getInstance();
		LuckyTable lt=new LuckyTable();
		lt.setCoinNum(CoinNums[i]);
		lt.setDayNum(cal.get(Calendar.DATE));
		lt.setLuckyIndex(index);
		lt.setMonthNum(cal.get(Calendar.MONTH)+1);
		lt.setUpdated(new Date());
		lt.setUser(user);
		lt.setYearNum(cal.get(Calendar.YEAR));		
		lt=luckyTableRepository.save(lt);		
		return lt;
	}
	
	public LuckyTable getLuckyTable(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		Calendar cal=Calendar.getInstance();
		List<LuckyTable> ltlist=luckyTableRepository.findByYearNumAndMonthNumAndDayNumAndUser(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), user);
		if(ltlist.size()>0){
			return ltlist.get(0);
		}
		return null;
	}
	
	public Page<CoinHistory> getCoinHistory(Integer page,Integer size,String order) throws Exception{
		Pageable pageable=new PageRequest(page-1, size, order.equals("asc")?Direction.ASC:Direction.DESC,"updated");
		Calendar cal=Calendar.getInstance();
		return coinHistoryRepository.findByUserAndYearNumOrderByUpdatedDesc(getContextUser(), cal.get(Calendar.YEAR), pageable);
	}
	
	public Long getYearlyCoinSum(User user,Integer yearNum) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(yearNum==null){
			Calendar cal=Calendar.getInstance();
			yearNum=cal.get(Calendar.YEAR);
		}
		return coinHistoryRepository.findSumScoreByUserAndYearNum(user, yearNum);
	}
	
	public Long getDailyCoinSum(User user,Integer yearNum,Integer monthNum,Integer dayNum,BalanceType balanceType) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(yearNum==null || monthNum==null || dayNum==null){
			Calendar cal=Calendar.getInstance();
			yearNum=cal.get(Calendar.YEAR);
			monthNum=cal.get(Calendar.MONTH)+1;
			dayNum=cal.get(Calendar.DATE);
		}
		return coinHistoryRepository.findSumScoreByUserAndYearNumAndMonthNumAndDayNumAndBalanceType(user, yearNum, monthNum, dayNum, balanceType);
	}

}
