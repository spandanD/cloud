package org.sdas.cloud.core;

import java.util.List;

import org.sdas.cloud.core.entity.Channel;
import org.sdas.cloud.core.entity.ChannelStreamer;

public interface ChannelManager {

	public boolean channelHasFreshContent();
	public boolean channelHasFreshContent(String channelName);
	public boolean channelHasSubscribers();
	public boolean channelHasSubscribers(String channelName);
	public Channel<?> getNextChannelContent();
	public Channel<?> getNextChannelContent(String channelName);
	public List<Channel<?>> getAllChannelContent();
	public List<Channel<?>> getAllChannelContent(String channelName);
	public List<ChannelStreamer<?>> getNextChannelContentAndSubscribers();
	public List<ChannelStreamer<?>> getNextChannelContentAndSubscribers(String channeName);
	public List<ChannelStreamer<?>> getAllChannelContentAndSubscribers();
	public List<ChannelStreamer<?>> getAllChannelContentAndSubscribers(String channeName);
	
	
}
