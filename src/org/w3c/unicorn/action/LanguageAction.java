package org.w3c.unicorn.action;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
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

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.output.AttachmentOutputFormater;
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
			if (Framework.getLanguageProperties().containsKey(langParameter)) {
				messages.add(new Message(Message.INFO, "This translation lacks " + (defaultProperties.size() - languageProperties.get(langParameter).size()) + " properties. Help us to improve it."));
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
	    
	    //resp.getWriter().println(new String(req.getParameter("ja_universal_checker").getBytes("UTF-8"), "UTF-8"));
	    
	    /*File file = new File(Property.get("UPLOADED_FILES_REPOSITORY") + "/test.txt");
	    file.createNewFile();
	    FileOutputStream fileStream = new FileOutputStream(file);
	    OutputStreamWriter os = new OutputStreamWriter(fileStream, "UTF-8");
	    os.write(req.getParameter("ja_universal_checker"));
	    os.close();*/
		
		String languageParameter = req.getParameter("translation_language");
		Map<String, Object> contextObjects = new Hashtable<String, Object>();

		if (languageParameter == null) {
			doGet(req, resp);
			return;
		} else {
			Properties langProps = languageProperties.get(languageParameter);
			if (langProps == null) {
				langProps = createProperties(languageParameter);
				if (langProps == null) {
					doGet(req, resp);
					return;
				}
			}
			
			StringBuilder changeLog = new StringBuilder();
			
			for (Object obj : req.getParameterMap().keySet()) {
				String paramKey = (String) obj;
				String key;
				if (!paramKey.startsWith(languageParameter + "_"))
					continue;
				else
					key = paramKey.replace(languageParameter + "_", "");
				
				if (!req.getParameter(paramKey).equals("") && !req.getParameter(paramKey).equals(langProps.getProperty(key))) {
					changeLog.append(key + ":\n");
					changeLog.append("\t + " + req.getParameter(paramKey) + "\n");
					if (langProps.getProperty(key) != null)
						changeLog.append("\t - " + langProps.getProperty(key) + "\n");
					
					langProps.put(key, req.getParameter(paramKey));
				}
				
				
			}
			
			
			
			//contextObjects.put("changeLog", changeLog);
			
			//Writer os = new CharArrayWriter();
		
			
			//langProps.store(new OutputStreamWriter(new ByteArrayOutputStream(), "UTF-8"), "");
			
			//contextObjects.put("properties", os.toString());
			
			MessageList messages = new MessageList();
			messages.add(new Message(Message.INFO, "Thank you for your submission."));
			req.setAttribute("messages", messages);
			if (req.getParameterMap().containsKey("ucn_lang"))
				req.getParameterMap().remove("ucn_lang");
			doGet(req, resp);
			// From now on the response is committed, careful 
			
			String[] recipients = {"thomas.gambet@orange.fr"};
			
			OutputFormater mainOutputFormater = new SimpleOutputFormater("language.mail", Property.get("DEFAULT_LANGUAGE"), "text/plain");
			OutputFormater attachmentOutputFormater = new AttachmentOutputFormater("language.properties", Property.get("DEFAULT_LANGUAGE"), "text/plain", "test.txt");
			
			List<OutputFormater> outputFormaters = new ArrayList<OutputFormater>();
			outputFormaters.add(mainOutputFormater);
			//outputFormaters.add(attachmentOutputFormater);
			
			Mail mailer = new Mail();
			try {
				mailer.sendMail(recipients, "test subject", outputFormaters, contextObjects);
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
		if (locale == null)
			return null;
		props.put("lang", locale.getLanguage());
		props.put("language", locale.getDisplayLanguage(locale));
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

}
