// $Id: LanguageAction.java,v 1.14 2009-10-12 16:04:42 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.output.FileOutputFormater;
import org.w3c.unicorn.output.OutputFormater;
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
	
	private static Log logger = LogFactory.getLog(LanguageAction.class);
	
	private static TreeMap<String, Properties> languageProperties;
	
	private static TreeMap<String, String> defaultProperties = new TreeMap<String, String>();
	
	private static TreeMap<String, String> availableLocales;

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
		velocityContext.put("availableLocales", availableLocales);
		
		
		Hashtable<String, String> languages = new Hashtable<String, String>();
		languages.put(defaultLang, defaultProperties.get("language"));
		velocityContext.put("languages", languages);
		
		languageProperties.remove(defaultLang);
		velocityContext.put("languageProps", languageProperties);
		velocityContext.put("defaultProps", defaultProperties);
		
		PrintWriter writer = resp.getWriter();
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		if (langParameter == null || req.getAttribute("submitted") != null)
			Templates.write("language.vm", velocityContext, writer);
		else {
			if (Framework.getLanguageProperties().containsKey(langParameter)) {
				if (langParameter.equals(Property.get("DEFAULT_LANGUAGE"))) {
					messages.add(new Message(Message.INFO, "You cannot edit the default language"));
					Templates.write("language.vm", velocityContext, writer);
					writer.close(); return;
				} else {
					int missings = defaultProperties.size() - languageProperties.get(langParameter).size();
					
					if (missings > 0)
						messages.add(new Message(Message.INFO, "This translation lacks " + (defaultProperties.size() - languageProperties.get(langParameter).size()) + " properties. Help us to improve it."));
					else 
						messages.add(new Message(Message.INFO, "This translation is complete but you can help us to improve it if needed."));
				}
				velocityContext.put("prop", languageProperties.get(langParameter));
			} else if (Language.isISOLanguageCode(langParameter)) {
				Locale locale = Language.getLocale(langParameter);
				if (locale == null) {
					messages.add(new Message(Message.WARNING, "The language code you requested is valid but the coresponding locale seems not to be installed on our system. Please make a request on public-qa-dev@w3.org."));
					Templates.write("language.vm", velocityContext, writer);
					writer.close(); return;
				} else {
					messages.add(new Message(Message.INFO, "Thank you for translating Unicorn in " 
							+ Language.getLocale(langParameter).getDisplayLanguage(Locale.ENGLISH) 
							+ ". You can submit a full or a partial translation."));
					velocityContext.put("prop", createProperties(langParameter));
				}
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
		
		String languageParameter = req.getParameter("translation_language");
		Map<String, Object> contextObjects = new Hashtable<String, Object>();

		if (languageParameter == null) {
			doGet(req, resp);
			return;
		} else {
			Properties langProps;
			if (languageProperties.get(languageParameter) == null) {
				langProps = createProperties(languageParameter);
				contextObjects.put("new_translation", true);
				if (langProps == null) {
					doGet(req, resp);
					return;
				}
			} else
				langProps = (Properties) languageProperties.get(languageParameter).clone();
			
			StringBuilder changeLog = new StringBuilder();
			boolean changed = false;
			for (Object obj : req.getParameterMap().keySet()) {
				String paramKey = (String) obj;
				String key;
				if (!paramKey.startsWith(languageParameter + "_"))
					continue;
				else
					key = paramKey.replace(languageParameter + "_", "");
				
				if (!req.getParameter(paramKey).equals("") && !req.getParameter(paramKey).equals(langProps.getProperty(key))) {
					changed = true;
					
					changeLog.append("\n" + key + ":\n");
					changeLog.append("\t + " + req.getParameter(paramKey) + "\n");
					if (langProps.getProperty(key) != null)
						changeLog.append("\t - " + langProps.getProperty(key) + "\n");
					
					langProps.put(key, req.getParameter(paramKey));
				}
			}
			
			if (!changed) {
				MessageList messages = new MessageList();
				messages.add(new Message(Message.ERROR, "You haven't made any changes."));
				req.setAttribute("messages", messages);
				doGet(req, resp);
				return;
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
			langProps.store(osw, "Submitted by " + req.getParameter("translator_name") + " <" + req.getParameter("translator_mail") + ">");
			osw.close();
			baos.close();
			contextObjects.put("properties", baos.toString("UTF-8"));
			contextObjects.put("changeLog", changeLog);
			
			if (!"".equals(req.getParameter("translator_name")))
				contextObjects.put("translator_name", req.getParameter("translator_name"));
			else
				contextObjects.put("translator_name", "Anonymous");
			if (!"".equals(req.getParameter("translator_mail")))
				contextObjects.put("translator_mail", req.getParameter("translator_mail"));
			else 
				contextObjects.put("translator_mail", "Not specified");
			contextObjects.put("translator_comments", req.getParameter("translator_comments"));
			contextObjects.put("language", Language.getLocale(languageParameter).getDisplayLanguage(Locale.ENGLISH));
			
			MessageList messages = new MessageList();
			messages.add(new Message(Message.INFO, "Thank you for your submission."));
			req.setAttribute("messages", messages);
			req.setAttribute("submitted", true);
			doGet(req, resp);
			
			String[] recipients = {Property.getProps("mail.properties").getProperty("unicorn.mail.language.to"), 
					req.getParameter("translator_mail")};
			String subject;
			if (!"".equals(req.getParameter("translator_name")))
				subject = "Unicorn - Translation in " + contextObjects.get("language") + " (submitted by " + req.getParameter("translator_name") + ")";
			else
				subject = "Unicorn - Translation in " + contextObjects.get("language") + " (anonymous submission)";
			
			OutputFormater mainOutputFormater = new SimpleOutputFormater("language.mail", Property.get("DEFAULT_LANGUAGE"), "text/plain");
			OutputFormater fileOutputFormater = new FileOutputFormater("language.properties", Property.get("DEFAULT_LANGUAGE"), "text/plain", languageParameter + ".properties");
			
			List<OutputFormater> outputFormaters = new ArrayList<OutputFormater>();
			outputFormaters.add(mainOutputFormater);
			outputFormaters.add(fileOutputFormater);
			
			Mail mailer = new Mail();
			try {
				mailer.sendMail(recipients, subject, outputFormaters, contextObjects);
			} catch (UnicornException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
		
	}

	private Properties createProperties(String langParameter) {
		Properties props = new Properties();
		Locale locale = Language.getLocale(langParameter);
		if (locale == null)
			return null;
		props.put("lang", locale.getLanguage());
		props.put("language", StringUtils.capitalize(locale.getDisplayLanguage(locale)));
		return props;
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

	public static TreeMap<String, String> getAvailableLocales() {
		return availableLocales;
	}

	public static void setAvailableLocales(TreeMap<String, String> availableLocales) {
		LanguageAction.availableLocales = availableLocales;
	}

}
