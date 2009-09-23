// $Id: MailOutputModule.java,v 1.7 2009-09-23 16:59:53 tgambet Exp $
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
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.UnicornAuthenticator;

/**
 * This module allow to generate output in mail format.
 * 
 * @author Thomas GAMBET
 */
public class MailOutputModule implements OutputModule {
	
	private OutputFormater firstOutputFormater;
	
	private OutputFormater mailOutputFormater;
	
	private String mimeType;
	
	private String recipient;
	
	private Map<String, String> mapOfOutputParameters;
	
	public MailOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		this.mapOfOutputParameters = mapOfOutputParameters;
		//this.mapOfSpecificParameters = mapOfSpecificParameters;
		
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
		try {
			aWriter.append("mail en cours d'envoi.");
			aWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			subject += "Task \"" + uniCall.getTask().getLongName(mapOfOutputParameters.get("lang")) + "\" for \"" + uniCall.getDocumentName() + "\"";
			
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

	public String getOutputParameter(String string) {
		return mapOfOutputParameters.get(string);
	}

}