package hutchtech.movies;

import static spark.Spark.*;
/**
 * Created by Scott Hutchings on 7/19/2016.
 */
public class Routes {
	public static void main(String[] args) {
		get("/", (req, res) -> "Hello Scott");
	}
}
