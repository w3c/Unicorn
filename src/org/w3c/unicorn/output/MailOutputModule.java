// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.output;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import org.w3c.unicorn.util.Message;

import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Mail;
import org.w3c.unicorn.util.Property;

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

		ArrayList<Message> messages = ((ArrayList<Message>) mapOfStringObject.get("messages"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss (Z)", new Locale(outputParameters.get("lang")));
		
		messages.add(new Message(Message.INFO, "$message_mail_date", null, dateFormat.format(new Date())));
		
		mapOfStringObject.put("baseUri", Property.get("UNICORN_URL"));
		
		UnicornCall uniCall = (UnicornCall) mapOfStringObject.get("unicorncall");
		boolean passed = uniCall.isPassed();
		
		String subject = "[Unicorn] ";
		if (passed)
			subject += "SUCCEEDED: ";
		else 
			subject += "FAILED: ";
		subject += "Task \"" + uniCall.getTask().getLongName(outputParameters.get("lang")) + "\" for \"" + uniCall.getDocumentName() + "\"";
		
		Mail sender = new Mail();
		try {
			sender.sendMail(new String[] {recipient}, subject, mailOutputFormaters, mapOfStringObject, true);
		} catch (UnicornException e) {
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