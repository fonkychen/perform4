package cn.aolc.group.performance.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.aolc.group.performance.event.OperationLogAddEvent;

@Component("operationLogAddListener")
public class OperationLogAddListener implements ApplicationListener<OperationLogAddEvent>{

	public void onApplicationEvent(OperationLogAddEvent event) {
		event.getOperationRecord();
	}

}
