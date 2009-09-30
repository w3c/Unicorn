// $Id: MailOutputModule.java,v 1.12 2009-09-30 13:36:15 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

import javax.mail.internet.MimeBodyPart;

/**
 * This module allows to send the response by mail.
 * 
 * @author Thomas GAMBET
 */
public class MailOutputModule extends OutputModule {
	
private List<OutputFormater> mailOutputFormaters;
	
	private String recipient;
	
	private String mailFormat;
	
	public MailOutputModule(Map<String, String> mapOfOutputParameters, Map<String, String> mapOfSpecificParameters) {
		super(mapOfOutputParameters, mapOfSpecificParameters);
		
		recipient = specificParameters.get("email");
		
		mailFormat = specificParameters.get("format");
		
		mailOutputFormaters = new ArrayList<OutputFormater>(); 
			
		mailOutputFormaters.add(OutputFactory.createOutputFormater("text", outputParameters.get("lang")));
		
		if (mailFormat == null) {
			mailFormat = defaultOutputFormater.getFormat();
		}
		
		if (!"text".equals(mailFormat)) {
			mailOutputFormaters.add(OutputFactory.createOutputFormater(mailFormat, outputParameters.get("lang")));
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void produceFirstOutput(Map<String, Object> mapOfStringObject, Writer aWriter) throws UnicornException {
		//mapOfStringObject.put("baseUri", "http://qa-dev.w3.org/unicorn/");
		
		ArrayList<Message> messages = ((ArrayList<Message>) mapOfStringObject.get("messages"));
		Message pendingMess = new Message(Message.Level.INFO, "$message_mail " + recipient);
		
		if (recipient == null) {
			throw new UnicornException(new Message(Message.Level.ERROR, "$message_missing_email"));
		} else {
			messages.add(pendingMess);
		}
		if (mailOutputFormaters == null && mailOutputFormaters.get(0) == null)
			throw new UnicornException();
		
		displayOnIndex(mapOfStringObject, aWriter);
		messages.remove(pendingMess);
	}
	
	@SuppressWarnings("unchecked")
	public void produceOutput(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		
		if (recipient == null)
			return;
		if (mailOutputFormaters == null && mailOutputFormaters.get(0) == null)
			return;
		
		try {
			ArrayList<Message> messages = ((ArrayList<Message>) mapOfStringObject.get("messages"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss (Z)", new Locale(outputParameters.get("lang")));
			
			messages.add(new Message(Message.Level.INFO, "$message_mail_date " + dateFormat.format(new Date())));
			
			mapOfStringObject.put("baseUri", Property.get("UNICORN_URL"));
			
			Properties mailProps = Property.getProps("mail.properties");
			Authenticator auth = new UnicornAuthenticator(mailProps.getProperty("unicorn.mail.username"), mailProps.getProperty("unicorn.mail.password"));
			Session session = Session.getDefaultInstance(mailProps, auth);
		    
			boolean debug = false;
			if ("true".equals(mailProps.getProperty("unicorn.mail.debug")))
				debug = true;
			
			session.setDebug(debug);
			
			UnicornCall uniCall = (UnicornCall) mapOfStringObject.get("unicorncall");
			boolean passed = uniCall.isPassed();
			
			String subject = "[Unicorn] ";
			if (passed)
				subject += "SUCCEEDED: ";
			else 
				subject += "FAILED: ";
			subject += "Task \"" + uniCall.getTask().getLongName(outputParameters.get("lang")) + "\" for \"" + uniCall.getDocumentName() + "\"";
			
			javax.mail.Message msg = new MimeMessage(session);
		    InternetAddress addressFrom = new InternetAddress(mailProps.getProperty("unicorn.mail.from"), "Unicorn");
			msg.setFrom(addressFrom);
			msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipient));
			msg.setSubject(subject);
			
			if (mailOutputFormaters.size() > 1) {
				// New multipart message
				Multipart mp = new MimeMultipart("alternative");
				for (OutputFormater outputFormater : mailOutputFormaters) {
					MimeBodyPart bodyPart = new MimeBodyPart();
					bodyPart.addHeader("Content-Type", outputFormater.getMimeType() + ", charset=UTF-8");
					CharArrayWriter writer = new CharArrayWriter();
					outputFormater.produceOutput(mapOfStringObject, writer);
					writer.close();
					bodyPart.setContent(writer.toString(), outputFormater.getMimeType());
					mp.addBodyPart(bodyPart);
				}
				msg.setContent(mp);
			} else {
				CharArrayWriter writer = new CharArrayWriter();
				mailOutputFormaters.get(0).produceOutput(mapOfStringObject, writer);
				writer.close();
				msg.setContent(writer.toString(), mailOutputFormaters.get(0).getMimeType());
			}
			
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
		if (getMimeType().equals("text/html")) {
			displayOnIndex(mapOfStringObject, aWriter);
			return;
		}
		defaultOutputFormater.produceError(mapOfStringObject, aWriter);
	}

}