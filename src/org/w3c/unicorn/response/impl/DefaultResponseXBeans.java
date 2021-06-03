// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3.x2009.x10.unicorn.observationresponse.GroupType;
import org.w3.x2009.x10.unicorn.observationresponse.ListType;
import org.w3.x2009.x10.unicorn.observationresponse.MessageType;
import org.w3.x2009.x10.unicorn.observationresponse.ObservationresponseDocument;
import org.w3.x2009.x10.unicorn.observationresponse.StatusType;
import org.w3.x2009.x10.unicorn.observationresponse.ObservationresponseDocument.Observationresponse;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.response.Group;
import org.w3c.unicorn.response.Message;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.exceptions.UnicornException;

public class DefaultResponseXBeans implements Response {
	
	private ObservationresponseDocument ord;
	private Observationresponse or;
	
	private List<Message> messages = new ArrayList<Message>();
	private int errorCount = 0;
	private int warningCount = 0;
	private int infoCount = 0;
	
	private List<Group> groups = new ArrayList<Group>();
	
	private String requestURI;
	
	private String observerID;
	
	public DefaultResponseXBeans(InputStream is, String charset, String observerID) throws UnicornException {
		
		if (charset == null)
			charset = "UTF-8";
		
		this.observerID = observerID;
		
		try {
			Collection<XmlError> errorList = new ArrayList<XmlError>();
			XmlOptions xo = new XmlOptions();
			xo.setErrorListener(errorList);
			ord = ObservationresponseDocument.Factory.parse(is, new XmlOptions().setCharacterEncoding(charset));
			is.close();
			or = ord.getObservationresponse();
			if (!or.validate(xo)) {
				String content = "";
				content += "Errors: " + errorList.size();
			    for (XmlError error : errorList) {
			    	content += "\n" + error.getMessage();
			    }
				throw new UnicornException(new org.w3c.unicorn.util.Message(2, "$message_response_validation_error", content, Framework.mapOfObserver.get(observerID).getName(Property.get(getLang()))));
			}
		} catch (XmlException e) {
			if (e.getMessage().contains("document element namespace mismatch"))
				throw new UnicornException(new org.w3c.unicorn.util.Message(org.w3c.unicorn.util.Message.ERROR, "$message_response_invalid_schema", null, Framework.mapOfObserver.get(observerID).getName(Property.get("DEFAULT_LANGUAGE"))));
			else
				throw new UnicornException(new org.w3c.unicorn.util.Message(e));
		} catch (IOException e) {
			throw new UnicornException(new org.w3c.unicorn.util.Message(e));
		}
		
		for (GroupType group : or.getGroupArray()) {
			if (group.getLang() == null)
				group.setLang(getLang());
			groups.add(new DefaultGroupXBeans(group));
		}
		
		for (ListType list : or.getListArray()) {
			for (MessageType message : list.getMessageArray()) {
				if (message.getLang() == null)
					message.setLang(getLang());
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
					errorCount++;
					break;
				case Message.WARNING:
					warningCount++;
					break;
				case Message.INFO:
					infoCount++;
					break;
				}
				messages.add(m);
			}
		}
		
		for (MessageType message : or.getMessageArray()) {
			if (message.getLang() == null)
				message.setLang(getLang());
			DefaultMessageXBeans m = new DefaultMessageXBeans(message);
			
			if (m.getURI() == null)
				m.setURI(getURI());
			
			switch (m.getType()) {
			case Message.ERROR:
				errorCount++;
				break;
			case Message.WARNING:
				warningCount++;
				break;
			case Message.INFO:
				infoCount++;
				break;
			}
			
			if (hasGroups() && !message.isSetGroup())
				m.setGroupName(groups.get(0).getName());
			
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
		if (or.getStatusArray().length == 0) {
			if (getErrorCount() > 0) {
				return FAILED;
			} else {
				return UNDEF;
			}
		} else {
			// Status element can be added at the top or the end of a document. If there are two status element, only the last one is used.
			StatusType status = or.getStatusArray()[or.getStatusArray().length - 1];
			if (status.isSetValue() && status.getValue().equalsIgnoreCase("passed")) {
				return PASSED;
			} else if (status.isSetValue() && status.getValue().equalsIgnoreCase("failed")) {
				return FAILED;
			} else {
				return UNDEF;
			}
		}
	}
	
