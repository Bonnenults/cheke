

package com.autozi.common.web.session.filter;


public class SessionException extends RuntimeException {
 
	private static final long serialVersionUID = -8371685782979909590L;

	public SessionException() {
        super();
    }

    public SessionException(String message) {
        super(message);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionException(Throwable cause) {
        super(cause);
    }
}
