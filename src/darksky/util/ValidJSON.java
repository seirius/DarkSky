package darksky.util;

public class ValidJSON {

	private boolean valid = false;
	
	public ValidJSON(boolean valid) {
		this.valid = valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public boolean getValid() {
		return this.valid;
	}
}
