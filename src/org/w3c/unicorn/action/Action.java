// $Id: Action.java,v 1.18 2009-10-08 11:14:43 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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

public abstract class Action extends HttpServlet {
	
	private static final long serialVersionUID = -7503310240481494239L;
	
	private static Log logger = LogFactory.getLog(Action.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.init();
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
	
	public String getLanguage(String langParameter, HttpServletRequest req, ArrayList<Message> messages) {
		
		String lang;
		if (langParameter == null || !Framework.getLanguageProperties().containsKey(langParameter)) {
			lang = Language.negociate(req.getLocales());
		} else
			lang = langParameter;
		
		if (messages == null)
			return lang;
		
		if (langParameter != null && !Framework.getLanguageProperties().containsKey(langParameter)) {
			if (Language.isISOLanguageCode(langParameter)) {
				Locale locale = Language.getLocale(langParameter);
				if (locale == null) {
					logger.warn("Missing locale: " + langParameter + ". This locale should be installed on the system in order to translate Unicorn in this language.");
					messages.add(new Message(Message.INFO, "$message_unavailable_requested_language", null, "locale not installed"));
				}
				else 
					messages.add(new Message(Message.INFO, "$message_unavailable_requested_language", null, locale.getDisplayLanguage(locale)));
			} else {
				messages.add(new Message(Message.INFO, "$message_invalid_requested_language", null, langParameter));
			}
		} else if (!Framework.getLanguageProperties().containsKey(req.getLocale().getLanguage()) && Property.get("SHOW_LANGUAGE_UNAVAILABLE_MESSAGE").equals("true"))
			messages.add(new Message(Message.INFO, "$message_unavailable_language", null, req.getLocale().getDisplayLanguage(req.getLocale())));
		else if (!Language.isComplete(lang))
			messages.add(new Message(Message.INFO, "$message_incomplete_language"));
		
		return lang;
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
			Message mess = new Message(Message.WARNING, "$message_no_task", null, Framework.getDefaultTask().getLongName(messages.getLang()));
			messages.add(mess);
		} else if (!Framework.mapOfTask.containsKey(taskParameter)) {
			Message mess = new Message(Message.WARNING, "$message_unknown_task", null, taskParameter, Framework.getDefaultTask().getLongName(messages.getLang()));
			messages.add(mess);
		}
		
		return task;
	}
	
}
