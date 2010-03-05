// $Id: LanguageAction.java,v 1.20 2010-03-05 13:48:42 tgambet Exp $
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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
import org.w3c.unicorn.util.UCNProperties;

/**
 * Servlet implementation class LanguageServlet
 */
public class LanguageAction extends Action {
	
	private static final long serialVersionUID = 1L;
	
	private static Log logger = LogFactory.getLog(LanguageAction.class);
	
	private static TreeMap<String, Properties> languageProperties;
	
	private static TreeMap<String, Properties> metadataProperties;
	
	private static TreeMap<String, String> defaultProperties = new TreeMap<String, String>();
	
	private static TreeMap<String, String> defaultMetadatas = new TreeMap<String, String>();
	
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
		velocityContext.put("native_lang", req.getLocale().getLanguage());
		velocityContext.put("translator_name", req.getAttribute("translator_name"));
		velocityContext.put("translator_mail", req.getAttribute("translator_mail"));
		velocityContext.put("translator_comments", req.getAttribute("translator_comments"));
		
		Hashtable<String, String> languages = new Hashtable<String, String>();
		languages.put(defaultLang, defaultProperties.get("language"));
		velocityContext.put("languages", languages);
		
		velocityContext.put("languageProps", languageProperties);
		velocityContext.put("defaultProps", defaultProperties);
		
		velocityContext.put("metadataProps", metadataProperties);
		velocityContext.put("defaultMetadata", defaultMetadatas);
		
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
				if (req.getAttribute("submitted_props") != null) {
					Properties submittedProps = (Properties) req.getAttribute("submitted_props");
					velocityContext.put("prop", submittedProps);
				} else {
					velocityContext.put("prop", languageProperties.get(langParameter));
					velocityContext.put("metadatas", metadataProperties.get(langParameter));
				}
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
					velocityContext.put("metadatas", createProperties(langParameter));
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
			UCNProperties langProps;
			UCNProperties metaProps;
			if (languageProperties.get(languageParameter) == null || metadataProperties.get(languageParameter) == null) {
				if (languageProperties.get(languageParameter) == null) {
					langProps = createProperties(languageParameter);
					contextObjects.put("new_interface_translation", true);
				} else
					langProps = (UCNProperties) languageProperties.get(languageParameter).clone();
				if (metadataProperties.get(languageParameter) == null) {
					metaProps = createProperties(languageParameter);
					contextObjects.put("new_tasklist_translation", true);
				} else
					metaProps = (UCNProperties) metadataProperties.get(languageParameter).clone();
				
				if (langProps == null || metaProps == null) {
					doGet(req, resp);
					return;
				}
			} else {
				langProps = (UCNProperties) languageProperties.get(languageParameter).clone();
				metaProps = (UCNProperties) metadataProperties.get(languageParameter).clone();
			}
			
			StringBuilder interfaceChangeLog = new StringBuilder();
			boolean interfaceChanged = false;
			for (Object obj : req.getParameterMap().keySet()) {
				String paramKey = (String) obj;
				String key;
				if (!paramKey.startsWith(languageParameter + "_"))
					continue;
				else
					key = paramKey.replace(languageParameter + "_", "");
				
				if (!req.getParameter(paramKey).equals("") && !req.getParameter(paramKey).equals(langProps.getProperty(key))) {
					interfaceChanged = true;
					
					interfaceChangeLog.append("\n" + key + ":\n");
					interfaceChangeLog.append("\t + " + req.getParameter(paramKey) + "\n");
					if (langProps.getProperty(key) != null)
						interfaceChangeLog.append("\t - " + langProps.getProperty(key) + "\n");
					
					langProps.put(key, req.getParameter(paramKey));
				}
			}
			
			StringBuilder tasklistChangeLog = new StringBuilder();
			boolean tasklistChanged = false;
			for (Object obj : req.getParameterMap().keySet()) {
				String paramKey = (String) obj;
				String key;
				if (!paramKey.startsWith("metadata_" + languageParameter + "_"))
					continue;
				else
					key = paramKey.replace("metadata_" + languageParameter + "_", "");
				
				if (!req.getParameter(paramKey).equals("") && !req.getParameter(paramKey).equals(metaProps.getProperty(key))) {
					tasklistChanged = true;
					
					tasklistChangeLog.append("\n" + key + ":\n");
					tasklistChangeLog.append("\t + " + req.getParameter(paramKey) + "\n");
					if (metaProps.getProperty(key) != null)
						tasklistChangeLog.append("\t - " + metaProps.getProperty(key) + "\n");
					
					metaProps.put(key, req.getParameter(paramKey));
				}
			}
			
			if (!interfaceChanged || !tasklistChanged) {
				MessageList messages = new MessageList();
				messages.add(new Message(Message.WARNING, "You haven't made any changes."));
				req.setAttribute("messages", messages);
				req.setAttribute("translator_name", req.getParameter("translator_name"));
				req.setAttribute("translator_mail", req.getParameter("translator_mail"));
				req.setAttribute("translator_comments", req.getParameter("translator_comments"));
				doGet(req, resp);
				return;
			}
			
