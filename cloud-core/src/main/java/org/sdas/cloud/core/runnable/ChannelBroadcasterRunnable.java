package org.sdas.cloud.core.runnable;

import javax.servlet.AsyncContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdas.cloud.core.entity.Channel;
import org.sdas.cloud.core.entity.ChannelStreamer;

public class ChannelBroadcasterRunnable implements Runnable{
	
	private final static Logger logger = LogManager.getLogger(ChannelBroadcasterRunnable.class);
	private ChannelStreamer<?> channelStreamer;
	
	public ChannelBroadcasterRunnable(ChannelStreamer<?> channelStreamer){
		this.channelStreamer = channelStreamer;
	}
	
	public void run() {
		try{
			AsyncContext asyncContext = channelStreamer.getAsyncContext();
			Channel<?> channel = channelStreamer.getChannel();
			Object content = channel.getContent();
			logger.info("Broadcasting {} using thread: {}" , content, Thread.currentThread().getName());				
			asyncContext.getResponse().getWriter().println(content);
			asyncContext.complete();
			logger.info("Broadcast to suspended connection complete");
		}
		catch(Exception ex){
			ex.printStackTrace();
			//TODO try to broadcast later?
			logger.error("Could not broadcast message to connection due to:" + ex.getMessage());
		}
	}

}
