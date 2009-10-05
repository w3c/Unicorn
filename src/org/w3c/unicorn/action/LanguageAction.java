package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;

/**
 * Servlet implementation class LanguageServlet
 */
public class LanguageAction extends Action {
	
	private static final long serialVersionUID = 1L;
	
	private static TreeMap<String, Properties> languageProperties;
	
	private static Properties defaultProperties;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.init();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
		
		String defaultLang = Property.get("DEFAULT_LANGUAGE");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		resp.setContentType("text/html; charset=UTF-8");
		
		VelocityContext velocityContext = new VelocityContext(Language.getContext(defaultLang));
		velocityContext.put("queryString", "./");
		velocityContext.put("messages", messages);
		velocityContext.put("baseUri", "./");
		Hashtable<String, String> languages = new Hashtable<String, String>();
		
		
		
		languages.put(defaultLang, defaultProperties.getProperty("language"));
		velocityContext.put("languages", languages);
		languageProperties.remove(defaultLang);
		velocityContext.put("languageProps", languageProperties);
		velocityContext.put("defaultProps", defaultProperties);
		
		PrintWriter writer = resp.getWriter();
		Templates.write("language.vm", velocityContext, writer);
		//writer.println(languageProperties);
		//writer.println(languages);
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public static void addLanguageProperties(Properties props) {
		languageProperties.put(props.getProperty("lang"), (Properties) props.clone());
	}

	public static TreeMap<String, Properties> getLanguageProperties() {
		return languageProperties;
	}

	public static void setLanguageProperties(TreeMap<String, Properties> languageProperties) {
		LanguageAction.languageProperties = languageProperties;
	}

	public static Properties getDefaultProperties() {
		return defaultProperties;
	}

	public static void setDefaultProperties(Properties defaultProperties) {
		LanguageAction.defaultProperties = defaultProperties;
	}
	
	

}