package org.sdas.cloud.core;

import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdas.cloud.core.annotation.CloudService;
import org.sdas.cloud.core.channel.ChannelService;

public abstract class CloudServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private final CloudFramework cloudFramework = new CloudFrameworkInitializer();
	private ChannelService channelService;
	private RequestHandler requestHandler;
	
	protected abstract Class<?> getClassType();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		requestHandler.onRequest(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		requestHandler.onRequest(request, response);
	}
	
	@Override
	public void init(ServletConfig sc){
		channelService = cloudFramework.initialize(sc.getServletContext());
		initializeRequestHandlerFromAnnotation();		
	}
	
	private void initializeRequestHandlerFromAnnotation(){
		try{
			Class<? extends RequestHandler> requestHandlerClazz = getClassType().getAnnotation(CloudService.class).requestHandler();
			Constructor<?> constructor = requestHandlerClazz.getConstructor(ChannelService.class);
			requestHandler = (RequestHandler) constructor.newInstance(new Object[]{channelService});
		}
		catch(Exception methodEx){
			methodEx.printStackTrace();
		}
	}
}
