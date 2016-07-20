package hutchtech.movies;

import org.apache.log4j.Logger;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.sparkjava.CallbackRoute;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;
import org.pac4j.sparkjava.RequiresAuthenticationFilter;
import spark.Route;

import static spark.Spark.*;
/**
 * Created by Scott Hutchings on 7/19/2016.
 */
public class Routes {
	public static final Logger LOG = Logger.getLogger(Routes.class);

	public static void main(String[] args) {

		final int port = getPort();
		LOG.debug("PORT: " + port);

		port(port);

		setupSecurity();

		get("/status", (req, res) -> System.getenv("TEST"));
		get("/", (request, response) -> "got in");
	}

	private static void setupSecurity(){
		final String googleOauthId = System.getenv("OAUTH.GOOGLE.CLIENTID");
		final String googleOauthSecret = System.getenv("OAUTH.GOOGLE.SECRET");
		final String baseUrl = System.getenv("BASEURL");
		final String stage = System.getenv("STAGE");

		Google2Client google2Client = new Google2Client(googleOauthId, googleOauthSecret);

		String callbackUrl = baseUrl + /*":" + getPort() +*/ "/callback";
		Clients clients = new Clients(callbackUrl, google2Client);

		final Config config = new Config(clients);
		config.setHttpActionAdapter(new DefaultHttpActionAdapter());

		final Route callback = new CallbackRoute(config);
		get("/callback", callback);
		post("/callback", callback);

		before("/", new RequiresAuthenticationFilter(config, "Google2Client"));
	}

	private static int getPort(){
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null){
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //default for local testing
	}
}
