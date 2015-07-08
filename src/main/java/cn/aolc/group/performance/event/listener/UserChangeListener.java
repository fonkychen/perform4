package cn.aolc.group.performance.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.aolc.group.performance.event.UserChangeEvent;
import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.CoinHistory;
import cn.aolc.group.performance.jpa.PopularHistory;
import cn.aolc.group.performance.jpa.ScoreHistory;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.sync.UpdateAchieveRecordService;
import cn.aolc.group.performance.sync.UpdateCoinService;
import cn.aolc.group.performance.sync.UpdateMessageService;
import cn.aolc.group.performance.sync.UpdatePopularService;
import cn.aolc.group.performance.sync.UpdateScoreService;

@Component("userChangeListener")
public class UserChangeListener implements ApplicationListener<UserChangeEvent>{
	
	private final static Logger logger=LoggerFactory.getLogger(UserChangeListener.class);
	
	@Autowired
	private UpdateScoreService updateScoreService;
	
	@Autowired
	private UpdateCoinService updateCoinService;
	
	@Autowired
	private UpdatePopularService updatePopularService;
	    
	@Autowired
	private UpdateAchieveRecordService updateAchieveRecordService;
	
	@Autowired
	private UpdateMessageService updateMessageService;
	
	public void onApplicationEvent(UserChangeEvent event) {
		//sync user data
		//logger.debug("onApplicationEvent",event);
		for(ScoreHistory sh:event.getScoreHistorys()){
			logger.debug("onApplicationEvent update score history");
			try{
				updateScoreService.updateScoreHistory(sh.getScoreType(),event.getReference());				
			}catch(Exception e){
				logger.debug("update score service exception",e);
			}
		}
		
		for(CoinHistory ch:event.getCoinHistorys()){
			try{
				updateCoinService.updateCoinHistory(ch.getCoinType(),event.getReference());
			}catch(Exception e){
				logger.debug("update coin service exception", e);
			}
		}
		
		for(PopularHistory ph:event.getPopularHistorys()){
			try{
				updatePopularService.updatePopular(ph.getPopularType(),event.getReference());
			}catch(Exception e){
				logger.debug("update popular service exception", e);
			}
		}
		
		for(AchieveRecord record:event.getAchieveRecords()){
			try{
				updateAchieveRecordService.updateAchieveRecord(record.getAchieveType(), event.getReference());
			}catch(Exception e){
				logger.debug("update popular service exception", e);
			}
		}
		
		for(UserMessage um:event.getUserMessages()){
			try{
				updateMessageService.updateUserMessage(um.getMessageType(), event.getReference());
			}catch(Exception e){
				logger.debug("update user message service exception",e);
			}
		}
	}
	
	

}
