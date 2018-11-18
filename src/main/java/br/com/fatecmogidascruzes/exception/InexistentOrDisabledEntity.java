package br.com.fatecmogidascruzes.exception;

public class InexistentOrDisabledEntity extends Exception {

	private static final long serialVersionUID = 1L;

	public InexistentOrDisabledEntity() {
		super();
	}

	public InexistentOrDisabledEntity(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InexistentOrDisabledEntity(String message, Throwable cause) {
		super(message, cause);
	}

	public InexistentOrDisabledEntity(String message) {
		super(message);
	}

	public InexistentOrDisabledEntity(Throwable cause) {
		super(cause);
	}

}
