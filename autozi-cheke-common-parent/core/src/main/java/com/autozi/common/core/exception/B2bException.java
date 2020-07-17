package com.autozi.common.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: ccli
 * Date: 12-4-17
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
public class B2bException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7271787658878356240L;

	public B2bException() {
        super();
    }

    public B2bException(String message) {
        super(message);
    }

    public B2bException(String message, Throwable cause) {
        super(message, cause);
    }

    public B2bException(Throwable cause) {
        super(cause);
    }
}
