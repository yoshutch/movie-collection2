package hutchtech.movies.da;

import com.mongodb.client.model.Filters;
import hutchtech.movies.domain.Collection;
import org.apache.log4j.Logger;
import org.bson.Document;

import static hutchtech.movies.app.Routes.userDao;

/**
 * Created by yoshutch on 7/24/16.
 */
public class CollectionDao {
	private static final Logger LOG = Logger.getLogger(CollectionDao.class);

	private DB database;

	public CollectionDao(DB db){
		this.database = db;
	}

	public Collection getCollectionById(String id){
		Document document = database.getCollection("Collections").find(Filters.eq("_id", id)).first();
		return mapDocumentToCollection(document);
	}

	public void saveCollection(Collection collection){
		database.getCollection("Collections").insertOne(mapNewCollectionToDocument(collection));
	}

	public void updateCollection(Collection collection){
		database.getCollection("Collections").updateOne(
				new Document().append("_id", collection.getId()),
				mapCollectionToDocument(collection));
	}

	private Document mapNewCollectionToDocument(Collection collection){
		return new Document()
				.append("name", collection.getName())
				.append("users", collection.getUsers())
				.append("movies", collection.getMovies());
	}

	private Document mapCollectionToDocument(Collection collection){
		return mapNewCollectionToDocument(collection)
				.append("_id", collection.getId());
	}

	private Collection mapDocumentToCollection(Document document){
		Collection collection = new Collection();
		collection.setId(document.getString("_id"));
		collection.setName(document.getString("name"));

		return collection;
	}
}
