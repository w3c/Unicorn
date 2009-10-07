package org.w3c.unicorn.util;

import java.util.ArrayList;

public class MessageList extends ArrayList<Message> {

	private static final long serialVersionUID = -720345110444544838L;
	
	private String lang;

	public MessageList() {
		this.lang = Property.get("DEFAULT_LANGUAGE");
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
	
	public MessageList(String lang) {
		this.lang = lang;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
