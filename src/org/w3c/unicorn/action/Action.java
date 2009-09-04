package org.w3c.unicorn.action;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

public abstract class Action extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
			return;
		}
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		if (langParameter == null || !Framework.getLanguageProperties().containsKey(langParameter))
			langParameter = Language.negociate(req.getLocales());
		
		if (!langParameter.equals(req.getLocale().getLanguage()))
			messages.add(new Message(Message.Level.INFO, "$message_unavailable_language (" + req.getLocale().getDisplayLanguage(req.getLocale()) + "). $message_translation", null));
		
		if (!Language.isComplete(langParameter))
			messages.add(new Message(Message.Level.INFO, "$message_incomplete_language. $message_translation", null));
		
		
		
		
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
		return queryString;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	
	

}
