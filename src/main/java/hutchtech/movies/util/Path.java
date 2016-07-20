package hutchtech.movies.util;

import lombok.*;
/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class Path {
	public static class Web {
		@Getter public static final String INDEX = "";
		@Getter public static final String LOGIN = "/login";
	}

	public static class Template {
		public static final String LOGIN = "/velocity/login.vm";
	}
}
