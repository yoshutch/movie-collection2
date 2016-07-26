package hutchtech.movies.da;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import hutchtech.movies.domain.User;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class UserDao {

	private static final Logger LOG = Logger.getLogger(UserDao.class);

	private DB database;

	public UserDao(DB db) {
		this.database = db;
	}

	public Set<User> getUsersByIds(Collection<String> ids){
		final MongoCollection<Document> users = database.getCollection("Users");
		FindIterable<Document> foundUsers = users.find(Filters.in("_id", ids));
		Set<User> userSet = new HashSet<>();
		for (Document foundUser : foundUsers) {
			userSet.add(mapDocumentToUser(foundUser));
		}
		return userSet;
	}

	public User getUserByUsername(String username){
		final MongoCollection<Document> users = database.getCollection("Users");
		return mapDocumentToUser(users.find(Filters.eq("username", username)).first());
	}

	public void save(User user){
		database.getCollection("Users").insertOne(mapNewUserToDocument(user));
	}

	public void update(User user){
		database.getCollection("Users").updateOne(
				new Document().append("_id", user.getId()),
				mapUserToDocument(user)
		);
	}

	private User mapDocumentToUser(Document document){
		if (document == null){
			return null;
		}
		User user = new User();
		user.setId(document.getObjectId("_id").toString());
		user.setUsername(document.getString("username"));
		user.setName(document.getString("name"));
		user.setSalt(document.getString("salt"));
		user.setHashedPassword(document.getString("hashedPassword"));
		user.setDefaultCollection(document.getString("defaultCollection"));

		return user;
	}

	private Document mapUserToDocument(User user){
		return mapNewUserToDocument(user)
				.append("_id", user.getId());
	}

	private Document mapNewUserToDocument(User user){
		return new Document()
				.append("username", user.getUsername())
				.append("name", user.getName())
				.append("salt", user.getSalt())
				.append("hashedPassword", user.getHashedPassword())
				.append("defaultCollection", user.getDefaultCollection());
	}
}
