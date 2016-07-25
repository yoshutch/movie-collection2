package hutchtech.movies.controller;

import hutchtech.movies.OimdbClient;
import hutchtech.movies.domain.Collection;
import hutchtech.movies.domain.User;
import hutchtech.movies.util.Path;
import hutchtech.movies.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hutchtech.movies.app.Routes.collectionDao;

/**
 * Created by yoshutch on 7/24/16.
 */
public class CollectionController {

	public static Route viewMovieCollection = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		User currentUser = request.session().attribute("currentUser");
		List<Collection> collections = collectionDao.getCollectionsForUserId(currentUser.getId());
		if (collections == null || collections.isEmpty()){
			Collection coll = new Collection();
			coll.setName("Default");
			coll.setUsers(Arrays.asList(currentUser.getId()));

			collectionDao.saveCollection(coll);

			model.put("collection", coll);
		} else {
			if (collections.size() == 1){
				final Collection collection = collections.get(0);
				model.put("collection", collection);
//				collection.setMovies(Arrays.asList(OimdbClient.findMovieByImdbId("tt0457510")));
//				collectionDao.updateCollection(collection);
			} else {
				collections.stream().filter(collection -> collection.getName().equals("Default")).forEach(collection -> {
					model.put("collection", collection);
				});
			}
		}
		return ViewUtil.render(request, model, Path.Template.COLLECTION);
	};
}
