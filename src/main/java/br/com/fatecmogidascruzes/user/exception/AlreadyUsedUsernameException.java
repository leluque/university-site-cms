package br.com.fatecmogidascruzes.user.exception;

public class AlreadyUsedUsernameException extends Exception {

	private static final long serialVersionUID = 1L;

	public AlreadyUsedUsernameException() {
		super();
	}

	public AlreadyUsedUsernameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlreadyUsedUsernameException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyUsedUsernameException(String message) {
		super(message);
	}

	public AlreadyUsedUsernameException(Throwable cause) {
		super(cause);
	}

}
