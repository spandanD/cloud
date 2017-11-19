package org.sdas.cloud.asynchronous.sample.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


import javax.servlet.AsyncContext;
@Path("/event-service")
public class EventService {

	AsyncContext context;
	
	@GET
	public String respond() throws InterruptedException{
		System.out.println(Thread.currentThread().getName());
		Thread.sleep(5000);
		System.out.println(Thread.currentThread().getName());
		return "Hello World";
	}
	
	
	
}
