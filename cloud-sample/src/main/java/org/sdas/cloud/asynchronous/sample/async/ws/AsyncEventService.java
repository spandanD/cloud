package org.sdas.cloud.asynchronous.sample.async.ws;

import javax.servlet.annotation.WebServlet;

import org.sdas.cloud.core.CloudServlet;
import org.sdas.cloud.core.annotation.CloudService;

@CloudService
@WebServlet(name="asyncServlet" , urlPatterns="/async" , asyncSupported= true)
public class AsyncEventService extends CloudServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected Class<?> getClassType() {
		return this.getClass();
	}

}
