package org.sdas.cloud.asynchronous.sample.async.ws;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdas.cloud.core.CloudFramework;
import org.sdas.cloud.core.CloudFrameworkInitializer;

@WebServlet(name="asyncServlet" , urlPatterns="/async" , asyncSupported= true)
public class AsyncEventService extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private final CloudFramework cloudFramework = new CloudFrameworkInitializer();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		cloudFramework.addRequest(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		cloudFramework.broadcast(request, response);
	}
	
	@Override
	public void init(ServletConfig sc){
		cloudFramework.initialize(sc.getServletContext());
	}

}
