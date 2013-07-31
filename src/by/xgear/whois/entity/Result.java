package by.xgear.whois.entity;

public class Result {
	
	private String score;
	
	private Artists artists;
	
	public Result() {
		super();
	}

	public Result(String score, Artists artists) {
		super();
		this.score = score;
		this.artists = artists;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Artists getArtists() {
		return artists;
	}

	public void setArtists(Artists artists) {
		this.artists = artists;
	}

}
