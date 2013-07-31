package by.xgear.whois.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Artists {
	
	@SerializedName(value = "artist")
	private List<Person> artists;

	public List<Person> getArtists() {
		return artists;
	}

	public void setArtists(List<Person> artists) {
		this.artists = artists;
	}

	@SerializedName(value = "@attr")
	private Attrs attr;
	
	class Attrs{

		private int matches;

		public Attrs() {
			super();
		}

		public Attrs(int matches) {
			super();
			this.matches = matches;
		}

		public int getMatches() {
			return matches;
		}

		public void setMatches(int matches) {
			this.matches = matches;
		}
		
	}
}
