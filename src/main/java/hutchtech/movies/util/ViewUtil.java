package hutchtech.movies.util;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

/**
 * Created by Scott Hutchings on 7/20/2016.
 */
public class ViewUtil {
	public static String render(Request request, Map<String, Object> model, String templatePath){
		final Object currentUser = request.session().attribute("currentUser");
		if (currentUser != null) {
			model.put("currentUser", currentUser);
			model.put("notLoggedIn", false);
		} else {
			model.put("notLoggedIn", true);
		}
		model.put("WebPath", Path.Web.class);
		model.put("Constants", Constants.class);
		return velocityTemplateEngine().render(new ModelAndView(model, templatePath));
	}

	private static VelocityTemplateEngine velocityTemplateEngine(){
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty("resource.loader", "class");
		engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
		return new VelocityTemplateEngine(engine);
	}
}
