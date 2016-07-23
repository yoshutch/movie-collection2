package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public enum Rating {
	G ("G"),
	PG ("PG"),
	PG13 ("PG13"),
	R ("R"),
	TVY ("TV7"),
	TVY7 ("TVY7"),
	TVG ("TVG"),
	TVPG ("TVPG"),
	TV14 ("TV14"),
	TVMA ("TVMA"),
	NA ("N/A");

	private String val;

	private Rating(String val){
		this.val = val;
	}

	@JsonValue
	public String getValue(){
		return val;
	}
}
