package hutchtech.movies.user;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class UserDao {
	private final List<User> users = Arrays.asList(
			//        Username    Salt for hash                    Hashed password (the password is "password" for all users)
			new User("test", "$2a$10$h.dl5J86rGH7I8bD9bZeZe", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO")
	);

	public User getUserByUsername(String username){
		return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
	}
}
