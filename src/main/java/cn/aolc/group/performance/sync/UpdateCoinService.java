package cn.aolc.group.performance.sync;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aolc.group.performance.dao.AchieveEventRepository;
import cn.aolc.group.performance.dao.AttendEventRepository;
import cn.aolc.group.performance.dao.CoinHistoryRepository;
import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.LuckyTableRepository;
import cn.aolc.group.performance.dao.PopularHistoryRepository;
import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.UserRewardResponseRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.dao.tenant.DrumEventRepository;
import cn.aolc.group.performance.dao.tenant.MallOrderRepository;
import cn.aolc.group.performance.dao.tenant.UserRewardRepository;
import cn.aolc.group.performance.dao.tenant.UserTaskRepository;
import cn.aolc.group.performance.jpa.AchieveEvent;
import cn.aolc.group.performance.jpa.AttendEvent;
import cn.aolc.group.performance.jpa.CoinHistory;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.LuckyTable;
import cn.aolc.group.performance.jpa.QuarterlyWealthBonus;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserPopularEvent;
import cn.aolc.group.performance.jpa.UserRewardApply;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.UserTaskSliceApply;
import cn.aolc.group.performance.jpa.WeeklyTitleSalary;
import cn.aolc.group.performance.jpa.enumeration.BalanceType;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.UserRewardApplyStatus;
import cn.aolc.group.performance.jpa.enumeration.UserRewardStatus;
import cn.aolc.group.performance.jpa.enumeration.UserTaskSliceApplyStatus;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.DrumEvent;
import cn.aolc.group.performance.jpa.tenant.MallOrder;
import cn.aolc.group.performance.service.rest.BaseRestService;
import cn.aolc.group.performance.util.exception.SyncDataException;

@Component
public class UpdateCoinService {
	
	@Autowired
	private CoinHistoryRepository coinHistoryRepository;
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private UserRewardRepository userRewardRepository;
	
	@Autowired
	private UserTaskRepository userTaskRepository;
	
	@Autowired
	private AttendEventRepository attendEventRepository;
	
	@Autowired
	private AchieveEventRepository achieveEventRepository;
	
	@Autowired
	private LuckyTableRepository luckyTableRepository;
	
	@Autowired
	private MallOrderRepository mallOrderRepository;
	
//	@Autowired
//	private UpdateAchieveRecordService updateAchieveRecordService;
	
	@Autowired
	private PopularHistoryRepository popularHistoryRepository;
	
	@Autowired
	private DrumEventRepository drumEventRepository;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository;
	
	
	@Autowired
	private UserRewardResponseRepository userRewardResponseRepository;
	
	@Autowired
	private BaseRestService baseRestService;
	
