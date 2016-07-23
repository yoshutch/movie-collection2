package hutchtech.movies.controller;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public class MovieController {

	public static Route myMovieCollection = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, Path.Template.COLLECTION);
	};

	public static Route searchMovies = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		final String search = request.queryParams("search");
		final List<Movie> movies = OimdbClient.findMoviesByTitle(search);
		model.put("movies", movies);
		return ViewUtil.render(request, model, Path.Template.MOVIES_SEARCH);
	};

}
