package com.pubsub.mq.model;

import com.pubsub.mq.public_interface.iMessage;

public class Message implements iMessage {

	private final String message;// message is final to make sure once its published, it cannot change value later

	public Message(String msg){
		this.message = msg;
	}

	@Override
	public String getMessage(){
		return this.message;
	}
}
