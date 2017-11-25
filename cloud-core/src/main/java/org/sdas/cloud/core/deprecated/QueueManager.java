package org.sdas.cloud.core.deprecated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public interface QueueManager {

	public void addRequest(HttpServletRequest request, HttpServletResponse response);
	public void broadcast(HttpServletRequest request, HttpServletResponse response);
}
