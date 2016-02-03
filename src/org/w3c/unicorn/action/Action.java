// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.MessageList;
import org.w3c.unicorn.util.Property;

import com.ibm.icu.util.ULocale;

public abstract class Action extends HttpServlet {
	
	private static final long serialVersionUID = -7503310240481494239L;
	
	protected static Log logger = LogFactory.getLog(Action.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.initUnicorn();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	protected String getQueryStringWithout(String parameterName, HttpServletRequest req) {
		String query = req.getQueryString();
		String queryString;
		if (query == null) {
			queryString = "./?";
		} else {
			queryString = "?";
			queryString += query.replaceAll("&?" + parameterName + "=[^&]*", "");
			if (!queryString.equals("?"))
				queryString += "&";
		}
		return StringEscapeUtils.escapeHtml(queryString);
	}
	
	public ULocale getLanguage(String langParameter, HttpServletRequest req, ArrayList<Message> messages) {
		ULocale parameterLocale;
		ULocale browserLocale;
		ULocale matchedLocale;
		
		if (langParameter != null) {
			parameterLocale = Language.getAvailableLocale(langParameter);
			matchedLocale = Language.getUILocale(langParameter);
			if (messages != null && parameterLocale != matchedLocale) {
          if ("true".equals(Property.get("ENABLE_TRANSLATION_CONTRIBS"))) {
              messages.add(new Message(Message.INFO, "$message_unavailable_requested_language", null, parameterLocale.getDisplayName(parameterLocale), "?" + Property.get("UNICORN_PARAMETER_PREFIX") + "lang=" + parameterLocale.getName()));
          }
				return matchedLocale;
			}
		} else {
			if (req.getHeader("Accept-Language") != null) {
				browserLocale = Language.getAvailableLocale(req.getHeader("Accept-Language"));
				matchedLocale = Language.getUILocale(req.getHeader("Accept-Language"));
			} else {
				return Language.getDefaultLocale();
			}
			
			if (messages != null && browserLocale != matchedLocale) {
          if ("true".equals(Property.get("ENABLE_TRANSLATION_CONTRIBS"))) {
              messages.add(new Message(Message.INFO, "$message_unavailable_language", null, browserLocale.getDisplayName(browserLocale), "?" + Property.get("UNICORN_PARAMETER_PREFIX") + "lang=" + browserLocale.getName()));
          }
          return matchedLocale;
			}
		}
		
		if ("true".equals(Property.get("ENABLE_TRANSLATION_CONTRIBS")) && messages != null && !Language.isComplete(matchedLocale)) {
			messages.add(new Message(Message.INFO, "$message_incomplete_language", null, "", "?" + Property.get("UNICORN_PARAMETER_PREFIX") + "lang=" + matchedLocale.getName()));
		}
		return matchedLocale;
	}

	public static String getTask(String taskParameter, MessageList messages) {
		String task;
		if (taskParameter == null || !Framework.mapOfTask.containsKey(taskParameter))
			task = Framework.mapOfTask.getDefaultTaskId();
		else
			task = taskParameter;
			
		if (messages == null)
			return task;
		
		if (taskParameter == null) {
			Message mess = new Message(Message.WARNING, "$message_no_task", null, Framework.getDefaultTask().getLongName(messages.getLocale().getName()));
			messages.add(mess);
		} else if (!Framework.mapOfTask.containsKey(taskParameter)) {
			Message mess = new Message(Message.WARNING, "$message_unknown_task", null, taskParameter, Framework.getDefaultTask().getLongName(messages.getLocale().getName()));
			messages.add(mess);
		}
		
		return task;
	}
	
}
