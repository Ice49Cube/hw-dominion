package dominion.results;

import dominion.routing.*;

public class ViewHighScoresResult extends ResultBase {
	private HighScoreInfo[] scores;

	public ViewHighScoresResult() {
		super("viewHighScores");
	}

	public HighScoreInfo[] getScores() {
		return this.scores;
	}

	public void setScores(HighScoreInfo[] value) {
		this.scores = value;
	}
}