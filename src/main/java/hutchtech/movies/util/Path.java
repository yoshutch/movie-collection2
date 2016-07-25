package hutchtech.movies.util;

import lombok.*;
/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class Path {
	public static class Web {
		@Getter public static final String INDEX = "/";
		@Getter public static final String LOGIN = "/login";
		@Getter public static final String LOGOUT = "/logout";
		@Getter public static final String SIGNUP = "/signup";
		@Getter public static final String COLLECTION = "/collection";
		@Getter public static final String MOVIES_SEARCH = "/movies/search";
		@Getter public static final String ADD_MOVIE = "/movies/add";
	}

	public static class Template {
		public static final String INDEX = "/velocity/index.vm";
		public static final String LOGIN = "/velocity/login.vm";
		public static final String SIGNUP = "/velocity/signup.vm";
		public static final String COLLECTION = "/velocity/collection.vm";
		public static final String MOVIES_SEARCH = "/velocity/search.vm";
	}
}
