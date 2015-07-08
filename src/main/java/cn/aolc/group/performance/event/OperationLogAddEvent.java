package cn.aolc.group.performance.event;

import org.springframework.context.ApplicationEvent;
import cn.aolc.group.performance.jpa.OperationRecord;


public class OperationLogAddEvent extends ApplicationEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -623180310804543439L;
	private OperationRecord operationRecord;

	public OperationLogAddEvent(Object source,OperationRecord operationRecord) {
		super(source);
		this.setOperationRecord(operationRecord);
	}

	public OperationRecord getOperationRecord() {
		return operationRecord;
	}

	public void setOperationRecord(OperationRecord operationRecord) {
		this.operationRecord = operationRecord;
	}

}
