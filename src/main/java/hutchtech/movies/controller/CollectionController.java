package hutchtech.movies.controller;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.domain.Collection;
import hutchtech.movies.domain.Medium;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.domain.User;
import hutchtech.movies.util.Constants;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;

import static hutchtech.movies.app.Routes.collectionDao;
import static hutchtech.movies.app.Routes.userDao;

/**
 * Created by yoshutch on 7/24/16.
 */
public class CollectionController {

	public static Route viewMovieCollection = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		final User currentUser = request.session().attribute("currentUser");
		final List<Collection> collections = collectionDao.getCollectionsForUserId(currentUser.getId());
		model.put("collections", collections);

//		if there are no collections for user create a new default one
		if (collections == null || collections.isEmpty()){
			Collection coll = new Collection();
			coll.setName("Default");
			coll.setUsers(Arrays.asList(currentUser.getId()));

			collectionDao.saveCollection(coll);

//			set user's default collection
			currentUser.setDefaultCollection(coll.getId());
			userDao.update(currentUser);
		}

//		add default collection to model
		final Collection collection = collectionDao.getCollectionById(currentUser.getDefaultCollection());
		model.put("collection", collection);

//		sort movies and add to model
		final List<Movie> movies = collection.getMovies();
		final String sortBy = request.queryParams("sortBy");
		if (Constants.SORT_BY_RUNTIME_SHORT.equals(sortBy)){
			Collections.sort(movies, new RuntimeComparator(true));
		} else if (Constants.SORT_BY_RUNTIME_LONG.equals(sortBy)){
			Collections.sort(movies, new RuntimeComparator(false));
		} else if (Constants.SORT_BY_RATING.equals(sortBy)){
			Collections.sort(movies, (m1, m2) -> m1.getRating().compareTo(m2.getRating()));
		} else {
			Collections.sort(movies);
		}

		Set<String> genres = new TreeSet<>();
		Set<Medium> mediums = new TreeSet<>();
		for (Movie movie : movies) {
			genres.addAll(movie.getGenres());
			mediums.addAll(movie.getMediums());
		}
		model.put("genres", genres);
		model.put("mediums", mediums);
		model.put("movies", movies);

		return ViewUtil.render(request, model, Path.Template.COLLECTION);
	};

	private static class RuntimeComparator implements Comparator<Movie>{
		private boolean shortToLong;

		public RuntimeComparator(boolean shortToLong) {
			this.shortToLong = shortToLong;
		}

		@Override
		public int compare(Movie m1, Movie m2) {
			String r1 = m1.getRuntime();
			String r2 = m2.getRuntime();
			if (r1 == null){
				return -1;
			}
			r1 = r1.replace(" min", "");
			r2 = r2.replace(" min", "");
			if (shortToLong) {
				return Integer.valueOf(r1).compareTo(Integer.valueOf(r2));
			} else {
				return Integer.valueOf(r2).compareTo(Integer.valueOf(r1));
			}
		}
	}
}
