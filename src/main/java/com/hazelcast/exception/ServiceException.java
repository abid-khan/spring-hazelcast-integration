package com.hazelcast.exception;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	private String message;

	public ServiceException(String message, Throwable exception) {
		super(exception);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
