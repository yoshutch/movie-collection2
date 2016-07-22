package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public class Movie {
	@JsonProperty("imdbID")
	private String imdbId;
	@JsonIgnore
	private String userIdl;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Rated")
	private String rating;
	@JsonProperty("Runtime")
	private String runtime;
	@JsonProperty("Poster")
	private String posterUrl;
	@JsonProperty("Genre")
	private List<String> genres;

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getUserIdl() {
		return userIdl;
	}

	public void setUserIdl(String userIdl) {
		this.userIdl = userIdl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"imdbId='" + imdbId + '\'' +
				", userIdl='" + userIdl + '\'' +
				", title='" + title + '\'' +
				", rating=" + rating +
				", runtime='" + runtime + '\'' +
				", posterUrl='" + posterUrl + '\'' +
				", genres=" + genres +
				'}';
	}
}
