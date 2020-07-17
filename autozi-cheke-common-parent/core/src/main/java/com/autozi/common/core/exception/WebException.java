package com.autozi.common.core.exception;

public class WebException extends BaseException {
	private static final long serialVersionUID = 538664744096576290L;

	public WebException() {
		super();
	}

	public WebException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebException(String message) {
		super(message);
	}

	public WebException(Throwable cause) {
		super(cause);
	}

}
