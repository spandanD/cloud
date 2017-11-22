package org.sdas.cloud.core.entity;

import javax.servlet.AsyncContext;

public class ChannelStreamer<T> {
	private Channel<T> channel;
	private AsyncContext asyncContext;
	private String contentType;
	
	public ChannelStreamer(Channel<T> channel, AsyncContext asyncContext){
		this.channel = channel;
		this.asyncContext = asyncContext;
	}
	
	public AsyncContext getAsyncContext(){
		return this.asyncContext;
	}
	
	public Channel<T> getChannel(){
		return this.channel;
	}
}
