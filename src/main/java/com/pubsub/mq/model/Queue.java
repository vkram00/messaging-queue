package com.pubsub.mq.model;

import com.pubsub.mq.public_interface.iMessage;
import com.pubsub.mq.public_interface.iSubscriber;
import com.pubsub.mq.utils.TopicProcessor;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Queue {

	private final HashMap<String, TopicProcessor> topicProcessors;
	private final ExecutorService executor;

	public Queue(){

		this.topicProcessors = new HashMap<>();
		this.executor = Executors.newCachedThreadPool();
	}

	public Topic createTopic(final String topicName) {
		final Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
		TopicProcessor topicProcessor = new TopicProcessor(topic);
		topicProcessors.put(topic.getTopicId(), topicProcessor);
		System.out.println("Created Topic - " + topicName);
		return topic;
	}

	public void subscribe(final Topic topic, final iSubscriber subscriber){
		topic.subsrcibe(new TopicSubscriber(subscriber));
		System.out.println(subscriber.getId() + " subscribed to " + topic.getTopicName());
	}

	public void publish(final Topic topic, final iMessage message){
		topic.setMessage(message);
		System.out.println(message.getMessage() + " added to topic " +  topic.getTopicName());
		executor.execute(() -> topicProcessors.get(topic.getTopicId()).publish());
	}

	public void resetOffset(final Topic topic, final iSubscriber subscriber, final int newOffset){
		for(TopicSubscriber topicSubscriber: topic.getSubscribers()){
			if(topicSubscriber.getSubscriber().equals(subscriber)){
				topicSubscriber.getOffset().set(newOffset);
				System.out.println(topicSubscriber.getSubscriber().getId() + " offset is reset to " + newOffset);
				executor.execute(() -> topicProcessors.get(topic.getTopicId()).startSubscriberWorker(topicSubscriber));
				break;
			}
		}
	}
}
