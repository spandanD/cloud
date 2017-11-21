package org.sdas.cloud.core;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;

public class CloudFrameworkInitializer implements CloudFramework 
{
	private final static int DEFAULT_THREAD_POOL_SIZE = 10;
	private final static int DEFAULT_TIME_PERIOD = 100;
	private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
	private ExecutorService threadPool;
	private ScheduledExecutorService primaryMonitorThread;
	private CloudEventQueueManager queueManager;
	
	public void initialize(ServletContext servletContext) {
		queueManager = new CloudEventQueueManager(servletContext);
		primaryMonitorThread = Executors.newSingleThreadScheduledExecutor();
		primaryMonitorThread.scheduleAtFixedRate(new EventMonitorRunnable(), 0, DEFAULT_TIME_PERIOD, DEFAULT_TIME_UNIT);
		threadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
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
	
	private class EventMonitorRunnable implements Runnable{

		public void run() {
			Queue<AsyncContext> suspendedRequests = queueManager.getSuspendedRequestQueue();
			Queue<String> events = queueManager.getEvents();
			System.out.println("no. of suspended connections: " + suspendedRequests.size());
			if (events.peek() != null){
				//TODO: multiple events within one cycle?
				String event = events.poll();
				while (!suspendedRequests.isEmpty()){
					AsyncContext aCtx = suspendedRequests.poll();
					try{
						aCtx.getResponse().getWriter().println(event);
						aCtx.complete();
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}
		
	}
    
}
