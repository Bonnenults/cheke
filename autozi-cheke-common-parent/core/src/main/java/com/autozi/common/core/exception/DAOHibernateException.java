package com.autozi.common.core.exception;


public class DAOHibernateException extends DAOException {
	private static final long serialVersionUID = 1L;

	public DAOHibernateException() {
		super();
	}

	public DAOHibernateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOHibernateException(String message) {
		super(message);
	}

	public DAOHibernateException(Throwable cause) {
		super(cause);
	}
	
}