			if ("".equals(req.getParameter("translator_name")) || "".equals(req.getParameter("translator_mail"))) {
				MessageList messages = new MessageList();
				messages.add(new Message(Message.WARNING, "Please enter your name and your email address so we can contact you."));
				req.setAttribute("messages", messages);
				req.setAttribute("submitted_props", langProps);
				req.setAttribute("translator_name", req.getParameter("translator_name"));
				req.setAttribute("translator_mail", req.getParameter("translator_mail"));
				req.setAttribute("translator_comments", req.getParameter("translator_comments"));
				doGet(req, resp);
				return;
			}
			
			try {
				InternetAddress ia = new InternetAddress(req.getParameter("translator_mail"));
				ia.validate();
			} catch (AddressException e) {
				MessageList messages = new MessageList();
				messages.add(new Message(Message.WARNING, "The email address you entered is invalid."));
				req.setAttribute("messages", messages);
				req.setAttribute("submitted_props", langProps);
				req.setAttribute("translator_name", req.getParameter("translator_name"));
				req.setAttribute("translator_mail", req.getParameter("translator_mail"));
				req.setAttribute("translator_comments", req.getParameter("translator_comments"));
				doGet(req, resp);
				return;
			}
			
			contextObjects.put("translator_name", req.getParameter("translator_name"));
			contextObjects.put("translator_mail", req.getParameter("translator_mail"));
			contextObjects.put("translator_comments", req.getParameter("translator_comments"));
			contextObjects.put("language", Language.getLocale(languageParameter).getDisplayLanguage(Locale.ENGLISH));
			contextObjects.put("interfaceChangeLog", interfaceChangeLog);
			contextObjects.put("tasklistChangeLog", tasklistChangeLog);
			
			langProps.remove("lang");
			langProps.remove("language");
			metaProps.remove("lang");
			metaProps.remove("language");
			
			if (interfaceChanged) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
				langProps.store(osw, "Submitted by " + req.getParameter("translator_name") + " <" + req.getParameter("translator_mail") + ">");
				osw.close();
				baos.close();
				contextObjects.put("interfaceProperties", baos.toString("UTF-8"));
			}
			
			if (tasklistChanged) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
				metaProps.store(osw, "Submitted by " + req.getParameter("translator_name") + " <" + req.getParameter("translator_mail") + ">");
				osw.close();
				baos.close();
				contextObjects.put("tasklistProperties", baos.toString("UTF-8"));
			}
			
			MessageList messages = new MessageList();
			messages.add(new Message(Message.INFO, "Thank you for your submission."));
			req.setAttribute("messages", messages);
			req.setAttribute("submitted", true);
			doGet(req, resp);
			
			String[] recipients = {Property.getProps("mail.properties").getProperty("unicorn.mail.language.to"), req.getParameter("translator_mail")};
			String subject = "Unicorn - Translation in " + contextObjects.get("language") + " (submitted by " + req.getParameter("translator_name") + ")";
			
			List<OutputFormater> outputFormaters = new ArrayList<OutputFormater>();
			
			OutputFormater mainOutputFormater = new SimpleOutputFormater("language.mail", Property.get("DEFAULT_LANGUAGE"), "text/plain");
			outputFormaters.add(mainOutputFormater);
			
			OutputFormater fileOutputFormater;
			if (interfaceChanged) {
				fileOutputFormater = new FileOutputFormater("language.properties", Property.get("DEFAULT_LANGUAGE"), "text/plain", languageParameter + ".properties");
				outputFormaters.add(fileOutputFormater);
			}
			
			OutputFormater fileOutputFormater2;
			if (tasklistChanged) {
				fileOutputFormater2 = new FileOutputFormater("tasklist.properties", Property.get("DEFAULT_LANGUAGE"), "text/plain", languageParameter + ".tasklist.properties");
				outputFormaters.add(fileOutputFormater2);
			}
			
			Mail mailer = new Mail();
			try {
				mailer.sendMail(recipients, subject, outputFormaters, contextObjects);
			} catch (UnicornException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private UCNProperties createProperties(String langParameter) {
		UCNProperties props = new UCNProperties();
		Locale locale = Language.getLocale(langParameter);
		if (locale == null)
			return null;
		props.put("lang", locale.getLanguage());
		props.put("language", StringUtils.capitalize(locale.getDisplayLanguage(locale)));
		return props;
	}

	public static void addLanguageProperties(Properties props) {
		if (!props.getProperty("lang").equals(Property.get("DEFAULT_LANGUAGE")))
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

	public static TreeMap<String, Properties> getMetadataProperties() {
		return metadataProperties;
	}

	public static void setMetadataProperties(
			TreeMap<String, Properties> metadataProperties) {
		LanguageAction.metadataProperties = metadataProperties;
	}

	public static TreeMap<String, String> getDefaultMetadatas() {
		return defaultMetadatas;
	}

	public static void setDefaultMetadatas(TreeMap<String, String> defaultMetadatas) {
		LanguageAction.defaultMetadatas = defaultMetadatas;
	}

	public static void setDefaultMetadatas(Properties defaultProperties) {
		for (Object obj : defaultProperties.keySet()) {
			String key = (String) obj;
			LanguageAction.defaultMetadatas.put(key, defaultProperties.getProperty(key));
		}
	}
	
	public static void addMetadatasProperties(String lang, Properties props) {
		metadataProperties.put(lang, (Properties) props.clone());
	}
}
