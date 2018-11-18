package br.com.fatecmogidascruzes.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not allowed to access this resource. Have you signed in?")
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}