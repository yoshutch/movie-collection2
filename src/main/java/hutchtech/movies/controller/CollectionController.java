package hutchtech.movies.controller;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.domain.*;
import hutchtech.movies.domain.Collection;
import hutchtech.movies.util.Constants;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.*;
import java.util.stream.Collectors;

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
			User updateUser = new User();
			updateUser.setId(currentUser.getId());
			updateUser.setDefaultCollection(coll.getId());
			userDao.update(updateUser);
			currentUser.setDefaultCollection(coll.getId());
		}

//		add default collection to model
		final Collection collection = collectionDao.getCollectionById(currentUser.getDefaultCollection());
		model.put("collection", collection);

//		filter movies
		List<Movie> movies = collection.getMovies();
		model.put("totalNumOfMovies", movies.size());
		Set<String> genres = new TreeSet<>();
		Set<Medium> mediums = new TreeSet<>();
		Set<Rating> ratings = new TreeSet<>();
		for (Movie movie : movies) {
			genres.addAll(movie.getGenres());
			mediums.addAll(movie.getMediums());
			ratings.add(movie.getRating());
		}
		model.put("genres", genres); //all genres before filter
		model.put("mediums", mediums); //all mediums before filter
		model.put("ratings", ratings); //all ratings before filter
		final String[] genreFiltersArray = request.queryParamsValues("genreFilter");
		final String[] mediumFiltersArray = request.queryParamsValues("mediumFilter");
		final String[] ratingFiltersArray = request.queryParamsValues("ratingFilter");
		final Set<String> genreFilters = genreFiltersArray == null ? genres : new HashSet<>(Arrays.asList(genreFiltersArray));
		final Set<Medium> mediumFilters = mediumFiltersArray == null ? mediums : Arrays.stream(mediumFiltersArray).map(Medium::new).collect(Collectors.toSet());
		final Set<Rating> ratingFilters = ratingFiltersArray == null ? ratings : Arrays.stream(ratingFiltersArray).map(Rating::new).collect(Collectors.toSet());

		model.put("genreFilters", genreFilters);
		model.put("mediumFilters", mediumFilters);
		model.put("ratingFilters", ratingFilters);

		movies = movies.stream()
					.filter(movie -> !Collections.disjoint(movie.getGenres(), genreFilters))
					.filter(movie -> !Collections.disjoint(
							movie.getMediums().stream()
									.map(Medium::getVal).collect(Collectors.toList()
							), mediumFilters.stream()
									.map(Medium::getVal).collect(Collectors.toList())))
					.filter(movie -> ratingFilters.contains(movie.getRating()))
					.collect(Collectors.toList());

//		sort movies
		String sortBy = request.queryParams("sortBy");
		if (Constants.SORT_BY_RUNTIME_SHORT.equals(sortBy)){
			Collections.sort(movies, new RuntimeComparator(true));
		} else if (Constants.SORT_BY_RUNTIME_LONG.equals(sortBy)){
			Collections.sort(movies, new RuntimeComparator(false));
		} else if (Constants.SORT_BY_RATING.equals(sortBy)){
			Collections.sort(movies, (m1, m2) -> m1.getRating().compareTo(m2.getRating()));
		} else {
			sortBy = Constants.SORT_BY_TITLE;
			Collections.sort(movies);
		}
		model.put("sortBy", sortBy);

//		add movies to model
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
