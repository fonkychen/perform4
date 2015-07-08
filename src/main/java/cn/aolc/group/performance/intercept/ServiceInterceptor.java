package cn.aolc.group.performance.intercept;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.AchieveAnnotation;
import cn.aolc.group.performance.annotation.MessageAnnotation;
import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.annotation.UserPopularAdded;
import cn.aolc.group.performance.annotation.UserScoreAdded;
import cn.aolc.group.performance.event.UserChangeEvent;
import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.CoinHistory;
import cn.aolc.group.performance.jpa.PopularHistory;
import cn.aolc.group.performance.jpa.ScoreHistory;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.enumeration.AchieveType;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.MessageType;
import cn.aolc.group.performance.jpa.enumeration.PopularType;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.util.OperationDef;

@Component("serviceInterceptor")
public class ServiceInterceptor extends BaseInterceptor implements MethodInterceptor,ApplicationContextAware{
	private final static Logger logger=LoggerFactory.getLogger(ServiceInterceptor.class);
	private ApplicationContext applicationContext;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Method method=invocation.getMethod();
		try{
			Object retval=invocation.proceed();	
			User user=getPrincipal()==null?null:getPrincipal().getUser();		
	        handleUserData(user,method,retval);
			return retval;
		}catch(Throwable tb){
			logger.debug("interceptor exception", tb);
			throw tb;
		}
		
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	@Async
	@Transactional(propagation=Propagation.REQUIRED)
	private void handleOperationLog(User user,MethodInvocation invocation,Method method){
		OperationDef od=method.getAnnotation(OperationDef.class);
        if(od!=null){
			
			Object [] objs=invocation.getArguments();
			String description="";
			for(Object obj:objs){
				description=description+obj+"-";
			}			
		}	
	}
	
	@Async
	@Transactional(propagation=Propagation.REQUIRED)
	private void handleUserData(User user,Method method,Object retval){
		
		UserScoreAdded usc=method.getAnnotation(UserScoreAdded.class);
		UserCoinAdded ucc=method.getAnnotation(UserCoinAdded.class);
		UserPopularAdded upc=method.getAnnotation(UserPopularAdded.class);
		AchieveAnnotation aac=method.getAnnotation(AchieveAnnotation.class);
		MessageAnnotation mac=method.getAnnotation(MessageAnnotation.class);
		UserChangeEvent event=new UserChangeEvent(this, user);
	//	logger.debug("event "+usc+" "+ucc+" "+upc);
		long rid=-1l;
		if(retval instanceof EntityWithId){
			rid=((EntityWithId)retval).getId();
			event.setReference((EntityWithId)retval);
		}
		if(rid >=0 && usc!=null){
			for(ScoreType st:usc.scoreType()){
				ScoreHistory sh=new ScoreHistory();
				sh.setReferenceId(rid);		
				sh.setScoreType(st);
				event.add(sh);
			}					
		}
		
		if(rid >=0 && ucc!=null){
			for(CoinType ct:ucc.coinType()){
				CoinHistory ch=new CoinHistory();			
				ch.setCoinType(ct);
				ch.setReferenceId(rid);
				event.add(ch);
			}			
		}
		
		if(rid >=0 && upc!=null){
			for(PopularType pt:upc.popularType()){
				PopularHistory ph=new PopularHistory();
			    ph.setPopularType(pt);
			    ph.setReferenceId(rid);
			    event.add(ph);
			}		    
		}
		
		if(rid >=0 && aac!=null){
			for(AchieveType at:aac.type()){
				AchieveRecord record=new AchieveRecord();
				record.setAchieveType(at);
				record.setUser(user);
				event.add(record);
			}
		}
		
		if(rid >=0 && mac!=null){
			for(MessageType mt:mac.type()){
				UserMessage um=new UserMessage();
				um.setByUser(user);
				um.setMessageType(mt);
				event.add(um);
			}
		}
		applicationContext.publishEvent(event);		
	}	
}
