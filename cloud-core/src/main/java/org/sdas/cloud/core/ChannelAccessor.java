package org.sdas.cloud.core;

import javax.servlet.AsyncContext;

public interface ChannelAccessor {

	public void subscribe(AsyncContext asyncContext);
	public void subscribe(String channelName , AsyncContext asyncContext);
	public void publish(Object payload);
	public void publish(String channelName, Object payload);
}
