package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

/**
 * Created by yoshutch on 7/24/16.
 */
public class Collection {
	private String id;
	private String name;
	private List<Movie> movies;
	private List<String> users;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Collection{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", movies=" + movies +
				", users=" + users +
				'}';
	}
}
