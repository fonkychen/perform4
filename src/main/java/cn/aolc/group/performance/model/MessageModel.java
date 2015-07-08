package cn.aolc.group.performance.model;

import java.io.Serializable;
import java.util.List;

import cn.aolc.group.performance.jpa.UserMessage;
import cn.aolc.group.performance.jpa.UserNotify;

public class MessageModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8989408006745765001L;
	
	private List<UserMessage> messageList;
	
	private List<UserNotify> notifyList;

	public List<UserMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<UserMessage> messageList) {
		this.messageList = messageList;
	}

	public List<UserNotify> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<UserNotify> notifyList) {
		this.notifyList = notifyList;
	}

}
