package cn.aolc.group.performance.sync;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aolc.group.performance.dao.AchieveEventRepository;
import cn.aolc.group.performance.dao.AchieveMilestoneRepository;
import cn.aolc.group.performance.dao.AchieveRecordRepository;
import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.MeritAcceptRepository;
import cn.aolc.group.performance.dao.UserMessageRepository;
import cn.aolc.group.performance.dao.UserPopularEventRepository;
import cn.aolc.group.performance.dao.UserSelfMarkRepository;
import cn.aolc.group.performance.dao.tenant.DrumEventRepository;
import cn.aolc.group.performance.jpa.AchieveEvent;
import cn.aolc.group.performance.jpa.AchieveMilestone;
import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.DailyBoard;
import cn.aolc.group.performance.jpa.MeritAcceptRecord;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.UserPopularEvent;
import cn.aolc.group.performance.jpa.UserSelfMark;
import cn.aolc.group.performance.jpa.enumeration.AchieveType;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.MessageType;
import cn.aolc.group.performance.jpa.enumeration.UserPopularAction;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.DrumEvent;
import cn.aolc.group.performance.util.exception.SyncDataException;

@Component
public class UpdateAchieveRecordService {
	
	@Autowired
	private AchieveRecordRepository achieveRecordRepository;
	
	@Autowired
	private AchieveMilestoneRepository achieveMilestoneRepository;
	
	@Autowired
	private AchieveEventRepository achieveEventRepository;
	
	@Autowired
	private UserMessageRepository userMessageRepository;
	
	@Autowired
	private UpdateCoinService updateCoinService;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private UserSelfMarkRepository userSelfMarkRepository;
	
	@Autowired
	private DrumEventRepository drumEventRepository;
	
	@Autowired
	private UserPopularEventRepository userPopularEventRepository;
	
	@Autowired
	private MeritAcceptRepository meritAcceptRepository;
	
	private void updateAchieveRecord(User user,AchieveType achieveType,Integer statis) throws Exception{
		AchieveRecord record=null;
		Calendar cal=Calendar.getInstance();
		Integer yearNum=cal.get(Calendar.YEAR);
		List<AchieveRecord> ats=achieveRecordRepository.findByAchieveTypeAndUserAndYearNum(achieveType, user,yearNum);
		if(ats.size()>0){
			record=ats.get(0);
		}
		
		if(record==null){
			record=new AchieveRecord();
			record.setAchieveType(achieveType);
			record.setStatis(0);
			record.setUser(user);
			record.setYearNum(yearNum);
		}
		record.setStatis(statis);
		record.setUpdated(new Date());
		achieveRecordRepository.save(record);
		
		List<AchieveMilestone> ams=achieveMilestoneRepository.findByAchieveTypeOrderByRankDesc(achieveType);
		AchieveMilestone am=null;
		for(AchieveMilestone cam:ams){
			if(cam.getNum()<=record.getStatis()){
				am=cam;
				break;
			}
		}
		
		if(am!=null){
			List<AchieveEvent> aes=achieveEventRepository.findByUserAndAchieveMilestoneAndYearNum(user, am, yearNum);
			if(aes.size()>0)return;
			
			AchieveEvent ae=new AchieveEvent();
			ae.setAchieveMilestone(am);
			ae.setCoinNum(am.getCoinNum());
			ae.setUser(user);
			ae.setUpdated(new Date());
			ae.setYearNum(yearNum);
			ae=achieveEventRepository.save(ae);
			//Calendar cal=Calendar.getInstance();
			
			updateCoinService.updateCoinHistory(CoinType.Achieve, ae);
			
//			UserMessage um=new UserMessage();
//			um.setByUser(null);
//			um.setContent(am.getName()+"|"+am.getDescription()+"|+"+am.getCoinNum()+"UB");
//			um.setIsRead(false);
//			um.setMessageType(MessageType.AchieveEventType);
//			um.setReferenceId(ae.getId());
//			um.setUpdated(new Date());
//			um.setUser(user);
//			userMessageRepository.save(um);
		}
	}
	
	public void updateAchieveRecord(AchieveType at,EntityWithId reference) throws Exception{
		if(at==null || reference==null){
			throw new Exception("invalid parameter");
		}
		Calendar cal=Calendar.getInstance();
		int yearm=cal.get(Calendar.YEAR);
		User user=null;
		Long statis=null;
		if(at.equals(AchieveType.DailyBoard)){
			if(!(reference instanceof DailyBoard)) throw new SyncDataException("invalid reference");
			DailyBoard db=(DailyBoard)reference;
			user=db.getUser();
			statis=dailyBoardRepository.countByUserAndYearNum(user, yearm);
			
		}
		else if(at.equals(AchieveType.DrumEvent)){
			if(!(reference instanceof DrumEvent)) throw new SyncDataException("invalid reference");
			DrumEvent de=(DrumEvent)reference;
			user=de.getUser();
			statis=drumEventRepository.countByUserAndYearNum(user, yearm);
		}
		else if(at.equals(AchieveType.MeritAccept)){
			if(!(reference instanceof MeritAcceptRecord)) throw new SyncDataException("invalid reference");
			MeritAcceptRecord de=(MeritAcceptRecord)reference;
			user=de.getUser();
			statis=meritAcceptRepository.countByUserAndYearNum(user, yearm);
		}
		else if(at.equals(AchieveType.PopularReceiveEggHero)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getUser();
			statis=userPopularEventRepository.countByUserAndYearNumAndPopularAction(user, yearm, UserPopularAction.ThrowEgg);
		}
		else if(at.equals(AchieveType.PopularReceiveFlowerHero)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getUser();
			statis=userPopularEventRepository.countByUserAndYearNumAndPopularAction(user, yearm, UserPopularAction.SendFlower);
		}
		else if(at.equals(AchieveType.PopularSendEggHero)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getByUser();
			statis=userPopularEventRepository.countByUserAndYearNumAndPopularAction(user, yearm, UserPopularAction.ThrowEgg);
		}
		else if(at.equals(AchieveType.PopularSendFlowerHero)){
			if(!(reference instanceof UserPopularEvent)) throw new SyncDataException("invalid reference");
			UserPopularEvent event=(UserPopularEvent)reference;
			user=event.getByUser();
			statis=userPopularEventRepository.countByUserAndYearNumAndPopularAction(user, yearm, UserPopularAction.SendFlower);
		}
		else if(at.equals(AchieveType.SelfMark)){
			if(!(reference instanceof UserSelfMark)) throw new SyncDataException("invalid reference");
			UserSelfMark usm=(UserSelfMark)reference;
			user=usm.getUser();
			statis=userSelfMarkRepository.countByUserAndYearNum(user, yearm);
		}
		if(user!=null && statis!=null){
			updateAchieveRecord(user, at,statis.intValue());
		}
		
	}

}
