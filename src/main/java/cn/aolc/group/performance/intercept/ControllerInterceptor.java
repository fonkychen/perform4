package cn.aolc.group.performance.intercept;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aolc.group.performance.jpa.UserNotify;
import cn.aolc.group.performance.jpa.enumeration.NotifyType;

@Component("controllerInterceptor")
public class ControllerInterceptor extends BaseInterceptor implements MethodInterceptor{
	
	
	
//	@Autowired
//	private UserNotifyRepository userNotifyRepository;

	public Object invoke(MethodInvocation arg0) throws Throwable {
		
		Method method=arg0.getMethod();
		Class cla=method.getDeclaringClass();
		List<NotifyType> ntlist=new ArrayList<NotifyType>();
		if(cla.getSimpleName().equals("ServiceController")){
			if(method.getName().equals("activitylist")){
				
				ntlist.add(NotifyType.NewActivity);
			}
			else if(method.getName().equals("checkdrum")){
				ntlist.add(NotifyType.NewDrumCheck);
			}
		}
		else if(cla.getSimpleName().equals("WealthController")){
			if(method.getName().equals("shopmall")){
			
				ntlist.add(NotifyType.NewMallProduct);
			}
			else if(method.getName().equals("orderrecord")){
				ntlist.add(NotifyType.NewOrderRecord);
			}
			else if(method.getName().equals("reward")){
				ntlist.add(NotifyType.NewReward);
				ntlist.add(NotifyType.NewRewardResponse);
			}
            else if(method.getName().equals("task")){
				ntlist.add(NotifyType.NewTask);
			}
			else if(method.getName().equals("assigntask")){
				ntlist.add(NotifyType.NewTask);
				ntlist.add(NotifyType.NewTaskResponse);
			}
		}
		else if(cla.getSimpleName().equals("StaffController")){
			if(method.getName().equals("checkvocation")){
				ntlist.add(NotifyType.NewVocationApply);
			}
		}
		
//		if(ntlist.size()>0){
//			for(NotifyType nt:ntlist){
//				List<UserNotify> uns=userNotifyRepository.findByUserAndNotifyType(getPrincipal().getUser(), nt);
//				UserNotify un=null;
//				if(uns.size()>0){
//					un=uns.get(0);
//					un.setCount(0);
//					userNotifyRepository.save(un);
//				}
//			}
//				
//		}
		return arg0.proceed();
	}

}
