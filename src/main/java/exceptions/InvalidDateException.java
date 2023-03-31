package exceptions;

public class InvalidDateException extends IllegalArgumentException {

	public InvalidDateException() {
		super();
	}
	
	public InvalidDateException(String msg) {
		super(msg);
	}
}
