package org.sdas.cloud.core;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdas.cloud.core.entity.Channel;
import org.sdas.cloud.core.entity.ChannelStreamer;
import org.sdas.cloud.core.runnable.ChannelBroadcasterRunnable;
import org.sdas.cloud.core.runnable.ChannelMonitorSingleChunkRunnable;
import org.sdas.cloud.core.util.RequestReader;


public class CloudFrameworkInitializer implements CloudFramework 
{
	private final static int DEFAULT_THREAD_POOL_SIZE = 10;
	private final static int DEFAULT_TIME_PERIOD = 100;
	private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
	private final static int DEFAULT_TIMEOUT_PERIOD = 100000;
	private ExecutorService threadPool;
	private ScheduledExecutorService primaryMonitorThread;
	//private CloudEventQueueManager queueManager;
	private ChannelService channelService = new ChannelQueueImpl();
	
	public void initialize(ServletContext servletContext) {
		//queueManager = new CloudEventQueueManager(servletContext);
		channelService.initialize(servletContext);
		primaryMonitorThread = Executors.newSingleThreadScheduledExecutor();
		threadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
		Runnable channelMonitorRunnable = new ChannelMonitorSingleChunkRunnable(threadPool , (ChannelManager) channelService);
		primaryMonitorThread.scheduleAtFixedRate(channelMonitorRunnable, 0, DEFAULT_TIME_PERIOD, DEFAULT_TIME_UNIT);		
	}

	public void initialize(ServletContext servletContext, CloudConfig config) {
		throw new RuntimeException("Unimplemented");
	}

	public void destroy() {
		if (primaryMonitorThread != null)
			primaryMonitorThread.shutdown();
		if (threadPool != null)
			threadPool.shutdown();
	}

	public void addRequest(HttpServletRequest request, HttpServletResponse response) {
		AsyncContext suspendedRequest = request.startAsync(request,response);
		suspendedRequest.setTimeout(DEFAULT_TIMEOUT_PERIOD);
		//asyncContext.addListener(new AysncListenerImpl());
		channelService.subscribe(suspendedRequest);
	}

	public void broadcast(HttpServletRequest request, HttpServletResponse response) {
		try{
			RequestReader reader = new RequestReader();
			String content = reader.getRequestContent(request);
			channelService.publish(content);
		}
		catch(Exception ex){
			throw new CloudException("Could not broadcast due to:" + ex.getMessage());
		}
			
	}
    
}
