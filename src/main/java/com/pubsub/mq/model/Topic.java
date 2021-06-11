package com.pubsub.mq.model;

import com.pubsub.mq.public_interface.iMessage;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Topic {

	private final String topicId;
	private final String topicName;
	private final List<iMessage> messages;
	private final List<TopicSubscriber> subscribers;

	public Topic(final String id, final String name){
		this.topicId = id;
		this.topicName = name;
		this.messages = new ArrayList<>();
		this.subscribers = new ArrayList<>();
	}

	public String getTopicId(){
		return this.topicId;
	}

	public String getTopicName(){
		return this.topicName;
	}

	public synchronized void setMessage(final iMessage message){
		this.messages.add(message);
	}

	public void subsrcibe(final TopicSubscriber subscriber){
		this.subscribers.add(subscriber);
	}

	public List<iMessage> getMessages(){
		return Collections.unmodifiableList(messages); // using this conversion so that the list can not be changed publicly
	}

	public List<TopicSubscriber> getSubscribers(){
		return Collections.unmodifiableList(subscribers); // using this conversion so that the list can not be changed publicly
	}

}
