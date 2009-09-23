// $Id: MailOutputModule.java,v 1.2 2009-09-23 09:28:05 tgambet Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.*; 

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
			
		
		aOutputFormater = OutputFactory.createOutputFormater(mapOfOutputParameters.get("format"),
				mapOfOutputParameters.get("lang"), mapOfOutputParameters.get("mimetype"));
		
		mailOutputFormater = OutputFactory.createOutputFormater(mapOfSpecificParameters.get("format"),
				mapOfOutputParameters.get("lang"), mapOfOutputParameters.get("mimetype"));
		
	}
	
	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) {
		
		try {
			aWriter.append("mail en cours d'envoi.");
			aWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("TOOOOOO");
			e.printStackTrace();
		}
	}
	
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		
		/*try {
			aWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("TOOOOOO");
			e.printStackTrace();
		}*/
		
	    Properties props = new Properties();
	    props.put("mail.smtp.starttls.enable","true");
	    //props.put("mail.smtp.user", "thomas.gambet@gmail.com");
	    
		try {
			Session session = Session.getDefaultInstance(props, null);
		    session.setDebug(true);
		    Message msg = new MimeMessage(session);
			
		    InternetAddress addressFrom = new InternetAddress("thomas.gambet@gmail.com");
			msg.setFrom(addressFrom);
			
			InternetAddress[] adresses = {new InternetAddress(recipient)};
			msg.setRecipients(Message.RecipientType.TO, adresses);
			
			// Setting the Subject and Content Type
			msg.setSubject("test subject");
			
			CharArrayWriter writer = new CharArrayWriter();
			mailOutputFormater.produceOutput(mapOfStringObject, writer);
			writer.close();
			
			msg.setContent(writer.toString(), mapOfOutputParameters.get("mimetype"));
			
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "eric.gambet@gmail.com", "36153615");
			transport.sendMessage(msg, msg.getAllRecipients());
			//aOutputFormater.produceOutput(mapOfStringObject, aWriter);
			//aWriter.close();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
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
