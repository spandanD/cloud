package org.sdas.cloud.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;

public class CloudEventQueueManager {

	private final static String REQUEST_ALIAS = "suspendedRequests";
	private final static String EVENT_ALIAS = "messages";
	private Queue<AsyncContext> suspendedRequestQueue;
	private Queue<String> events;

	public CloudEventQueueManager(ServletContext servletContext){
		suspendedRequestQueue = new ConcurrentLinkedQueue<AsyncContext>();
		servletContext.setAttribute(REQUEST_ALIAS, suspendedRequestQueue);
		events = new ConcurrentLinkedQueue<String>();
		servletContext.setAttribute(EVENT_ALIAS, events);
	}
	
	public Queue<AsyncContext> getSuspendedRequestQueue() {
		return suspendedRequestQueue;
	}


	public boolean offer(AsyncContext suspendedRequest) {
		return suspendedRequestQueue.offer(suspendedRequest);
	}
	
	public boolean queueEvent(String message){
		return events.offer(message);
	}


	public Queue<String> getEvents() {
		return events;
	}


	public void setEvents(Queue<String> events) {
		this.events = events;
	}

	
}
