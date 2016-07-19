package hutchtech.movies;

import static spark.Spark.*;
/**
 * Created by Scott Hutchings on 7/19/2016.
 */
public class Routes {
	public static void main(String[] args) {
		port(getHerokuAssignedPort());
		get("/", (req, res) -> "Hello Scott");
	}

	private static int getHerokuAssignedPort(){
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null){
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //default for local testing
	}
}
