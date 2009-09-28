// $Id: MailOutputModule.java,v 1.10 2009-09-28 16:41:18 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.Properties;
import org.w3c.unicorn.util.Message;
import javax.mail.*;
import javax.mail.internet.*;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.UnicornAuthenticator;

/**
 * This module allows to send the response by mail.
 * 
 * @author Thomas GAMBET
 */
public class MailOutputModule extends OutputModule {
	
	private OutputFormater firstOutputFormater;
	
	private OutputFormater mailOutputFormater;
	
	private String mimeType;
	
	private String recipient;
	
	public MailOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		super(mapOfOutputParameters, mapOfSpecificParameters);
		
		recipient = mapOfSpecificParameters.get("email");
		mimeType = mapOfOutputParameters.get("mimetype");
		
		String format = mapOfOutputParameters.get("format");
		String lang = mapOfOutputParameters.get("lang"); 
		
		firstOutputFormater = OutputFactory.createOutputFormater(format, lang, mimeType);
		
		if (mapOfSpecificParameters.get("format") != null)
			format = mapOfSpecificParameters.get("format");
		if (mapOfSpecificParameters.get("mimetype") != null)
			mimeType = mapOfSpecificParameters.get("mimetype");
		
		mailOutputFormater = OutputFactory.createOutputFormater(format, lang, mimeType);
	}
	
	@SuppressWarnings("unchecked")
	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) throws UnicornException {
		//mapOfStringObject.put("baseUri", "http://qa-dev.w3.org/unicorn/");
		
		ArrayList<Message> messages = ((ArrayList<Message>) mapOfStringObject.get("messages"));
		Message pendingMess = new Message(Message.Level.INFO, "Le rapport est en cours d'envoi à l'adresse: " + recipient);
		
		if (recipient == null) {
			throw new UnicornException(new Message(Message.Level.ERROR, "Aucune adresse email spécifiée. Rajoutez &opt_email=votre.adresse@mail.com à la requête."));
		} else {
			messages.add(pendingMess);
		}
		displayOnIndex(mapOfStringObject, aWriter);
		messages.remove(pendingMess);
	}
	
	@SuppressWarnings("unchecked")
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		
		
		// TODO http://java.sun.com/developer/EJTechTips/2004/tt0625.html#1 for multipart messages
		
		
		if (recipient == null)
			return;
		
		ArrayList<Message> messages = ((ArrayList<Message>) mapOfStringObject.get("messages"));
		messages.add(new Message(Message.Level.INFO, "Observation effectuée le " + new Date()));
		
		try {
			mapOfStringObject.put("baseUri", "http://qa-dev.w3.org:8001/unicorn/");
			
			Properties mailProps = Property.getProps("mail.properties");
			Authenticator auth = new UnicornAuthenticator(mailProps.getProperty("unicorn.mail.username"), mailProps.getProperty("unicorn.mail.password"));
			Session session = Session.getDefaultInstance(mailProps, auth);
		    
			boolean debug = false;
			if ("true".equals(mailProps.getProperty("unicorn.mail.debug")))
				debug = true;
			
			session.setDebug(debug);
			javax.mail.Message msg = new MimeMessage(session);
		    
		    InternetAddress addressFrom = new InternetAddress(mailProps.getProperty("unicorn.mail.from"), "Unicorn");
			msg.setFrom(addressFrom);
			msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipient));
			
			// Setting the Subject and Content Type
			UnicornCall uniCall = (UnicornCall) mapOfStringObject.get("unicorncall");
			boolean passed = uniCall.isPassed();
			
			String subject = "[Unicorn] ";
			if (passed)
				subject += "SUCCEEDED: ";
			else 
				subject += "FAILED: ";
			subject += "Task \"" + uniCall.getTask().getLongName(outputParameters.get("lang")) + "\" for \"" + uniCall.getDocumentName() + "\"";
			
			msg.setSubject(subject);
			
			CharArrayWriter writer = new CharArrayWriter();
			mailOutputFormater.produceOutput(mapOfStringObject, writer);
			writer.close();
			msg.setContent(writer.toString(), mimeType);
			
			Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		if (outputParameters.get("mimetype").equals("text/html")) {
			displayOnIndex(mapOfStringObject, aWriter);
			return;
		}
		firstOutputFormater.produceError(mapOfStringObject, aWriter);
	}

}