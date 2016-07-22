package hutchtech.movies.controller;

import hutchtech.movies.domain.User;
import org.mindrot.jbcrypt.BCrypt;
import static hutchtech.movies.app.Routes.userDao;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */


public class UserController {

	public static boolean authenticate(String username, String password){
		if (username.isEmpty() || password.isEmpty()){
			return false;
		}
		User user = userDao.getUserByUsername(username);
		if (user == null){
			return false;
		}
		String hashedPassword = BCrypt.hashpw(password, user.getSalt());
		return hashedPassword.equals(user.getHashedPassword());
	}

}
