package hutchtech.movies.controller;

import hutchtech.movies.controller.UserController;
import hutchtech.movies.domain.User;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static hutchtech.movies.app.Routes.userDao;

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
			final User currentUser = userDao.getUserByUsername(request.queryParams("username"));
			request.session().attribute("currentUser", currentUser);
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
			String loginRedirect = request.pathInfo();
			if (request.queryString() != null && !request.queryString().isEmpty()){
				loginRedirect += "?" + request.queryString();
			}
			request.session().attribute("loginRedirect", loginRedirect);
			response.redirect(Path.Web.LOGIN);
		}
	}
}
