package hutchtech.movies.controller;

import hutchtech.movies.controller.UserController;
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
			request.session().attribute("currentUser", request.queryParams("username"));
			if (request.session().attribute("loginRedirect") != null) {
				response.redirect(request.session().attribute("loginRedirect"));
			} else {
				response.redirect(Path.Web.COLLECTION);
			}
		}
		return ViewUtil.render(request, model, Path.Template.LOGIN);
	};

	public static Route handleLogoutPost = (Request request, Response response) -> {
		request.session().removeAttribute("currentUser");
		response.redirect(Path.Web.LOGIN);
		return null;
	};

	public static void ensureUserIsLoggedIn (Request request, Response response) {
		if (request.session().attribute("currentUser") == null){
			request.session().attribute("loginRedirect", request.pathInfo());
			response.redirect(Path.Web.LOGIN);
		}
	}
}
