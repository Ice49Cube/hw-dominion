package dominion.results;

import dominion.routing.*;

public class TestServerResult extends ResultBase {
	
	public TestServerResult() {
		super("testServer");
	}

	public TestServerResult(boolean success, int code, String method) {
		super(success, code, method);
	}
}
