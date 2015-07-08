package cn.aolc.group.performance.event;

import org.springframework.context.ApplicationEvent;

import cn.aolc.group.performance.jpa.enumeration.MessageType;

public class MessageEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7219963452284050456L;
	
	private MessageType messageType;
	
	public MessageEvent(Object source,MessageType messageType) {
		super(source);		
		this.messageType=messageType;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

}
