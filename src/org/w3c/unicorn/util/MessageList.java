package org.w3c.unicorn.util;

import java.util.ArrayList;

import com.ibm.icu.util.ULocale;

public class MessageList extends ArrayList<Message> {

	private static final long serialVersionUID = -720345110444544838L;
	
	private ULocale locale;

	public MessageList() {
		this.locale = Language.getDefaultLocale();
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
	
	public MessageList(ULocale lang) {
		this.locale = lang;
	}

	public ULocale getLocale() {
		return locale;
	}

	public void setLocale(ULocale locale) {
		this.locale = locale;
	}

}
