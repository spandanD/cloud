package org.sdas.cloud.core;

import javax.servlet.ServletContext;

public interface CloudFramework extends QueueManager {
	
	public void initialize(ServletContext servletContext);
	public void initialize(ServletContext servletContext, CloudConfig config);
	public void destroy();
}
