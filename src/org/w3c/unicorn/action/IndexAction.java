// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.MessageList;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;

import com.ibm.icu.util.ULocale;

public class IndexAction extends Action {

	private static final long serialVersionUID = 599055553694915687L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.initUnicorn();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
		
		resp.setContentType("text/html; charset=UTF-8");
		
		MessageList messages = new MessageList(Language.getDefaultLocale());
		String paramPrefix = Property.get("UNICORN_PARAMETER_PREFIX");
		
		String queryString = "./" + getQueryStringWithout(paramPrefix + "lang", req);
		ULocale lang = getLanguage((String) req.getParameter(paramPrefix + "lang"), req, messages);
		messages.setLocale(lang);
		String task = getTask((String) req.getParameter(paramPrefix + "task"), null);
		
		VelocityContext velocityContext = new VelocityContext(Language.getContext(lang));
		velocityContext.put("queryString", queryString);
		velocityContext.put("messages", messages);
		velocityContext.put("current_task", Framework.mapOfTask.get(task));
		velocityContext.put("default_task", Framework.mapOfTask.getDefaultTask());
		velocityContext.put("baseUri", "./");
		velocityContext.put("language_action", "./");
		
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
		
		PrintWriter writer = resp.getWriter();
		if (req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			velocityContext.put("ajaxCall", "true");
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
