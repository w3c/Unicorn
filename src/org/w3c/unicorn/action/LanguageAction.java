package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.output.SimpleOutputFormater;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.MessageList;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;
import org.w3c.unicorn.util.Mail;

/**
 * Servlet implementation class LanguageServlet
 */
public class LanguageAction extends Action {
	
	private static final long serialVersionUID = 1L;
	
	private static TreeMap<String, Properties> languageProperties;
	
	private static TreeMap<String, String> defaultProperties = new TreeMap<String, String>();

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
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		String defaultLang = Property.get("DEFAULT_LANGUAGE");
		MessageList messages;
		
		if (req.getAttribute("messages") != null && req.getAttribute("messages") instanceof MessageList)
			messages = (MessageList) req.getAttribute("messages");
		else
			messages = new MessageList();
		
		VelocityContext velocityContext = new VelocityContext(Language.getContext(defaultLang));
		velocityContext.put("queryString", "./");
		velocityContext.put("messages", messages);
		velocityContext.put("baseUri", "./");
		
		Hashtable<String, String> languages = new Hashtable<String, String>();
		languages.put(defaultLang, defaultProperties.get("language"));
		velocityContext.put("languages", languages);
		
		languageProperties.remove(defaultLang);
		velocityContext.put("languageProps", languageProperties);
		velocityContext.put("defaultProps", defaultProperties);
		
		PrintWriter writer = resp.getWriter();
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		if (langParameter == null)
			Templates.write("language.vm", velocityContext, writer);
		else {
			if (Framework.getLanguageProperties().containsKey(langParameter))
				velocityContext.put("prop", languageProperties.get(langParameter));
			else if (Language.isISOLanguageCode(langParameter)) {
				messages.add(new Message(Message.INFO, "Thank you for translating Unicorn in " 
						+ Language.getLocale(langParameter).getDisplayLanguage(Locale.ENGLISH) 
						+ ". You can submit a full or a partial translation."));
				velocityContext.put("prop", createProperties(langParameter));
			} else {
				messages.add(new Message(Message.ERROR, "$message_invalid_requested_language", null, langParameter));
				Templates.write("language.vm", velocityContext, writer);
				writer.close(); return;
			}
			Templates.write("language.form.vm", velocityContext, writer);
			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.init();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		if (req.getParameter("translation_language") == null) {
			doGet(req, resp);
			return;
		} else {
			MessageList messages = new MessageList();
			messages.add(new Message(Message.INFO, "Thank you for your submition."));
			req.setAttribute("messages", messages);
			doGet(req, resp);
			// From now on the response is committed, careful 
			
			String[] recipients = {"thomas.gambet@gmail.com"};
			
			Mail mailer = new Mail();
			try {
				mailer.sendMail(recipients, "test subject", new SimpleOutputFormater("text", Property.get("DEFAULT_LANGUAGE")), null);
			} catch (UnicornException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//checkParameters(req);
		}
		
	}

	private Properties createProperties(String langParameter) {
		Properties props = new Properties();
		Locale locale = Language.getLocale(langParameter);
		props.put("lang", locale.getLanguage());
		props.put("language", locale.getDisplayLanguage(Locale.ENGLISH));
		return props;
	}
	
	private void checkParameters(HttpServletRequest req) {
		
		/*String languageParameter = req.getParameter("translation_language");
		
		for (Object obj : req.getParameterMap().keySet()) {
			
			String paramKey = (String) obj;
			String key;
			
			if (!paramKey.startsWith(languageParameter + "_"))
				continue;
			else
				key = paramKey.replace(languageParameter + "_", "");
			
			Properties langProps = languageProperties.get(languageParameter);
			
			if (langProps.getProperty(key) == null || !req.getParameter(paramKey).equals(langProps.getProperty(key))) {
				
			}
			
		}*/
		
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

	public static TreeMap<String, String> getDefaultProperties() {
		return defaultProperties;
	}

	public static void setDefaultProperties(TreeMap<String, String> defaultProperties) {
		LanguageAction.defaultProperties = defaultProperties;
	}
	
	public static void setDefaultProperties(Properties defaultProperties) {
		for (Object obj : defaultProperties.keySet()) {
			String key = (String) obj;
			LanguageAction.defaultProperties.put(key, defaultProperties.getProperty(key));
		}
	}

}
