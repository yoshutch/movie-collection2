package hutchtech.movies.app;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.controller.LoginController;
import hutchtech.movies.controller.SignupController;
import hutchtech.movies.da.UserDao;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;

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

		get(Path.Web.INDEX, (req, res) -> {
			final Movie m = OimdbClient.findMovieByImdbId("tt0088763");
			LOG.debug(m);
			return ViewUtil.render(req, new HashMap<String, Object>(), Path.Template.INDEX);
		});
		get(Path.Web.LOGIN, LoginController.serveLoginPage);
		post(Path.Web.LOGIN, LoginController.handleLoginPost);
		post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
		get(Path.Web.SIGNUP, SignupController.serveSignupPage);
		post(Path.Web.SIGNUP, SignupController.handleSignupPost);
	}

	private static int getHerokuAssignedPort(){
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null){
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //default for local testing
	}
}
