package darksky.exceptions;

public class ExceptionDAO extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExceptionDAO() {}
	
	public ExceptionDAO(String msg) {
		super(msg);
	}
	
	public ExceptionDAO(String msg, Throwable cause) {
		super(msg, cause);
	}
}
