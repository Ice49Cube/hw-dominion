package dominion.commands;

import dominion.routing.ResultBase;

public class PlayActionResult extends ResultBase {

	public PlayActionResult(String method) {
		super(method);
	}
	
	public PlayActionResult() {
		super("playAction");
	}
	
}
