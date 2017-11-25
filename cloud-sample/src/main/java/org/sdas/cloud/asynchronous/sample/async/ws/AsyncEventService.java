package org.sdas.cloud.asynchronous.sample.async.ws;

import javax.servlet.annotation.WebServlet;

import org.sdas.cloud.core.CloudServlet;

@WebServlet(name="asyncServlet" , urlPatterns="/async" , asyncSupported= true)
public class AsyncEventService extends CloudServlet{

	private static final long serialVersionUID = 1L;

	

}
