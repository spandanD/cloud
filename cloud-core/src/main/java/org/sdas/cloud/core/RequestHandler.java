package org.sdas.cloud.core;

import javax.servlet.AsyncEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandler {

	public void onRequest(HttpServletRequest request, HttpServletResponse response);
}
