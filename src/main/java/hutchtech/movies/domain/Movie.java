package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import hutchtech.movies.util.JsonMapper;

import java.util.List;

/**
 * Created by Scott Hutchings on 7/21/2016.
 *
 */
public class Movie implements Comparable<Movie> {
	@JsonProperty("imdbID")
	private String imdbId;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Rated")
	private Rating rating;
	@JsonProperty("Runtime")
	private String runtime;
	@JsonProperty("Poster")
	private String posterUrl;
	@JsonProperty("Genre")
	private List<String> genres;
	@JsonProperty("Mediums")
	private List<Medium> mediums;

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
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

	public String getHttpsPosterUrl(){
		return posterUrl.replace("http://", "https://");
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public List<Medium> getMediums() {
		return mediums;
	}

	public void setMediums(List<Medium> mediums) {
		this.mediums = mediums;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"imdbId='" + imdbId + '\'' +
				", title='" + title + '\'' +
				", rating=" + rating +
				", runtime='" + runtime + '\'' +
				", posterUrl='" + posterUrl + '\'' +
				", genres=" + genres +
				", mediums=" + mediums +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Movie movie = (Movie) o;

		return imdbId.equals(movie.imdbId);

	}

	@Override
	public int hashCode() {
		return imdbId.hashCode();
	}

	@Override
	public int compareTo(Movie m2) {
		return this.title.compareTo(m2.getTitle());
	}

	public String toJson() throws JsonProcessingException {
		return JsonMapper.mapper().writeValueAsString(this);
	}
}
