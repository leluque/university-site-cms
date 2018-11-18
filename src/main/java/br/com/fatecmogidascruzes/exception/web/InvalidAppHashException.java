package br.com.fatecmogidascruzes.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not allowed to access this resource.")
public class InvalidAppHashException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}