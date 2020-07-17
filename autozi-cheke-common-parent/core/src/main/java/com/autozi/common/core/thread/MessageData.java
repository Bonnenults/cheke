package com.autozi.common.core.thread;

import java.io.Serializable;

/**
 * 消息体
 * @author haocheng
 *
 * @param <T>
 */
public class MessageData<T> implements Serializable{

	private static final long serialVersionUID = -1805305828201690527L;
	
	private Object source;
	private Object target;
	
	private T object;
	private int messageType=0;
	
	public MessageData(T t,int messageType){
		this.object=t;
		this.messageType=messageType;
	}
	
	public MessageData(T t,int messageType,Object source,Object target){
		this.object=t;
		this.messageType=messageType;
		this.source=source;
		this.target=target;
	}

	public T getObject() {
		return object;
	}

	public int getMessageType() {
		return messageType;
	}
	
	public Object getSource(){
		return source;
	}

	public Object getTarget() {
		return target;
	}
}
