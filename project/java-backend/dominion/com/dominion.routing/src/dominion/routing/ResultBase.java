package dominion.routing;

//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The ResultBase class should be inherited by all the result objects returned
 * by the methods on target.
 */
public abstract class ResultBase {
	@JsonIgnore()
	private final int code;
	private boolean success;
	private String method;

	/**
	 * Constructs a ResultBase object with success set to true and code 200.
	 * 
	 * @param method
	 *            The name of the method to call.
	 */
	public ResultBase(String method) {
		this(true, 200, method);
	}

	/**
	 * Constructs a ResultBase object with success set to false and method set
	 * to null.
	 * 
	 * @param success
	 *            Indicates a successful call.
	 * @param code
	 *            The HTTP response code. Which isn't serialized to the client
	 *            but used as status code of the HTTP response it's status line.
	 */
	public ResultBase(boolean success, int code) {
		this(success, code, null);
	}

	public ResultBase(boolean success, int code, String method) {
		this.success = success;
		this.code = code;
		this.method = method;
	}

	public int getCode() {
		return this.code;
	}

	public String getMethod() {
		return this.method;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public void setMethod(String value) {
		this.method = value;
	}

	public void setSuccess(boolean value) {
		this.success = value;
	}

}
