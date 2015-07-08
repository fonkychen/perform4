package cn.aolc.group.performance.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;

import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.CoinHistory;
import cn.aolc.group.performance.jpa.PopularHistory;
import cn.aolc.group.performance.jpa.ScoreHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.inter.EntityWithId;


public class UserChangeEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 196057568310257243L;
	
	private User user;
	private EntityWithId reference;
	
	private List<ScoreHistory> scoreHistorys;
	
	private List<CoinHistory> coinHistorys;
	
	private List<PopularHistory> popularHistorys;
	
	private List<AchieveRecord> achieveRecords;
	
	private List<UserMessage> userMessages;
	
	public UserChangeEvent(Object source,User user) {
		super(source);
		this.setUser(user);
		scoreHistorys=new ArrayList<ScoreHistory>();
		coinHistorys=new ArrayList<CoinHistory>();
		popularHistorys=new ArrayList<PopularHistory>();
		achieveRecords=new ArrayList<AchieveRecord>();
		userMessages=new ArrayList<UserMessage>();
	}
	
	public void add(ScoreHistory sh){
		scoreHistorys.add(sh);
	}
	
	public void add(CoinHistory ch){
		coinHistorys.add(ch);
	}
	
	public void add(PopularHistory ph){
		popularHistorys.add(ph);
	}
	
	public void add(AchieveRecord record){
		achieveRecords.add(record);
	}
	
	public void add(UserMessage userMessage){
		userMessages.add(userMessage);
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ScoreHistory> getScoreHistorys() {
		return scoreHistorys;
	}

	public void setScoreHistorys(List<ScoreHistory> scoreHistorys) {
		this.scoreHistorys = scoreHistorys;
	}

	public List<CoinHistory> getCoinHistorys() {
		return coinHistorys;
	}

	public void setCoinHistorys(List<CoinHistory> coinHistorys) {
		this.coinHistorys = coinHistorys;
	}
	
	public List<PopularHistory> getPopularHistorys() {
		return popularHistorys;
	}

	public void setPopularHistorys(List<PopularHistory> popularHistorys) {
		this.popularHistorys = popularHistorys;
	}

	public List<AchieveRecord> getAchieveRecords() {
		return achieveRecords;
	}

	public void setAchieveRecords(List<AchieveRecord> achieveRecords) {
		this.achieveRecords = achieveRecords;
	}

	public EntityWithId getReference() {
		return reference;
	}

	public void setReference(EntityWithId reference) {
		this.reference = reference;
	}

	public List<UserMessage> getUserMessages(){
		return this.userMessages;
	}
}
