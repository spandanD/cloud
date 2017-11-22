package org.sdas.cloud.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;

import org.sdas.cloud.core.entity.Channel;
import org.sdas.cloud.core.entity.ChannelStreamer;

public class ChannelQueueImpl implements ChannelService {

	private final static String DEFAULT_CHANNEL_NAME = "defaultChannel";
	private final static String REQUEST_ALIAS = "suspendedRequests";
	private final static String EVENT_ALIAS = "messages";
	private Map<String, Queue<AsyncContext>> subscribersByChannelsQueue;
	private Queue<Channel<?>> channels;
	
	public void initialize(ServletContext servletContext) {
		subscribersByChannelsQueue = new ConcurrentHashMap<String, Queue<AsyncContext>>();
		servletContext.setAttribute(REQUEST_ALIAS, subscribersByChannelsQueue);
		channels = new ConcurrentLinkedQueue<Channel<?>>();
		servletContext.setAttribute(EVENT_ALIAS, channels);
	}
	
	public boolean channelHasFreshContent() {
		return !channels.isEmpty();
	}

	public boolean channelHasFreshContent(String channelName) {
		throw new CloudException("Channel search not implemented yet");
	}

	public boolean channelHasSubscribers() {
		for (Queue<AsyncContext> subscribersByChannel : subscribersByChannelsQueue.values()){
			if (subscribersByChannel != null && !subscribersByChannel.isEmpty())
				return true;
		}
		return false;
	}

	public boolean channelHasSubscribers(String channelName) {
		Queue<AsyncContext> subscribers = subscribersByChannelsQueue.get(channelName);
		return (subscribers!= null && !subscribers.isEmpty());
	}

	public Channel<?> getNextChannelContent() {
		return channels.poll(); // Will remove it from the list as well
	}

	public Channel<?> getNextChannelContent(String channelName) {
		throw new CloudException("Channel search not implemented yet");
	}

	public List<ChannelStreamer<?>> getAllChannelContentAndSubscribers() {
		List<ChannelStreamer<?>> channelStreamers = new ArrayList<ChannelStreamer<?>>();
		while (!channels.isEmpty()){
			channelStreamers.addAll(getNextChannelContentAndSubscribers());
		}
		return channelStreamers;
	}

	public List<ChannelStreamer<?>> getAllChannelContentAndSubscribers(String channeName) {
		throw new CloudException("Channel search not implemented yet");
	}
	
	public List<ChannelStreamer<?>> getNextChannelContentAndSubscribers() {
		List<ChannelStreamer<?>> channelStreamers = new ArrayList<ChannelStreamer<?>>();
		if (channels.isEmpty())
			return channelStreamers;
		Channel<?> channel = channels.poll();
		String channelName = channel.getName();
		Queue<AsyncContext> asyncContext = subscribersByChannelsQueue.get(channelName);
		while (!asyncContext.isEmpty()){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ChannelStreamer<?> channelStreamer = new ChannelStreamer(channel, asyncContext.poll());
			channelStreamers.add(channelStreamer);
		}
		return channelStreamers;
	}

	public List<ChannelStreamer<?>> getNextChannelContentAndSubscribers(String channeName) {
		throw new CloudException("Channel search not implemented yet");
	}

	public List<Channel<?>> getAllChannelContent() {
		List<Channel<?>> channelsQueued = new ArrayList<Channel<?>>();
		while (!channels.isEmpty())
			channelsQueued.add(getNextChannelContent());
		return channelsQueued;			
	}

	public List<Channel<?>> getAllChannelContent(String channelName) {
		throw new CloudException("Channel search not implemented yet");
	}

	public void subscribe(AsyncContext asyncContext) {
		subscribe(DEFAULT_CHANNEL_NAME , asyncContext);	
	}

	public void subscribe(String channelName, AsyncContext asyncContext) {
		try{
			if (channelName== null || channelName.isEmpty())
				throw new CloudException("Channel name is empty or null");
			if (asyncContext == null)
				throw new CloudException("Cannot add null asyn context");
			Queue<AsyncContext> asyncContextQueue = subscribersByChannelsQueue.get(channelName);
			if (asyncContextQueue == null)
				asyncContextQueue = new LinkedBlockingQueue<AsyncContext>();
			asyncContextQueue.offer(asyncContext);
			subscribersByChannelsQueue.put(channelName, asyncContextQueue);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	public void publish(Object payload) {
		publish(DEFAULT_CHANNEL_NAME , payload);	
	}

	public void publish(String channelName, Object payload) {
		Channel<?> channel = new Channel<Object>(channelName, payload);
		channels.offer(channel);
	}
	
}
