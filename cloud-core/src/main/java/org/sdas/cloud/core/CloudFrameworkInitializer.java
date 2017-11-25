package org.sdas.cloud.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContext;

import org.sdas.cloud.core.channel.ChannelManager;
import org.sdas.cloud.core.channel.ChannelQueueImpl;
import org.sdas.cloud.core.channel.ChannelService;
import org.sdas.cloud.core.runnable.ChannelMonitorSingleChunkRunnable;

public class CloudFrameworkInitializer implements CloudFramework 
{
	private ExecutorService threadPool;
	private ScheduledExecutorService primaryMonitorThread;
	private ChannelService channelService;
	
	@Override
	public ChannelService initialize(ServletContext servletContext) {
		channelService = new ChannelQueueImpl();
		channelService.initialize(servletContext);
		primaryMonitorThread = Executors.newSingleThreadScheduledExecutor();
		threadPool = Executors.newFixedThreadPool(CloudConfig.DEFAULT_THREAD_POOL_SIZE);
		Runnable channelMonitorRunnable = new ChannelMonitorSingleChunkRunnable(threadPool , (ChannelManager) channelService);
		primaryMonitorThread.scheduleAtFixedRate(channelMonitorRunnable, 0, CloudConfig.DEFAULT_TIME_PERIOD, CloudConfig.DEFAULT_TIME_UNIT);	
		return channelService;
	}

	@Override
	public ChannelService initialize(ServletContext servletContext, CloudConfig config) {
		throw new RuntimeException("Unimplemented");
	}

	@Override
	public void destroy() {
		if (primaryMonitorThread != null)
			primaryMonitorThread.shutdown();
		if (threadPool != null)
			threadPool.shutdown();
	}
    
}
