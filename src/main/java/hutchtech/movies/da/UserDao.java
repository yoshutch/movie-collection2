package hutchtech.movies.da;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import hutchtech.movies.domain.User;
import org.apache.log4j.Logger;
import org.bson.Document;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class UserDao {

	public static final Logger LOG = Logger.getLogger(UserDao.class);

	private MongoClient mongoClient;
	private MongoDatabase database;

	public UserDao() {

		MongoClientURI mongoUrl = new MongoClientURI(System.getenv("MONGODB_URI"));
		mongoClient = new MongoClient(mongoUrl);
		database = mongoClient.getDatabase(mongoUrl.getDatabase());
		LOG.debug(database.listCollectionNames().first());
	}

	public User getUserByUsername(String username){
		final MongoCollection<Document> users = database.getCollection("Users");
		return mapDocumentToUser(users.find(Filters.eq("username", username)).first());
	}

	public void save(User user){
		database.getCollection("Users").insertOne(mapUserToDocument(user));
	}

	private User mapDocumentToUser(Document document){
		if (document == null){
			return null;
		}
		User user = new User();
		user.setId(document.getObjectId("_id").toString());
		user.setUsername(document.getString("username"));
		user.setSalt(document.getString("salt"));
		user.setHashedPassword(document.getString("hashedPassword"));

		return user;
	}

	private Document mapUserToDocument(User user){
		return new Document()
				.append("username", user.getUsername())
				.append("salt", user.getSalt())
				.append("hashedPassword", user.getHashedPassword());
	}
}
