package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public class Rating implements Comparable<Rating> {
	public static final Rating G = new Rating("G");
	public static final Rating PG = new Rating ("PG");
	public static final Rating PG13 = new Rating ("PG-13");
	public static final Rating R = new Rating ("R");
	public static final Rating TVY = new Rating ("TV-Y");
	public static final Rating TVY7 = new Rating ("TV-Y7");
	public static final Rating TVG = new Rating ("TV-G");
	public static final Rating TVPG = new Rating ("TV-PG");
	public static final Rating TV14 = new Rating ("TV-14");
	public static final Rating TVMA = new Rating ("TV-MA");
	public static final Rating NA = new Rating ("N/A");
	public static final Rating UNRATED = new Rating ("UNRATED");
	public static final Rating NOT_RATED = new Rating ("NOT RATED");

	private String val;

	public Rating(String val){
		this.val = val;
	}

	@JsonValue
	public String toString(){
		return val;
	}

	public String getVal() {
		return val;
	}

	@Override
	public int compareTo(Rating o) {
		return val.compareTo(o.getVal());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Rating rating = (Rating) o;

		return val.equals(rating.val);

	}

	@Override
	public int hashCode() {
		return val.hashCode();
	}
}
