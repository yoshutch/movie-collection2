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
	private String collectionId;
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
	@JsonIgnore
	private List<Medium> mediums;

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
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
				", collectionId='" + collectionId + '\'' +
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
}
