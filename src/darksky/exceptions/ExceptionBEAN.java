package darksky.exceptions;

public class ExceptionBEAN extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExceptionBEAN() {}
	
	public ExceptionBEAN(String msg) {
		super(msg);
	}
	
	public ExceptionBEAN(String msg, Throwable cause) {
		super(msg, cause);
	}
}
