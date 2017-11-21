package org.sdas.cloud.core;

public class CloudException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CloudException(String message){
		super(message);
	}

}
