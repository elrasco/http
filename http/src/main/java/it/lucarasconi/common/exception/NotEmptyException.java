package it.lucarasconi.common.exception;

public class NotEmptyException extends IllegalArgumentException {

	private static final long serialVersionUID = 4991118060306863947L;

	public NotEmptyException() {
	}

	public NotEmptyException(String s) {
		super(s);
	}

	public NotEmptyException(Throwable cause) {
		super(cause);
	}

	public NotEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

}
