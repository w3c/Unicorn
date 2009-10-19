// $Id: DefaultResponseXBeans.java,v 1.2 2009-10-19 12:42:07 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3.unicorn.x2009.x10.observationresponse.GroupType;
import org.w3.unicorn.x2009.x10.observationresponse.ListType;
import org.w3.unicorn.x2009.x10.observationresponse.MessageType;
import org.w3.unicorn.x2009.x10.observationresponse.ObservationresponseDocument;
import org.w3.unicorn.x2009.x10.observationresponse.ObservationresponseDocument.Observationresponse;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.response.Group;
import org.w3c.unicorn.response.Message;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.exceptions.UnicornException;

public class DefaultResponseXBeans implements Response {
	
	private ObservationresponseDocument ord;
	private Observationresponse or;
	
	private List<Message> messages = new ArrayList<Message>();
	private List<Message> errorMessages = new ArrayList<Message>();
	private List<Message> warningMessages = new ArrayList<Message>();
	private List<Message> infoMessages = new ArrayList<Message>();
	
	private List<Group> groups = new ArrayList<Group>();
	
	private String requestURI;
	
	private String observerID;
	
	public DefaultResponseXBeans(InputStream is, String charset) throws UnicornException {
		
		if (charset == null)
			charset = "UTF-8";
		
		try {
			ord = ObservationresponseDocument.Factory.parse(is, new XmlOptions().setCharacterEncoding(charset));
			or = ord.getObservationresponse();
			
			if (!or.validate())
				throw new UnicornException(new org.w3c.unicorn.util.Message(2, "$message_response_validation_error"));
			
			System.out.println("Response ----------------------");
			System.out.println("Date: " + getDate());
			System.out.println("URI: " + getURI());
			System.out.println("Status: " + getStatus());
			System.out.println("Rating: " + getRating());
			System.out.println("-------------------------------");
			
		} catch (XmlException e) {
			if (e.getMessage().contains("is not a valid observationresponse"))
				throw new UnicornException(new org.w3c.unicorn.util.Message(org.w3c.unicorn.util.Message.ERROR, "$message_observer_invalid_response_schema"));
			else
				throw new UnicornException(new org.w3c.unicorn.util.Message(e));
		} catch (IOException e) {
			throw new UnicornException(new org.w3c.unicorn.util.Message(e));
		}
		
		for (GroupType group : or.getGroupList())
			groups.add(new DefaultGroupXBeans(group));
		
		for (ListType list : or.getListList()) {
			for (MessageType message : list.getMessageList()) {
				DefaultMessageXBeans m = new DefaultMessageXBeans(message);
				if (m.getURI() == null) {
					if (list.isSetRef())
						m.setURI(list.getRef());
					else
						m.setURI(or.getRef().getStringValue());
				}
				if (list.isSetGroup() && m.getGroupName() == null)
					m.setGroupName(list.getGroup());
				switch (m.getType()) {
				case Message.ERROR:
					errorMessages.add(m);
					break;
				case Message.WARNING:
					warningMessages.add(m);
					break;
				case Message.INFO:
					infoMessages.add(m);
					break;
				}
				messages.add(m);
			}
		}
		
		for (MessageType message : or.getMessageList()) {
			DefaultMessageXBeans m = new DefaultMessageXBeans(message);
			
			if (m.getURI() == null)
				m.setURI(getURI());
			
			switch (m.getType()) {
			case Message.ERROR:
				errorMessages.add(m);
				break;
			case Message.WARNING:
				warningMessages.add(m);
				break;
			case Message.INFO:
				infoMessages.add(m);
				break;
			}
			messages.add(m);
		}
	}
	
	public Date getDate() {
		return or.getDate().getTime();
	}
	
	public String getURI() {
		return or.getRef().getStringValue();
	}

	public int getStatus() {
		if (or.isSetStatus() && or.getStatus().getValue().equalsIgnoreCase("passed")) {
			return PASSED;
		} else if (or.isSetStatus() && or.getStatus().getValue().equalsIgnoreCase("failed") || getErrorCount() > 0) {
			return FAILED;
		} else { 
			return UNDEF;
		}
	}
	
	public Integer getRating() {
		if(or.isSetStatus() && or.getStatus().isSetRating())
			return or.getStatus().getRating();
		return null;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public List<Message> getErrorMessages() {
		return errorMessages;
	}
	
	public List<Message> getInfoMessages() {
		return infoMessages;
	}
	
	public List<Message> getWarningMessages() {
		return warningMessages;
	}
	
	public int getErrorCount() {
		return errorMessages.size();
	}
	
	public int getWarningCount() {
		return warningMessages.size();
	}
	
	public int getInfoCount() {
		return infoMessages.size();
	}

	public List<Group> getGroups() {		
		return groups;
	}

	public boolean isSetRating() {
		if (getRating() == null)
			return false;
		return true;
	}
	
	public void setRequestUri(String uri) {
		requestURI = uri;
	}

	public String getRequestUri() {
		return requestURI;
	}
	
	public String getHTMLRequestUri() {
		if (requestURI != null) {
			String outputParamName = Framework.mapOfObserver.get(observerID).getParamOutputName();
			return requestURI.replaceAll("&?" + outputParamName + "=[^&]*", "");
		} else {
			return null;
		}
	}
	
	public String getObserverID() {
		return observerID;
	}
	
	public void setObserverId(String obsId) {
		observerID = obsId;
	}
	
	public boolean hasGroups() {
		if (groups.size() > 0)
			return true;
		return false;
	}
	
	public String[] execQuery(String query) {
		XmlObject[] objects = or.execQuery(query);
		String[] result = new String[objects.length];
		int i = 0;
		for (XmlObject obj : objects) {
			result[i] = obj.toString();
			i++;
		}
		return result;
	}

	public String[] selectPath(String xpath) {
		XmlObject[] objects = or.selectPath(xpath);
		String[] result = new String[objects.length];
		int i = 0;
		for (XmlObject obj : objects) {
			result[i] = obj.toString();
			i++;
		}
		return result;
	}

	public boolean evaluateXPath(String xpathQuery) {
		XmlObject[] objects = or.selectPath(xpathQuery);
		if (objects.length > 0)
			return true;
		return false;
	}

	public List<Group> getGroupChildren(Group group) {
		String groupName = group.getName();
		List<Group> children = new ArrayList<Group>();
		for (Group g : groups) {
			if (g.hasParent() && g.getParentName().equals(groupName)) {
				children.add(g);
			}
		}
		return children;
	}

	public boolean isPassed() {
		if (getStatus() == PASSED)
			return true;
		return false;
	}

}
