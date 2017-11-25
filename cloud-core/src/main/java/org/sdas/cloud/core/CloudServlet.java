package org.sdas.cloud.core;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CloudServlet extends HttpServlet{
	
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
