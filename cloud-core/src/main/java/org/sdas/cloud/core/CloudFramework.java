package org.sdas.cloud.core;

import javax.servlet.ServletContext;

public interface CloudFramework {
	
	public ChannelService initialize(ServletContext servletContext);
	public ChannelService initialize(ServletContext servletContext, CloudConfig config);
	public void destroy();
}
