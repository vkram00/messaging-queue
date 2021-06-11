package com.pubsub.mq.utils;

import com.pubsub.mq.model.Topic;
import com.pubsub.mq.model.TopicSubscriber;
import com.pubsub.mq.public_interface.iMessage;


public class SubscriberWorker implements Runnable{

	private final Topic topic;
	private final TopicSubscriber topicSubscriber;

	public SubscriberWorker(final Topic topic, final TopicSubscriber topicSubscriber){
		this.topic = topic;
		this.topicSubscriber = topicSubscriber;
	}

	@Override public void run() {
		synchronized (topicSubscriber){
			do {
				int currOffset = topicSubscriber.getOffset().get();
				while (currOffset >= topic.getMessages().size()) {
					try {
						topicSubscriber.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				iMessage message = topic.getMessages().get(currOffset);
				try {
					topicSubscriber.getSubscriber().consume(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				topicSubscriber.getOffset().compareAndSet(currOffset, currOffset+1);
			}while(true);
		}
	}

	public synchronized void wakeUpIfRequired(){
		synchronized (topicSubscriber){
			topicSubscriber.notify();
		}
	}
}
