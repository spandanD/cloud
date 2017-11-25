package org.sdas.cloud.core.runnable;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdas.cloud.core.channel.ChannelManager;
import org.sdas.cloud.core.entity.ChannelStreamer;

public class ChannelMonitorSingleChunkRunnable implements Runnable{

	private final ChannelManager channelManager;
	private final ExecutorService workerThreadPool;
	private final static Logger logger = LogManager.getLogger(ChannelMonitorSingleChunkRunnable.class);
	
	public ChannelMonitorSingleChunkRunnable(ExecutorService threadPool, ChannelManager channelManager){
		this.workerThreadPool = threadPool;
		this.channelManager = channelManager;
	}
	
	public void run() {
		try{
			if (!channelManager.channelHasFreshContent()){
				logger.trace("No channel has fresh content");
				return;
			}
			if (!channelManager.channelHasSubscribers()){
				logger.trace("No channel has new active subscribers");
				return;
			}
			logger.info("New content received, distribting response completion job to worker threads");
			List<ChannelStreamer<?>> channelStreamers = channelManager.getNextChannelContentAndSubscribers();
			for (ChannelStreamer<?> channelStreamer : channelStreamers)
				workerThreadPool.submit(new ChannelBroadcasterRunnable(channelStreamer));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	

}
