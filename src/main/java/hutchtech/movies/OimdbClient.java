package hutchtech.movies;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hutchtech.movies.domain.Movie;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Scott Hutchings on 7/21/2016.
 */
public class OimdbClient {
	private static final Logger LOG = Logger.getLogger(OimdbClient.class);

	public static Movie findMovieByImdbId(String imdbId) throws IOException {
		final URL url = new URL("http://www.omdbapi.com/?i=" + URLEncoder.encode (imdbId,"UTF-8") + "&r=json");

		return mapper().readValue(httpGetRequest(url), Movie.class);
	}

	public static List<Movie> findMoviesByTitle(String title) throws IOException{
		final URL url = new URL("http://www.omdbapi.com/?s=" + URLEncoder.encode(title, "UTF-8") + "&type=movie&r=json");

		final String m = mapper().readTree(httpGetRequest(url)).path("Search").toString();
		final Movie[] movies = mapper().readValue(m, Movie[].class);
		List<Movie> movieList = new ArrayList<>();
		for (Movie movie : movies) {
			movieList.add(findMovieByImdbId(movie.getImdbId()));
		}
		return movieList;
	}

	private static String httpGetRequest(URL url) throws IOException{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200){
			LOG.error("Responses code not 200, actual: " + conn.getResponseCode());
		}

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));

		String output = "";
		String line;
		while ((line =  bufferedReader.readLine()) != null){
			output += line;
		}

		conn.disconnect();

		return output;
	}

	private static ObjectMapper mapper(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper;
	}
}
