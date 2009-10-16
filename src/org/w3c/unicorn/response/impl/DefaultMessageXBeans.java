// $Id: DefaultMessageXBeans.java,v 1.1 2009-10-16 16:24:31 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlOptions;
import org.w3.unicorn.x2009.x10.observationresponse.ContextType;
import org.w3.unicorn.x2009.x10.observationresponse.MessageType;
import org.w3c.unicorn.response.Context;
import org.w3c.unicorn.response.Message;
import org.w3c.unicorn.response.impl.DefaultContextXBeans;

public class DefaultMessageXBeans implements Message {
	
	private List<Context> contexts = new ArrayList<Context>();
	
	private String description;
	
	private String title;
	
	private int level;
	
	private int type;
	
	private String uri;
	
	private String group;
	
	public DefaultMessageXBeans(MessageType message) {
		
		title = message.getTitle();
		
		if (message.isSetRef())
			uri = message.getRef();
		
		if (message.isSetLevel())
			level = message.getLevel();
		
		if (message.getType().equalsIgnoreCase("error"))
			type = Message.ERROR;
		if (message.getType().equalsIgnoreCase("warning"))
			type = Message.WARNING;
		if (message.getType().equalsIgnoreCase("info"))
			type = Message.INFO;
		
		if (message.isSetGroup())
			group = message.getGroup();
		
		if (message.isSetDescription())
			description = message.getDescription().xmlText(new XmlOptions().setUseDefaultNamespace()
					.setSavePrettyPrint()).replaceAll("</?xml-fragment[^>]*>", "").replaceAll("xmlns=\".*\"", "");
	
		for (ContextType context : message.getContextList())
			contexts.add(new DefaultContextXBeans(context));
		
		System.out.println("Message ----------------------");
		System.out.println("Title: " + title);
		System.out.println("Description: " + description);
		System.out.println("level: " + level);
		System.out.println("type: " + type);
		System.out.println("uri: " + uri);
		System.out.println("group: " + group);
		System.out.println("contexts.size(): " + contexts.size());
		System.out.println("------------------------------");
	}
	
	public List<Context> getContexts() {
		return contexts;
	}

	public String getDescription() {
		return description;
	}

	public int getLevel() {
		return level;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public String getGroupName() {
		return group;
	}

	public void setGroupName(String group) {
		this.group = group;
	}
}
