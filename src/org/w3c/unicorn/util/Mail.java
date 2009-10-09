package org.w3c.unicorn.util;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.output.AttachmentOutputFormater;
import org.w3c.unicorn.output.OutputFormater;

public class Mail {

	public void sendMail(String[] recipients, String subject, List<OutputFormater> outputFormaters, Map<String, Object> contextObjects) throws UnicornException {
		
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

			Address[] recipientAdresses = new Address[recipients.length];
			int i = 0;
			for (String recipient : recipients) {
				recipientAdresses[i] = new InternetAddress(recipient);
				i++;
			}
			msg.setRecipients(javax.mail.Message.RecipientType.TO, recipientAdresses);
			msg.setSubject(subject);
			
			if (outputFormaters.size() > 1) {
				// New multipart message
				MimeMultipart mp = new MimeMultipart();
				for (OutputFormater outputFormater : outputFormaters) {		
					MimeBodyPart bodyPart = new MimeBodyPart();
					if (outputFormater instanceof AttachmentOutputFormater)
						bodyPart.setFileName(((AttachmentOutputFormater) outputFormater).getFileName());

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
					
					
					
					bodyPart.setContent(byteArrayOutputStream.toString("UTF-8"), outputFormater.getMimeType());
					
					bodyPart.setHeader("Content-Type", outputFormater.getMimeType() + "; charset=UTF-8");
					bodyPart.setHeader("Content-Transfer-Encoding", "8bit");
					mp.addBodyPart(bodyPart);
				}
				msg.setContent(mp);
			} else {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
				//CharArrayWriter wr = new CharArrayWriter();
				
				outputFormaters.get(0).produceOutput(contextObjects, outputStreamWriter);
				
				//outputStreamWriter.write("んのこと仲介手数料もなし");
				
				try {
					outputStreamWriter.close();
					byteArrayOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(byteArrayOutputStream.toString("UTF-8"));
				
				
				msg.setContent(byteArrayOutputStream.toString("UTF-8"), outputFormaters.get(0).getMimeType() + "; charset=UTF-8");
				msg.writeTo(new FileOutputStream(new File(Property.get("UPLOADED_FILES_REPOSITORY") + "/test1.txt")));
				msg.setHeader("Content-Type", outputFormaters.get(0).getMimeType() + "; charset=UTF-8");
				msg.setHeader("Content-Transfer-Encoding", "7bit");
				
			}
			msg.writeTo(new FileOutputStream(new File(Property.get("UPLOADED_FILES_REPOSITORY") + "/test.txt")));
			Transport.send(msg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMail(String[] recipients, String subject, OutputFormater outputFormater, Map<String, Object> contextObjects) throws UnicornException {
		List<OutputFormater> outputFormaters = new ArrayList<OutputFormater>();
		outputFormaters.add(outputFormater);
		
		sendMail(recipients, subject, outputFormaters, contextObjects);
	}
}
