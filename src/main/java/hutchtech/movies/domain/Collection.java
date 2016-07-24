package hutchtech.movies.domain;

import java.util.Set;

/**
 * Created by yoshutch on 7/24/16.
 */
public class Collection {
	private String id;
	private String name;
	private Set<Movie> movies;
	private Set<User> users;

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

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
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
