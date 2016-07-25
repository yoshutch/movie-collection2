package hutchtech.movies.controller;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.domain.Collection;
import hutchtech.movies.domain.Medium;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.domain.User;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hutchtech.movies.app.Routes.collectionDao;

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
		final User currentUser = request.session().attribute("currentUser");
		final List<Collection> collections = collectionDao.getCollectionsForUserId(currentUser.getId());
		model.put("collections", collections);
		return ViewUtil.render(request, model, Path.Template.MOVIES_SEARCH);
	};

	public static Route addMovieToCollection = (Request request, Response response) -> {
		final String imdbId = request.queryParams("imdbId");
		final String collectionId = request.queryParams("collectionId");
		final String dvd = request.queryParams("dvd");
		final String bluRay = request.queryParams("bluRay");
		final String vhs = request.queryParams("vhs");
		final String amazon = request.queryParams("amazon");
		final String ultraviolet = request.queryParams("ultraviolet");
		final String play = request.queryParams("play");
		final String digital = request.queryParams("digital");
		final String amazonText = request.queryParams("amazonText");
		final String ultravioletText = request.queryParams("ultravioletText");
		final String playText = request.queryParams("playText");
		final String digitalText = request.queryParams("digitalText");

		Movie movie = new Movie();
		movie.setImdbId(imdbId);

		List<Medium> mediums = new ArrayList<>();
		if (dvd != null && dvd.equals("Y")){
			mediums.add(Medium.DVD);
		}
		if (bluRay != null && bluRay.equals("Y")){
			mediums.add(Medium.BLURAY);
		}
		if (vhs != null && vhs.equals("Y")){
			mediums.add(Medium.VHS);
		}
		Medium m = null;
		if (amazon != null && amazon.equals("Y")){
			m = Medium.AMAZON;
			if (amazonText != null) {
				m.setNote(amazonText);
			}
			mediums.add(m);
		}
		if (ultraviolet != null && ultraviolet.equals("Y")){
			m = Medium.ULTRA_VIOLET;
			if (ultravioletText != null) {
				m.setNote(ultravioletText);
			}
			mediums.add(m);
		}
		if (play != null && play.equals("Y")){
			m = Medium.GOOGLE_PLAY;
			if (playText != null) {
				m.setNote(playText);
			}
			mediums.add(m);
		}
		if (digital != null && digital.equals("Y")){
			m = Medium.DIGITAL;
			if (digitalText != null) {
				m.setNote(digitalText);
			}
			mediums.add(m);
		}

		final Collection collection = collectionDao.getCollectionById(collectionId);
		final List<Movie> movies = collection.getMovies();
		if (movies.contains(movie)){
			final int i = movies.indexOf(movie);
			movies.get(i).setMediums(mediums);
		} else {
			movie = OimdbClient.findMovieByImdbId(imdbId);
			movie.setMediums(mediums);
			movies.add(movie);
		}

		collection.setMovies(movies);
		collectionDao.updateCollection(collection);

		return "{\"Success\": \"true\"}";
	};
}
