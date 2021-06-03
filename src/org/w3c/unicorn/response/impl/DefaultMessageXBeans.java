// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlOptions;
import org.w3.x2009.x10.unicorn.observationresponse.ContextType;
import org.w3.x2009.x10.unicorn.observationresponse.MessageType;
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
	
	private String lang;
	
	public DefaultMessageXBeans(MessageType message) {
		
		XmlOptions opts = new XmlOptions();
		opts.setSaveCDataLengthThreshold(10000000);
		opts.setSaveCDataEntityCountThreshold(-1);
		opts.setUseDefaultNamespace();
		
		title = message.getTitle().xmlText(opts)
				.replaceAll("[ ]*xmlns=\"[^>]*\"", "").replaceAll("</?xml-fragment[^>]*>", "");
		
		lang = message.getLang();
		
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
		
		if (message.isSetDescription()) {
			description = message.getDescription().xmlText(opts)
				.replaceAll("[ ]*xmlns=\"[^>]*\"", "").replaceAll("</?xml-fragment[^>]*>", "");
		}
		
		for (ContextType context : message.getContextArray())
			contexts.add(new DefaultContextXBeans(context));
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

	public String getLang() {
		return lang;
	}
}
