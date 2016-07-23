package hutchtech.movies.app;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.controller.LoginController;
import hutchtech.movies.controller.MovieController;
import hutchtech.movies.controller.SignupController;
import hutchtech.movies.da.UserDao;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.*;
/**
 * Created by Scott Hutchings on 7/19/2016.
 */
public class Routes {

	public static final Logger LOG = Logger.getLogger(Routes.class);
	public static UserDao userDao;

	public static void main(String[] args) {
		userDao = new UserDao();

		port(getHerokuAssignedPort());
		staticFileLocation("/css");

		get(Path.Web.INDEX, (req, res) -> {
//			final Movie m = OimdbClient.findMovieByImdbId("tt0088763");
//			LOG.debug(m);
			final List<Movie> movies = OimdbClient.findMoviesByTitle("Star Wars");
			LOG.debug(movies);
			return ViewUtil.render(req, new HashMap<>(), Path.Template.INDEX);
		});
		get(Path.Web.LOGIN, LoginController.serveLoginPage);
		post(Path.Web.LOGIN, LoginController.handleLoginPost);
		post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
		get(Path.Web.SIGNUP, SignupController.serveSignupPage);
		post(Path.Web.SIGNUP, SignupController.handleSignupPost);
		get(Path.Web.COLLECTION, MovieController.myMovieCollection);
		get(Path.Web.MOVIES_SEARCH, MovieController.searchMovies);

		before(Path.Web.COLLECTION, LoginController::ensureUserIsLoggedIn);
	}

	private static int getHerokuAssignedPort(){
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null){
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //default for local testing
	}
}
