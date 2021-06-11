package com.pubsub.mq.utils;

import com.pubsub.mq.model.Topic;
import com.pubsub.mq.model.TopicSubscriber;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TopicProcessor {

	private final Topic topic;
	private final HashMap<String, SubscriberWorker> subsciberWorkers;

	public TopicProcessor(final Topic topic){
		this.topic = topic;
		this.subsciberWorkers = new HashMap<>();
	}

	public void publish(){
		for(TopicSubscriber subscriber: topic.getSubscribers() ){
			startSubscriberWorker(subscriber);
		}
	}

	public void startSubscriberWorker(TopicSubscriber topicSubscriber){
		final String subscriberId = topicSubscriber.getSubscriber().getId();
		if(!subsciberWorkers.containsKey(subscriberId)){
			final SubscriberWorker worker = new SubscriberWorker(topic, topicSubscriber);
			subsciberWorkers.put(subscriberId, worker);
			ExecutorService exec = Executors.newSingleThreadExecutor();
			exec.execute(worker);
		}
		final SubscriberWorker worker = subsciberWorkers.get(subscriberId);
		worker.wakeUpIfRequired();
	}
}
