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
	}

	public static class Template {
		public static final String INDEX = "/velocity/index.vm";
		public static final String LOGIN = "/velocity/login.vm";
		public static final String SIGNUP = "/velocity/signup.vm";
	}
}
