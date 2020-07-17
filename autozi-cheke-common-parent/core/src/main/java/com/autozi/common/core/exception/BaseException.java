package com.autozi.common.core.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 8818856558760718016L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);

	}

	public BaseException(String message) {
		super(message);

	}

	public BaseException(Throwable cause) {
		super(cause);

	}
}
