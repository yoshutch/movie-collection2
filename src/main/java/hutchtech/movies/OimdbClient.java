package hutchtech.movies;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hutchtech.movies.domain.Movie;
import hutchtech.movies.util.JsonMapper;
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

		final Movie movie = JsonMapper.mapper().readValue(httpGetRequest(url), Movie.class);
		if (movie.getGenres() != null && movie.getGenres().size() == 1){
			final String s = movie.getGenres().get(0);
			final String[] split = s.split(",");
			List<String> genres = new ArrayList<>();
			for (String s1 : split) {
				genres.add(s1.trim());
			}
			movie.setGenres(genres);
		}
		return movie;
	}

	public static List<Movie> findMoviesByTitle(String title) throws IOException{
		final URL url = new URL("http://www.omdbapi.com/?s=" + URLEncoder.encode(title, "UTF-8") + "&type=movie&r=json");

		final String m = JsonMapper.mapper().readTree(httpGetRequest(url)).path("Search").toString();
		final Movie[] movies = JsonMapper.mapper().readValue(m, Movie[].class);
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
}