	public Integer getRating() {
		if (or.getStatusArray().length != 0) {
			StatusType status = or.getStatusArray()[or.getStatusArray().length - 1];
			if (status.isSetRating())
				return status.getRating();
		}
		return null;
	}
	
	public Iterable<Message> getMessages() {
		return messages;
	}
	
	public Iterable<Message> getErrorMessages() {
		return getMessages(null, Message.ERROR);
	}
	
	public Iterable<Message> getInfoMessages() {
		return getMessages(null, Message.INFO);
	}
	
	public Iterable<Message> getWarningMessages() {
		return getMessages(null, Message.WARNING);
	}
	
	public int getErrorCount() {
		return errorCount;
	}
	
	public int getWarningCount() {
		return warningCount;
	}
	
	public int getInfoCount() {
		return infoCount;
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

	public Iterable<Message> getMessages(String uri, int type) {
		return new MessageIterable(uri, type, null);
	}
	
	protected class MessageIterable implements Iterable<Message> {

		private int index = 0;
		private Integer type;
		private String uri;
		private String group;
		
		public MessageIterable(String uri, Integer type, String group) {
			this.uri = uri;
			this.type = type;
			this.group = group;
		}
		
		public int size() {
			index = 0;
			int size = 0;
			while (index < messages.size()) {
				if ((uri == null || uri.equals(messages.get(index).getURI())) && 
					(type == null || messages.get(index).getType() == type) &&
					(group == null || group.equals(messages.get(index).getGroupName()))) {
					index++;
					size++;
				}
				index++;
			}
			index = 0;
			return size;
		}
		
		public Iterator<Message> iterator() {
			return new Iterator<Message>() {
				public boolean hasNext() {
					int x = index;
					while (x < messages.size()) {
						if ((uri == null || uri.equals(messages.get(x).getURI())) && 
							(type == null || messages.get(x).getType() == type) &&
							(group == null || group.equals(messages.get(x).getGroupName())))
							
							return true;
						x++;
					}
					return false;
				}

				public Message next() {
					while (index < messages.size()) {
						if ((uri == null || uri.equals(messages.get(index).getURI())) && 
							(type == null || messages.get(index).getType() == type) &&
							(group == null || group.equals(messages.get(index).getGroupName()))) {
							index++;
							return messages.get(index - 1);
						}
						index++;
					}
					return null;
				}

				public void remove() {
					return;
				}
			};
		}
	}

	public Iterable<Message> getErrorMessages(String uri) {
		return getMessages(uri, Message.ERROR);
	}

	public Iterable<Message> getInfoMessages(String uri) {
		return getMessages(uri, Message.INFO);
	}

	public Iterable<Message> getMessages(String uri, Integer type) {
		return new MessageIterable(uri, type, null);
	}

	public Map<String, Iterable<Message>> getURISortedMessages(int type) {
		List<String> uris = new ArrayList<String>();
		for (Message mess : getMessages(null, type)) {
			if (!uris.contains(mess.getURI()))
				uris.add(mess.getURI());
		}
		Map<String, Iterable<Message>> sortedMap = new Hashtable<String, Iterable<Message>>();
		for (String uri : uris) {
			sortedMap.put(uri, getMessages(uri, type));
		}
		return sortedMap;
	}

	public Iterable<Message> getWarningMessages(String uri) {
		return getMessages(uri, Message.WARNING);
	}

	public String getHTMLIndexUri() {
		return Framework.mapOfObserver.get(observerID).getIndexURI();
	}

	public Iterable<Message> getMessages(String group) {
		return new MessageIterable(null, null, group);
	}
	
	public Iterable<Message> getMessages(String group, String uri) {
		return new MessageIterable(uri, null, group);
	}

	public Map<String, Iterable<Message>> getURISortedMessages(String group) {
		List<String> uris = new ArrayList<String>();
		for (Message mess : getMessages(group)) {
			if (!uris.contains(mess.getURI()))
				uris.add(mess.getURI());
		}
		Map<String, Iterable<Message>> sortedMap = new Hashtable<String, Iterable<Message>>();
		for (String uri : uris) {
			sortedMap.put(uri, getMessages(group, uri));
		}
		return sortedMap;
	}

	public boolean isUndef() {
		if (getStatus() == UNDEF)
			return true;
		return false;
	}

	public String getLang() {
		return or.getLang();
	}

}
