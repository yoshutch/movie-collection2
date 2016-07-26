package hutchtech.movies.da;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import hutchtech.movies.domain.Collection;
import hutchtech.movies.domain.Medium;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.domain.Rating;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Created by yoshutch on 7/24/16.
 */
public class CollectionDao {
	private static final Logger LOG = Logger.getLogger(CollectionDao.class);

	private DB database;

	public CollectionDao(DB db){
		this.database = db;
	}

	public List<Collection> getCollectionsForUserId(String userId){
		BasicDBObject query = new BasicDBObject();
		query.put("users", Arrays.asList(userId));
		final FindIterable<Document> iterable = database.getCollection("Collections").find(query);
		List<Collection> result = new ArrayList<>();
		for (Document document : iterable) {
			result.add(mapDocumentToCollection(document));
		}
		return result;
	}

	public Collection getCollectionById(String id){
		Document document = database.getCollection("Collections").find(Filters.eq("_id", new ObjectId(id))).first();
		return mapDocumentToCollection(document);
	}

	public void saveCollection(Collection collection){
		final Document cDocument = mapCollectionToDocument(collection);
		database.getCollection("Collections").insertOne(cDocument);
		collection.setId(cDocument.getObjectId("_id").toString());
	}

	public void updateCollection(Collection collection){
		final Document bson1 = mapCollectionToDocument(collection);
//		database.getCollection("Collections").updateOne(
//				new Document().append("_id", collection.getId()),
//				bson1);
		BasicDBList movieList = new BasicDBList();
		for (Movie movie : collection.getMovies()) {
			movieList.add(mapMovieToDocument(movie));
		}
		database.getCollection("Collections").updateOne(
				Filters.eq("_id", new ObjectId(collection.getId())),
				new Document("$set", new Document("movies", movieList)));
	}

	private Document mapCollectionToDocument(Collection collection){
		return new Document()
				.append("name", collection.getName())
				.append("users", collection.getUsers())
				.append("movies", collection.getMovies());
	}

	private Collection mapDocumentToCollection(Document document){
		Collection collection = new Collection();
		collection.setId(document.get("_id").toString());
		collection.setName(document.getString("name"));
		collection.setUsers((List<String>) document.get("users"));
		final ArrayList<Document> movieDocumentArray = (ArrayList<Document>) document.get("movies");
		List<Movie> movies = new ArrayList<>();
		for (Document document1 : movieDocumentArray) {
			movies.add(mapDocumentToMovie(document1));
		}
		collection.setMovies(movies);
		return collection;
	}

	private Document mapMovieToDocument (Movie movie){
		final List<Medium> mediums = movie.getMediums();
		BasicDBList list = new BasicDBList();
		for (Medium medium : mediums) {
			list.add(new Document()
					.append("val", medium.getVal())
					.append("note", medium.getNote()));
		}
		return new Document()
				.append("imdbId", movie.getImdbId())
				.append("title", movie.getTitle())
				.append("poster", movie.getPosterUrl())
				.append("rating", movie.getRating().name())
				.append("runtime", movie.getRuntime())
				.append("genres", movie.getGenres())
				.append("mediums", list);
	}

	private Movie mapDocumentToMovie (Document document) {
		Movie movie = new Movie();
		movie.setImdbId(document.getString("imdbId"));
		movie.setTitle(document.getString("title"));
		movie.setPosterUrl(document.getString("poster"));
		movie.setRating(Rating.valueOf(document.getString("rating")));
		movie.setRuntime(document.getString("runtime"));
		movie.setGenres((List<String>) document.get("genres"));
		final List<Document> mediumsList = (List<Document>) document.get("mediums");
		List<Medium> mediums = new ArrayList<>();
		if (mediumsList != null) {
			for (Document document1 : mediumsList) {
				mediums.add(new Medium(document1.getString("val"), document1.getString("note")));
			}
			movie.setMediums(mediums);
		}
		return movie;
	}


}
