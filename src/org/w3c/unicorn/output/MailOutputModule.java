// $Id: MailOutputModule.java,v 1.3 2009-09-23 13:02:05 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.*; 
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.mail.UnicornAuthenticator;

/**
 * This module allow to generate output in mail format.
 * 
 * @author Thomas GAMBET
 */
public class MailOutputModule implements OutputModule {
	
	private OutputFormater aOutputFormater;
	
	private OutputFormater mailOutputFormater;
	
	private Map<String, String> mapOfOutputParameters;
	
	private Map<String, String> mapOfSpecificParameters;
	
	private String recipient;
	
	public MailOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		this.mapOfOutputParameters = mapOfOutputParameters;
		this.mapOfSpecificParameters = mapOfSpecificParameters;
		this.recipient = mapOfSpecificParameters.get("email");
			
		String format = mapOfOutputParameters.get("format");
		String lang = mapOfOutputParameters.get("lang"); 
		String mimeType = mapOfOutputParameters.get("mimetype");
		
		aOutputFormater = OutputFactory.createOutputFormater(format, lang, mimeType);
		
		if (mapOfSpecificParameters.get("format") != null)
			format = mapOfSpecificParameters.get("format");
		
		mailOutputFormater = OutputFactory.createOutputFormater(format, lang, mimeType);
	}
	
	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) {
		try {
			aWriter.append("mail en cours d'envoi.");
			aWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
	    
		try {
			Properties mailProps = Property.getProps("mail.properties");
			Authenticator auth = new UnicornAuthenticator(mailProps.getProperty("unicorn.mail.username"), mailProps.getProperty("unicorn.mail.password"));
			Session session = Session.getDefaultInstance(mailProps, auth);
		    session.setDebug(true);
		    Message msg = new MimeMessage(session);
		    
		    InternetAddress addressFrom = new InternetAddress(mailProps.getProperty("unicorn.mail.from"), "Unicorn");
			msg.setFrom(addressFrom);
			
			InternetAddress[] adresses = {new InternetAddress(recipient)};
			msg.setRecipients(Message.RecipientType.TO, adresses);
			
			// Setting the Subject and Content Type
			msg.setSubject("Unicorn results, " + new Date());
			
			CharArrayWriter writer = new CharArrayWriter();
			mailOutputFormater.produceOutput(mapOfStringObject, writer);
			writer.close();
			msg.setContent(writer.toString(), mapOfOutputParameters.get("mimetype"));
			
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
		aOutputFormater.produceError(mapOfStringObject, aWriter);
	}

	public String getOutputParameter(String string) {
		return mapOfOutputParameters.get(string);
	}

}