// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ibm.icu.util.ULocale;

public class LanguageAction extends Action {
	
	private static final long serialVersionUID = 1L;
	
	private static Log logger = LogFactory.getLog(LanguageAction.class);
	
	private static Hashtable<ULocale, UCNProperties> languageProperties = new Hashtable<ULocale, UCNProperties>();
	
	private static Hashtable<ULocale, UCNProperties> metadataProperties = new Hashtable<ULocale, UCNProperties>();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.initUnicorn();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		MessageList messages;
		if (req.getAttribute("messages") != null && req.getAttribute("messages") instanceof MessageList)
			messages = (MessageList) req.getAttribute("messages");
		else
			messages = new MessageList();
		
		VelocityContext velocityContext = new VelocityContext(Language.getContext(Language.getDefaultLocale()));
    if("false".equals(Property.get("ENABLE_TRANSLATION_CONTRIBS"))) {
        velocityContext.put("enable_translation_contribs", false);
    }
    else {
        velocityContext.put("enable_translation_contribs", true);
    }
		velocityContext.put("queryString", "./");
		velocityContext.put("messages", messages);
		velocityContext.put("baseUri", "./");
		velocityContext.put("availableLocales", Language.getAvailableLocales());
		if (req.getHeader("Accept-Language") != null)
			velocityContext.put("native_lang", Language.getAvailableLocale(req.getHeader("Accept-Language")).getName());
		else
			velocityContext.put("native_lang", Language.getDefaultLocale().getName());
		velocityContext.put("translator_name", req.getAttribute("translator_name"));
		velocityContext.put("translator_mail", req.getAttribute("translator_mail"));
		velocityContext.put("translator_comments", req.getAttribute("translator_comments"));
		velocityContext.put("uiLocales", Language.getUiLocales());
		
		ArrayList<ULocale> languages = new ArrayList<ULocale>();
		languages.add(Language.getDefaultLocale());
		velocityContext.put("languages", languages);
		
		UCNProperties defaultProperties = languageProperties.get(Language.getDefaultLocale());
		UCNProperties defaultMetadatas = metadataProperties.get(Language.getDefaultLocale());
		
		velocityContext.put("languageProps", languageProperties);
		velocityContext.put("defaultProps", defaultProperties);
		
		velocityContext.put("metadataProps", metadataProperties);
		velocityContext.put("defaultMetadata", defaultMetadatas);
		
		PrintWriter writer = resp.getWriter();
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		
		if (langParameter == null || req.getAttribute("submitted") != null)
			Templates.write("language.vm", velocityContext, writer);
		else {
      if ("false".equals(Property.get("ENABLE_TRANSLATION_CONTRIBS"))) {
        resp.sendError(404, "Translation contributions are disabled on this server");
        return;
      }
			ULocale locale = Language.getAvailableLocale(langParameter);
			ULocale requestedLocale = Language.getLocale(langParameter);			
			if (requestedLocale != locale) {
				messages.add(new Message(Message.INFO, "The requested language \"" + requestedLocale.getDisplayName(requestedLocale) + "\" has been changed to the fallback language \"" +
														locale.getDisplayName(locale) + "\" instead. If you think this is not right please make a request on <a href=\"mailto:public-qa-dev@w3.org\">our public mailing-list</a>."));
			}
			if (Language.getUiLocales().contains(locale)) {
				if (locale.equals(Language.getDefaultLocale())) {
					messages.clear();
					messages.add(new Message(Message.ERROR, "The language you are trying to edit is either the default language or is unknown. " +
															"If it is the latter and you think we are wrong please make a request on <a href=\"mailto:public-qa-dev@w3.org\">our public mailing-list</a>."));
					Templates.write("language.vm", velocityContext, writer);
					writer.close(); return;
				} else {
					int missings = defaultProperties.size() + defaultMetadatas.size() - languageProperties.get(locale).size() - metadataProperties.get(locale).size();
					if (missings > 0)
						messages.add(new Message(Message.INFO, "This translation lacks " + missings + " properties on " + (defaultProperties.size() + defaultMetadatas.size()) + ". Help us to improve it."));
					else 
						messages.add(new Message(Message.INFO, "This translation is complete but you can help us to improve it if needed."));
				}
				if (req.getAttribute("submitted_props") != null) {
					UCNProperties submittedProps = (UCNProperties) req.getAttribute("submitted_props");
					velocityContext.put("prop", submittedProps);
				} else
					velocityContext.put("prop", languageProperties.get(locale));
				if (req.getAttribute("submitted_metas") != null) {
					UCNProperties submittedMetas = (UCNProperties) req.getAttribute("submitted_metas");
					velocityContext.put("metadatas", submittedMetas);
				} else
					velocityContext.put("metadatas", metadataProperties.get(locale));
			} else {
				messages.add(new Message(Message.INFO, "Thank you for translating Unicorn in " 
						+ locale.getDisplayName(Language.getDefaultLocale()) 
						+ ". You can submit a full or a partial translation."));
				velocityContext.put("prop", new UCNProperties());
				velocityContext.put("metadatas", new UCNProperties());
			}
			velocityContext.put("transLocale", locale);
			velocityContext.put("transLocaleDir", Language.getLocaleDirection(locale));
			Templates.write("language.form.vm", velocityContext, writer);
			writer.close();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			Framework.initUnicorn();
			if (!Framework.isUcnInitialized) {
				resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
				return;
			}
		}
    
