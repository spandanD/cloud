package org.sdas.cloud.core;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdas.cloud.core.util.RequestReader;

public class RequestHandlerPubSubImpl implements RequestHandler{

	private final ChannelService channelService;
	
	public RequestHandlerPubSubImpl(ChannelService channelService){
		this.channelService = channelService;
	}
	
	public void onRequest(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equalsIgnoreCase("GET")){
			AsyncContext suspendedRequest = request.startAsync(request,response);
			suspendedRequest.setTimeout(CloudConfig.DEFAULT_TIMEOUT_PERIOD);
			channelService.subscribe(suspendedRequest);
		}
		else if (request.getMethod().equalsIgnoreCase("POST")){
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

}
