// $Id: IndexAction.java,v 1.22 2009-09-24 17:39:54 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;

public class IndexAction extends Action {

	private static final long serialVersionUID = 599055553694915687L;
	
	//private static Log logger = LogFactory.getLog(IndexAction.class);
	
	private VelocityContext velocityContext;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.init();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
		
		resp.setContentType("text/html; charset=UTF-8");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		String paramPrefix = Property.get("UNICORN_PARAMETER_PREFIX");
		
		String lang;
		String task;
		String queryString = "./" + getQueryStringWithout(paramPrefix + "lang", req);
		if (req.getAttribute("unicorn_parameters") instanceof Map<?, ?>) {
			Map<?, ?> reqParams = (Map<?, ?>) req.getAttribute("unicorn_parameters");
			lang = getLanguage((String) reqParams.get(paramPrefix + "lang"), req, messages);
			task = getTask((String) reqParams.get(paramPrefix + "task"), null);
		} else {
			lang = getLanguage((String) req.getParameter(paramPrefix + "lang"), req, messages);
			task = getTask((String) req.getParameter(paramPrefix + "task"), null);
		}
		
		if (req.getAttribute("unicorn_messages") != null) {
			ArrayList<?> ucnMessages = (ArrayList<?>) req.getAttribute("unicorn_messages");
			for (Object mess : ucnMessages)
				messages.add((Message) mess);
		}
		
		velocityContext = new VelocityContext(Language.getContext(lang));
		velocityContext.put("queryString", queryString);
		velocityContext.put("messages", messages);
		velocityContext.put("current_task", Framework.mapOfTask.get(task));
		velocityContext.put("default_task", Framework.mapOfTask.get(Framework.mapOfTask.getDefaultTaskId()));
		velocityContext.put("baseUri", "./");
		
		Enumeration<?> paramEnum = req.getParameterNames();
		while (paramEnum.hasMoreElements()) {
			String key = (String) paramEnum.nextElement();
			String ref;
			if (key.startsWith(Property.get("UNICORN_PARAMETER_OUTPUT_PREFIX")))
				continue;
			if (key.startsWith(paramPrefix))
				ref = "param_" + key.substring(paramPrefix.length());
			else
				ref = "param_" + key;
			if (req.getParameterValues(key).length > 1) {
				String[] s = req.getParameterValues(key);
				ArrayList<String> array = new ArrayList<String>();
				for (int i = 0; i < s.length; i++)
					array.add(s[i]);
				velocityContext.put(ref, array);
			}
			else {
				velocityContext.put(ref, req.getParameter(key));
			}
		}
		
		if (req.getAttribute("unicorn_parameters") instanceof Map<?, ?>) {
			Map<?, ?> reqParams = (Map<?, ?>) req.getAttribute("unicorn_parameters");
			
			for (Object objKey : reqParams.keySet()) {
				String key = (String) objKey;
				String ref;
				if (key.startsWith(Property.get("UNICORN_PARAMETER_OUTPUT_PREFIX")))
					continue;
				if (key.startsWith(paramPrefix))
					ref = "param_" + key.substring(paramPrefix.length());
				else
					ref = "param_" + key;
				if (reqParams.get(key) instanceof String[]) {
					String[] s = (String[]) reqParams.get(key);
					ArrayList<String> array = new ArrayList<String>();
					for (int i = 0; i < s.length; i++)
						array.add(s[i]);
					velocityContext.put(ref, array);
				}
				else {
					velocityContext.put(ref, reqParams.get(key));
				}
			}
		}
		
		PrintWriter writer = resp.getWriter();
		if (req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			Templates.write("parameters.vm", velocityContext, writer);
		} else {
			Templates.write("index.vm", velocityContext, writer);
		}
		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
