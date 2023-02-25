package main.exceptions;

@SuppressWarnings("serial")
public class InvalidDateException extends IllegalArgumentException {

	public InvalidDateException() {
		super();
	}
	
	public InvalidDateException(String msg) {
		super(msg);
	}
}
