// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.output.FileOutputFormater;
import org.w3c.unicorn.output.OutputFormater;

public class Mail {

	private static Log logger = LogFactory.getLog(Mail.class);
	
	public void sendMail(String[] recipients, String subject, List<OutputFormater> outputFormaters, Map<String, Object> contextObjects, boolean alternative) throws UnicornException {
		
		try {
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

			List<InternetAddress> validAddresses = new ArrayList<InternetAddress>();
			
			for (String recipient : recipients) {
				try {
					InternetAddress address = new InternetAddress(recipient);
					validAddresses.add(address);
				} catch(AddressException e) {
					logger.warn("Invalid address: \"" + recipient + "\". Skipping.");
				}
			}
			
			InternetAddress[] recipientsAddresses = new InternetAddress[validAddresses.size()];
			int i = 0;
			for (InternetAddress add : validAddresses) {
				recipientsAddresses[i] = add;
				i++;
			}
			
			msg.setRecipients(javax.mail.Message.RecipientType.TO, recipientsAddresses);
			msg.setSubject(subject);
			
			if (outputFormaters.size() > 1) {
				// New multipart message
				MimeMultipart mp;
				if (alternative)
					mp = new MimeMultipart("alternative");
				else
					mp = new MimeMultipart();
				for (OutputFormater outputFormater : outputFormaters) {		
					MimeBodyPart bodyPart = new MimeBodyPart();
					if (outputFormater instanceof FileOutputFormater)
						bodyPart.setFileName(((FileOutputFormater) outputFormater).getFileName());

					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
					outputFormater.produceOutput(contextObjects, outputStreamWriter);
					try {
						outputStreamWriter.close();
						byteArrayOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					bodyPart.setContent(byteArrayOutputStream.toString("UTF-8"), outputFormater.getMimeType() + "; charset=UTF-8");
					bodyPart.setHeader("Content-Type", outputFormater.getMimeType() + "; charset=UTF-8");
					bodyPart.setHeader("Content-Transfer-Encoding", "7bit");
					mp.addBodyPart(bodyPart);
				}
				msg.setContent(mp);
			} else {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
				outputFormaters.get(0).produceOutput(contextObjects, outputStreamWriter);
				try {
					outputStreamWriter.close();
					byteArrayOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				msg.setContent(byteArrayOutputStream.toString("UTF-8"), outputFormaters.get(0).getMimeType() + "; charset=UTF-8");
				msg.setHeader("Content-Transfer-Encoding", "7bit");
			}
			
			Transport.send(msg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMail(String[] recipients, String subject, OutputFormater outputFormater, Map<String, Object> contextObjects, boolean alternative) throws UnicornException {
		List<OutputFormater> outputFormaters = new ArrayList<OutputFormater>();
		outputFormaters.add(outputFormater);
		
		sendMail(recipients, subject, outputFormaters, contextObjects, alternative);
	}
}
