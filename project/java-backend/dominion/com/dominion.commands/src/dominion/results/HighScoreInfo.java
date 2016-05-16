package dominion.results;

public class HighScoreInfo {
	private final String name;
	private final Integer score;

	public HighScoreInfo(String name, Integer score) {
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
