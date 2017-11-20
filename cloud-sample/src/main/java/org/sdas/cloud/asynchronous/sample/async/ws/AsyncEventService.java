package org.sdas.cloud.asynchronous.sample.async.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Queue;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="asyncServlet" , urlPatterns="/async" , asyncSupported= true)
public class AsyncEventService extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		AsyncContext asyncContext = request.startAsync(request,response);
		asyncContext.setTimeout(100000);
		asyncContext.addListener(new AysncListenerImpl());
		ServletContext servletContext = request.getServletContext();
		@SuppressWarnings("unchecked")
		Queue<AsyncContext> suspendedRequestQueue = (Queue<AsyncContext>) servletContext.getAttribute("suspendedRequests");
		suspendedRequestQueue.offer(asyncContext);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = reader.readLine();
		while (line != null){
			sb.append(line);
			line = reader.readLine();
		}
		ServletContext servletContext = request.getServletContext();
		@SuppressWarnings("unchecked")
		Queue<String> messages = (Queue<String>) servletContext.getAttribute("messages");
		messages.offer(sb.toString());		
	}
	
	private class AysncListenerImpl implements AsyncListener{

		@Override
		public void onComplete(AsyncEvent event) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			event.getSuppliedResponse().getWriter().println("You have been timed out");
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			event.getSuppliedResponse().getWriter().println("Internal Server error");
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
	}

}
