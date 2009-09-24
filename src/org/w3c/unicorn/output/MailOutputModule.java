// $Id: MailOutputModule.java,v 1.8 2009-09-24 15:33:03 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.UnicornAuthenticator;

/**
 * This module allow to generate output in mail format.
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
		
		mailOutputFormater = OutputFactory.createOutputFormater(format, lang, mimeType);
	}
	
	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) {
		//mapOfStringObject.put("baseUri", "http://qa-dev.w3.org/unicorn/");
		
		((ArrayList<org.w3c.unicorn.util.Message>) mapOfStringObject.get("messages")). add(new org.w3c.unicorn.util.Message(org.w3c.unicorn.util.Message.Level.INFO, "Le rapport est en cours d'envoi à l'adresse: " + recipient));
		displayOnIndex(mapOfStringObject, aWriter);
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		
		try {
			
			mapOfStringObject.put("baseUri", "http://qa-dev.w3.org:8001/unicorn/");
			
			Properties mailProps = Property.getProps("mail.properties");
			Authenticator auth = new UnicornAuthenticator(mailProps.getProperty("unicorn.mail.username"), mailProps.getProperty("unicorn.mail.password"));
			Session session = Session.getDefaultInstance(mailProps, auth);
		    
			boolean debug = false;
			if ("true".equals(mailProps.getProperty("unicorn.mail.debug")))
				debug = true;
			
			session.setDebug(debug);
		    Message msg = new MimeMessage(session);
		    
		    InternetAddress addressFrom = new InternetAddress(mailProps.getProperty("unicorn.mail.from"), "Unicorn");
			msg.setFrom(addressFrom);
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			
			// Setting the Subject and Content Type
			UnicornCall uniCall = (UnicornCall) mapOfStringObject.get("unicorncall");
			boolean passed = ((UnicornCall) mapOfStringObject.get("unicorncall")).isPassed();
			
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
		firstOutputFormater.produceError(mapOfStringObject, aWriter);
	}

}