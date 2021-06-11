package com.pubsub.mq.model;

import com.pubsub.mq.public_interface.iSubscriber;

import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber {

	private final AtomicInteger offset;
	private final iSubscriber subscriber;

	public TopicSubscriber(final iSubscriber subscriber){
		this.subscriber = subscriber;
		this.offset = new AtomicInteger(0);
	}

	public iSubscriber getSubscriber(){
		return this.subscriber;
	}

	public AtomicInteger getOffset(){
		return this.offset;
	}
}
