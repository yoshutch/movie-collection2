package hutchtech.movies.app;

import hutchtech.movies.login.LoginController;
import hutchtech.movies.user.UserDao;
import hutchtech.movies.util.Path;
import org.apache.log4j.Logger;

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


		get("/", (req, res) -> "Hello Scott");
		get(Path.Web.LOGIN, LoginController.serveLoginPage);
		post(Path.Web.LOGIN, LoginController.handleLoginPost);
	}

	private static int getHerokuAssignedPort(){
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null){
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //default for local testing
	}
}
