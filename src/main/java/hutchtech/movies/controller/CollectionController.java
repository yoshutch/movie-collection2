package hutchtech.movies.controller;

import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoshutch on 7/24/16.
 */
public class CollectionController {

	public static Route viewMovieCollection = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();

		return ViewUtil.render(request, model, Path.Template.COLLECTION);
	};
}
