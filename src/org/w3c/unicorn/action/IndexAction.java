package org.w3c.unicorn.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.language.Language;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;
import org.w3c.unicorn.Framework;

public class IndexAction extends Action {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(IndexAction.class);
	private VelocityContext velocityContext;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		resp.setContentType("text/html; charset=UTF-8");
		
		// Language negotiation
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		if (langParameter == null || !Framework.getLanguageProperties().containsKey(langParameter))
			langParameter = Language.negociate(req.getLocales());
		
		velocityContext = new VelocityContext(Language.getContext(langParameter));
		
		String taskParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "task");
		if (taskParameter == null || !Framework.mapOfTask.containsKey(taskParameter))
			taskParameter = Framework.mapOfTask.getDefaultTaskId();
		
		velocityContext.put("current_task", Framework.mapOfTask.get(taskParameter));
		
		if (req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			//for JavaScript testing purposes
			/*long s = System.currentTimeMillis();
			long t;
			do {
				 t = System.currentTimeMillis();
			} while ((t - s) < 3000);*/
			// -----------------
			Templates.write("parameters.vm", velocityContext, resp.getWriter());
			resp.getWriter().close();
		} else {
			Templates.write("index.vm", velocityContext, resp.getWriter());
			resp.getWriter().close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
