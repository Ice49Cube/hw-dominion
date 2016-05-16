package dominion.results;

public class HighScoreResult {
	private final String name;
	private final Integer score;

	public HighScoreResult(String name, Integer score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public Integer getScore() {
		return this.score;
	}
}
