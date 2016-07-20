package hutchtech.movies.login;

import hutchtech.movies.user.UserController;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class LoginController {

	public static Route serveLoginPage = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, Path.Template.LOGIN);
	};

	public static Route handleLoginPost = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		if (!UserController.authenticate(request.queryParams("username"), request.queryParams("password"))){
			model.put("authenticationFailed", true);
		} else {
			model.put("authenticationSucceeded", true);
			request.session().attribute("currentUser", request.queryParams("username"));
		}

		return ViewUtil.render(request, model, Path.Template.LOGIN);
	};
}
