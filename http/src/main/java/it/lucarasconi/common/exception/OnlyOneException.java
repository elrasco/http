package it.lucarasconi.common.exception;

public class OnlyOneException extends IllegalArgumentException {

	private static final long serialVersionUID = -430872527400632735L;

	public OnlyOneException() {
		super();
	}

	public OnlyOneException(String message, Throwable cause) {
		super(message, cause);
	}

	public OnlyOneException(String s) {
		super(s);
	}

	public OnlyOneException(Throwable cause) {
		super(cause);
	}

}
