package darksky.exceptions;

public class ExceptionService extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ExceptionService() {}
	
	public ExceptionService(String msg) {
		super(msg);
	}
	
	public ExceptionService(String msg, Throwable cause) {
		super(msg, cause);
	}

}
