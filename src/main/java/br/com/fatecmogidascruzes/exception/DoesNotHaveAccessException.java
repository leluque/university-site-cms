package br.com.fatecmogidascruzes.exception;

public class DoesNotHaveAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public DoesNotHaveAccessException() {
		super();
	}

	public DoesNotHaveAccessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DoesNotHaveAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DoesNotHaveAccessException(String message) {
		super(message);
	}

	public DoesNotHaveAccessException(Throwable cause) {
		super(cause);
	}

}
