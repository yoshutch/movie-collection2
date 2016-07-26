package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public enum Rating {
	G ("G"),
	PG ("PG"),
	PG13 ("PG-13"),
	R ("R"),
	TVY ("TV-Y"),
	TVY7 ("TV-Y7"),
	TVG ("TV-G"),
	TVPG ("TV-PG"),
	TV14 ("TV-14"),
	TVMA ("TV-MA"),
	NA ("N/A"),
	UNRATED ("UNRATED"),
	NOT_RATED ("NOT RATED");

	private String val;

	private Rating(String val){
		this.val = val;
	}

	@JsonValue
	public String toString(){
		return val;
	}
}