    if("false".equals(Property.get("ENABLE_TRANSLATION_CONTRIBS"))) {
        resp.sendError(404, "Translation contributions are disabled on this server");
        return;
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
					langProps = new UCNProperties();
					contextObjects.put("new_interface_translation", true);
				} else
					langProps = (UCNProperties) languageProperties.get(languageParameter).clone();
				if (metadataProperties.get(languageParameter) == null) {
					metaProps = new UCNProperties();
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
					key = paramKey.replaceFirst(languageParameter + "_", "");
				
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
			
			if (!interfaceChanged && !tasklistChanged) {
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
				req.setAttribute("submitted_metas", metaProps);
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
				req.setAttribute("submitted_metas", metaProps);
				req.setAttribute("translator_name", req.getParameter("translator_name"));
				req.setAttribute("translator_mail", req.getParameter("translator_mail"));
				req.setAttribute("translator_comments", req.getParameter("translator_comments"));
				doGet(req, resp);
				return;
			}
			
			contextObjects.put("translator_name", req.getParameter("translator_name"));
			contextObjects.put("translator_mail", req.getParameter("translator_mail"));
			contextObjects.put("translator_comments", req.getParameter("translator_comments"));
			contextObjects.put("language", Language.getLocale(languageParameter).getDisplayLanguage(Language.getDefaultLocale()));
			contextObjects.put("interfaceChangeLog", interfaceChangeLog);
			contextObjects.put("tasklistChangeLog", tasklistChangeLog);
			contextObjects.put("interfaceChanged", interfaceChanged);
			contextObjects.put("tasklistChanged", tasklistChanged);
			contextObjects.put("host", Property.get("UNICORN_URL"));
			
			if (interfaceChanged) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
				langProps.store(osw, "Last submission by " + req.getParameter("translator_name"));
				osw.close();
				baos.close();
				contextObjects.put("interfaceProperties", baos.toString("UTF-8"));
			}
			
			if (tasklistChanged) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
				metaProps.store(osw, "Last submission by " + req.getParameter("translator_name"));
				osw.close();
				baos.close();
				contextObjects.put("tasklistProperties", baos.toString("UTF-8"));
			}
			
			MessageList messages = new MessageList();
			messages.add(new Message(Message.INFO, "Thank you for your submission. It has been forwarded to a moderator. You will also receive an email with your translation attached if you'd like to review it."));
			req.setAttribute("messages", messages);
			req.setAttribute("submitted", true);
			doGet(req, resp);
			
			String[] recipients = {Property.getProps("mail.properties").getProperty("unicorn.mail.language.to"), req.getParameter("translator_mail")};
			String subject = "Unicorn - Translation in " + contextObjects.get("language") + " submitted by " + req.getParameter("translator_name");
			
			List<OutputFormater> outputFormaters = new ArrayList<OutputFormater>();
			
			OutputFormater mainOutputFormater = new SimpleOutputFormater("language.mail", Property.get("DEFAULT_LANGUAGE"), "text/plain");
			outputFormaters.add(mainOutputFormater);
			
			OutputFormater fileOutputFormater;
			if (interfaceChanged) {
				fileOutputFormater = new FileOutputFormater("language.properties", Property.get("DEFAULT_LANGUAGE"), "text/plain", languageParameter.replace("_", "-") + ".properties");
				outputFormaters.add(fileOutputFormater);
			}
			
			OutputFormater fileOutputFormater2;
			if (tasklistChanged) {
				fileOutputFormater2 = new FileOutputFormater("tasklist.properties", Property.get("DEFAULT_LANGUAGE"), "text/plain", languageParameter.replace("_", "-") + ".tasklist.properties");
				outputFormaters.add(fileOutputFormater2);
			}
			
			Mail mailer = new Mail();
			try {
				mailer.sendMail(recipients, subject, outputFormaters, contextObjects, false);
			} catch (UnicornException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static void addLanguageProperties(ULocale locale, UCNProperties props) {
		languageProperties.put(locale, (UCNProperties) props.clone());
	}

	public static Hashtable<ULocale, UCNProperties> getLanguageProperties() {
		return languageProperties;
	}

	public static void setLanguageProperties(Hashtable<ULocale, UCNProperties> languageProperties) {
		LanguageAction.languageProperties = languageProperties;
	}
	
	public static Hashtable<ULocale, UCNProperties> getMetadataProperties() {
		return metadataProperties;
	}

	public static void setMetadataProperties(Hashtable<ULocale, UCNProperties> metadataProperties) {
		LanguageAction.metadataProperties = metadataProperties;
	}
	
	public static void addMetadatasProperties(ULocale locale, UCNProperties props) {
		metadataProperties.put(locale, (UCNProperties) props.clone());
	}
	
}
