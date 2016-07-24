package hutchtech.movies.da;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by yoshutch on 7/24/16.
 */
public class DB {

	private MongoClient mongoClient;
	private MongoDatabase database;

	public DB() {
		MongoClientURI mongoUrl = new MongoClientURI(System.getenv("MONGODB_URI"));
		mongoClient = new MongoClient(mongoUrl);
		database = mongoClient.getDatabase(mongoUrl.getDatabase());
	}

	public MongoCollection<Document> getCollection(String collectionName) {
		return database.getCollection(collectionName);
	}
}
