package org.sdas.cloud.asynchronous.sample.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CloudAsyncListener implements ServletContextListener{

	private ExecutorService executorService;
	private ScheduledExecutorService scheduledExecutorService;
	private Queue<AsyncContext> suspendedRequestQueue;
	private Queue<String> messages;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new Runner(), 0, 100, TimeUnit.MILLISECONDS);
		suspendedRequestQueue = new ConcurrentLinkedQueue<AsyncContext>();
		sce.getServletContext().setAttribute("suspendedRequests", suspendedRequestQueue);
		messages = new ConcurrentLinkedQueue<String>();
		sce.getServletContext().setAttribute("messages", messages);
		executorService = Executors.newFixedThreadPool(10);
	}
	
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try{
			scheduledExecutorService.shutdown();
			executorService.shutdown();
		}
		catch(Exception ex){
			
		}
		
	}
	
	private class Runner implements Runnable{

		@Override
		public void run() {
			System.out.println("Number of suspened connections:" + suspendedRequestQueue.size());
			try {
				String date = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
				while (!messages.isEmpty()){
					String message = messages.poll();
					while (!suspendedRequestQueue.isEmpty()){
						AsyncContext aCtx = suspendedRequestQueue.poll();
						
						try{
							aCtx.getResponse().getWriter().println(message);
							aCtx.complete();
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		
	}

}
