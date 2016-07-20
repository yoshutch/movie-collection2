package hutchtech.movies.util;

import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class ViewUtil {
	public static String render(Request request, Map<String, Object> model, String templatePath){
		return velocityTemplateEngine().render(new ModelAndView(model, templatePath));
	}

	private static VelocityTemplateEngine velocityTemplateEngine(){
		VelocityTemplateEngine engine = new VelocityTemplateEngine();
		return engine;
	}
}
