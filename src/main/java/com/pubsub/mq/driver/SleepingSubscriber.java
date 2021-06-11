package com.pubsub.mq.driver;

import com.pubsub.mq.public_interface.iMessage;
import com.pubsub.mq.public_interface.iSubscriber;

public class SleepingSubscriber implements iSubscriber {
	private final String id;
	private final int sleepTimeInMillis;

	public SleepingSubscriber(String id, int sleepTimeInMillis) {
		this.id = id;
		this.sleepTimeInMillis = sleepTimeInMillis;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void consume(iMessage message) throws InterruptedException {
		System.out.println("Subscriber: " + id + " started consuming: " + message.getMessage());
		Thread.sleep(sleepTimeInMillis);
		System.out.println("Subscriber: " + id + " done consuming: " + message.getMessage());
	}
}