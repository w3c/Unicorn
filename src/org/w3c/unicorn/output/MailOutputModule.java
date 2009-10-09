// $Id: MailOutputModule.java,v 1.16 2009-10-09 06:47:19 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
		Message pendingMess = new Message(Message.INFO, "$message_mail", null, recipient);
		
		if (recipient == null) {
			throw new UnicornException(new Message(Message.ERROR, "$message_missing_email"));
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
			
			messages.add(new Message(Message.INFO, "$message_mail_date", null, dateFormat.format(new Date())));
			
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
					
					//CharArrayWriter writer = new CharArrayWriter();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
					
					outputFormater.produceOutput(mapOfStringObject, outputStreamWriter);
					outputStreamWriter.close();
					byteArrayOutputStream.close();
					bodyPart.setContent(byteArrayOutputStream.toString(), outputFormater.getMimeType());
					bodyPart.setHeader("Content-Type", outputFormater.getMimeType() + "; charset=UTF-8");
					bodyPart.setHeader("Content-Transfer-Encoding", "8bit");
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void produceError(Map<String, Object> mapOfStringObject, final Writer aWriter) {
		if (defaultOutputFormater.getFormat().equals("xhtml10")) {
			displayOnIndex(mapOfStringObject, aWriter);
			return;
		}
		defaultOutputFormater.produceError(mapOfStringObject, aWriter);
	}

}