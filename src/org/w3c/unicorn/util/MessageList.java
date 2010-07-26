// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.util.ArrayList;

import com.ibm.icu.util.ULocale;

public class MessageList extends ArrayList<Message> {

	private static final long serialVersionUID = -720345110444544838L;
	
	private static ArrayList<Message> defaultMessages = new ArrayList<Message>();
	
	private ULocale locale;

	public MessageList() {
		this(Language.getDefaultLocale());
	}
	
	public MessageList(ULocale lang) {
		this.locale = lang;
		this.addAll(defaultMessages);
	}
	
	public boolean hasInfo() {
		for (Message m : this)
			if (m.getLevel() == Message.INFO)
				return true;
		return false;
	}
	
	public boolean hasWarning() {
		for (Message m : this)
			if (m.getLevel() == Message.WARNING)
				return true;
		return false;
	}
	
	public boolean hasErrors() {
		for (Message m : this)
			if (m.getLevel() == Message.ERROR)
				return true;
		return false;
	}

	public ULocale getLocale() {
		return locale;
	}

	public void setLocale(ULocale locale) {
		this.locale = locale;
	}

	public static ArrayList<Message> getDefaultMessages() {
		return defaultMessages;
	}

	public static void setDefaultMessages(ArrayList<Message> defaultMessages) {
		MessageList.defaultMessages = defaultMessages;
	}

}
