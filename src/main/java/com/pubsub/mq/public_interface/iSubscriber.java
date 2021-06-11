package com.pubsub.mq.public_interface;

import com.pubsub.mq.model.Message;

public interface iSubscriber {

	public String getId();
	public void consume(iMessage message) throws InterruptedException;
}