	//@Async
	public void updateCoinHistory(CoinType coinType,EntityWithId reference) throws Exception{
		
		List<CoinHistory> chlist=coinHistoryRepository.findByCoinTypeAndReferenceId(coinType, reference.getId());
		CoinHistory ch=null;
		if(chlist.size()>0){
			ch=chlist.get(0);
		}
		Calendar cal=Calendar.getInstance();
		if(ch==null){			
			ch=new CoinHistory();
				
			ch.setYearNum(cal.get(Calendar.YEAR));
			ch.setMonthNum(cal.get(Calendar.MONTH)+1);
			ch.setDayNum(cal.get(Calendar.DATE));			
			ch.setCoinType(coinType);
			//ch.setBalanceType(balanceType);
			ch.setReferenceId(reference.getId());
			ch.setCreated(new Date());
		}
		User user=null;
		Long coinNum=0l;
		BalanceType bt=null;
		if(coinType!=null){
			if(coinType.equals(CoinType.DrumEvent)){
				if(!(reference instanceof DrumEvent)) throw new SyncDataException("invalid reference");
				DrumEvent de=(DrumEvent)reference;
				if(de!=null){
					user=de.getUser();
					coinNum=3l;
					bt=BalanceType.Income;
				}
			}
			
			else if(coinType.equals(CoinType.SelfMark)){
				if(!(reference instanceof UserSelfMark)) throw new SyncDataException("invalid reference");
				UserSelfMark usm=(UserSelfMark)reference;
				if(usm!=null){
					user=usm.getUser();
					boolean isEnabled=baseRestService.isEnabledForUser(usm.getYearNum(),usm.getMonthNum(),usm.getDayNum(), user);
					if(!isEnabled){
						coinNum=0l;
					}
					else coinNum=3l;
					bt=BalanceType.Income;
				}				
			}
			else if(coinType.equals(CoinType.DailyBoard)){
				if(!(reference instanceof DailyBoard)) throw new SyncDataException("invalid reference");
				DailyBoard db=(DailyBoard)reference;
				if(db!=null){
					user=db.getUser();
					Calendar ccal=Calendar.getInstance();
					//ccal.setTime(db.getCreateDate());
					ccal.set(db.getYearNum(), db.getMonthNum()-1, db.getDayNum(), 0, 0, 1);
					boolean isEnabled=baseRestService.isEnabledForUser(db.getYearNum(),db.getMonthNum(),db.getDayNum(), user);
					if(!isEnabled){
						coinNum=0l;
					}
					else if(!(ccal.get(Calendar.YEAR) == ch.getYearNum() && (ccal.get(Calendar.MONTH)+1)== ch.getMonthNum() && ccal.get(Calendar.DATE) ==ch.getDayNum())){
						if(ccal.after(cal)){//not happend
							//scoreNum=0;
							return;
						}
						else{
							coinNum=3l;
						}
						
					}
					else coinNum=3l;
					bt=BalanceType.Income;
				}				
			}
			else if(coinType.equals(CoinType.Attend)){//depend AttendEvent
				if(!(reference instanceof AttendEvent)) throw new SyncDataException("invalid reference");
				AttendEvent ae=(AttendEvent)reference;
				if(ae!=null){
					coinNum=ae.getCoinNum();
					user=ae.getUser();
					bt=BalanceType.Income;
				}
				
			}
			else if(coinType.equals(CoinType.Popular)){//add to by user
				if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
				UserPopularEvent event=(UserPopularEvent)reference;
				if(event!=null){
					coinNum=1l;
					user=event.getByUser();
					bt=BalanceType.Income;
				}				
			}

		
			else if(coinType.equals(CoinType.Achieve)){//depend AchieveEvent 
				if(!(reference instanceof AchieveEvent)) throw new SyncDataException("invalid reference");
				AchieveEvent ae=(AchieveEvent)reference;
				if(ae!=null){
					coinNum=ae.getCoinNum();
					user=ae.getUser();
					bt=BalanceType.Income;
				}
			}
			else if(coinType.equals(CoinType.LuckyTable)){//depend LuckTable
				if(!(reference instanceof LuckyTable)) throw new SyncDataException("invalid reference");
				LuckyTable lt=(LuckyTable)reference;
				if(lt!=null){
					coinNum=lt.getCoinNum();
					user=lt.getUser();
					bt=BalanceType.Income;
				}
			}
			else if(coinType.equals(CoinType.ShopMall)){//depend MallOrder
				if(!(reference instanceof MallOrder)) throw new SyncDataException("invalid reference");
				MallOrder mo=(MallOrder)reference;
				if(mo!=null){
					coinNum=mo.getCoinNum()*-1;
					user=mo.getOrderUser();
					bt=BalanceType.Outcome;
				}
			}
			else if(coinType.equals(CoinType.QuarterlyWealthBonus)){
				if(!(reference instanceof QuarterlyWealthBonus)) throw new SyncDataException("invalid reference");
				QuarterlyWealthBonus wb=(QuarterlyWealthBonus)reference;
				coinNum=wb.getCoinNum();
				user=wb.getUser();
				bt=BalanceType.Income;
			}
			else if(coinType.equals(CoinType.AcceptReward)){
				if(!(reference instanceof UserRewardApply)) throw new SyncDataException("invalid parameter");
				UserRewardApply apply=(UserRewardApply)reference;
				if(apply.getApplyStatus().equals(UserRewardApplyStatus.Accepted)){
					coinNum=apply.getUserReward().getCoinNum();
				}
				else{				
					return;
				}				
				user=apply.getUser();
				bt=BalanceType.Income;
			}
			else if(coinType.equals(CoinType.AcceptRewardOut)){
				if(!(reference instanceof UserRewardApply)) throw new SyncDataException("invalid reference");
				UserRewardApply apply=(UserRewardApply)reference;
				if(apply.getApplyStatus().equals(UserRewardApplyStatus.Accepted)){
					coinNum=apply.getUserReward().getCoinNum()*-1;
				}
				else{
					return;
				}
				user=apply.getUserReward().getByUser();
				bt=BalanceType.Outcome;
			}
			else if(coinType.equals(CoinType.AcceptTask)){
				if(!(reference instanceof UserTaskSliceApply)) throw new SyncDataException("invalid reference");
				UserTaskSliceApply apply=(UserTaskSliceApply)reference;
				if(apply.getApplyStatus().equals(UserTaskSliceApplyStatus.Accept)){
					coinNum=apply.getTaskSlice().getUserTask().getCoinNum();
				}
				else{
					return;
				}
				user=apply.getUser();
				bt=BalanceType.Income;
			}
			
			
			else if(coinType.equals(CoinType.TitleSalary)){
				if(!(reference instanceof WeeklyTitleSalary)) throw new SyncDataException("invalid reference");
				WeeklyTitleSalary wts=(WeeklyTitleSalary)reference;
				coinNum=wts.getSalary();
				user=wts.getUser();
				bt=BalanceType.Income;
			}
		}
		
		if(user==null){
			throw new Exception("invalid user");
		}
		ch.setUser(user);		
		ch.setBalanceType(bt);
		Long gap=coinNum;
		if(ch.getCoinNum()!=null){
			gap=coinNum-ch.getCoinNum();
		}
		ch.setCoinNum(coinNum);
		ch.setUpdated(new Date());
		coinHistoryRepository.save(ch);
		user.setUserCoins((user.getUserCoins()==null?0:user.getUserCoins())+gap);
		userRepository.save(user);
		
	}
	


}
