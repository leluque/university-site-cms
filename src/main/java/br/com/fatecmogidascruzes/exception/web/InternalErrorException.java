package br.com.fatecmogidascruzes.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "An unexpected error has happened.")
public class InternalErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalErrorException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InternalErrorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InternalErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InternalErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InternalErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}