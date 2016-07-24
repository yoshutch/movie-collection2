package hutchtech.movies.controller;

import hutchtech.movies.domain.User;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hutchtech.movies.app.Routes.userDao;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public class SignupController {

	public static Route serveSignupPage = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, Path.Template.SIGNUP);
	};

	public static Route handleSignupPost = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		final String username = request.queryParams("username");
		final String name = request.queryParams("name");
		final String password = request.queryParams("password");
		final String passwordConf = request.queryParams("passwordConf");

		List<String> signupErrors = new ArrayList<>();
		if (username == null || username.isEmpty()){
			signupErrors.add("username is required");
		}
		if (password == null || password.isEmpty()) {
			signupErrors.add("password is required");
		}
		if (passwordConf == null || passwordConf.isEmpty()) {
			signupErrors.add("password confirmation is required");
		}
		if (password != null && !password.equals(passwordConf)) {
			signupErrors.add("passwords must match");
		}
		if (!signupErrors.isEmpty()){
			model.put("signupErrors", signupErrors);
			return ViewUtil.render(request, model, Path.Template.SIGNUP);
		}

		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setSalt(BCrypt.gensalt());
		user.setHashedPassword(BCrypt.hashpw(password, user.getSalt()));
		userDao.save(user);

		request.session().attribute("currentUser", userDao.getUserByUsername(username));
		response.redirect(Path.Web.COLLECTION);
		return null;
	};
}
