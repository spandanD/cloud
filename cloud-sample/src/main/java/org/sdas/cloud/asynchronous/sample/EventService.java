package org.sdas.cloud.asynchronous.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/event-service")
public class EventService {

	@GET
	public String respond(){
		return "Hello World";
	}
	
}
