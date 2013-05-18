package it.lucarasconi.common.exception;

public class DBInitException extends Exception {

	private static final long serialVersionUID = 4432198822974500392L;

	public DBInitException() {
		super();
	}

	public DBInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBInitException(String message) {
		super(message);
	}

	public DBInitException(Throwable cause) {
		super(cause);
	}

}
