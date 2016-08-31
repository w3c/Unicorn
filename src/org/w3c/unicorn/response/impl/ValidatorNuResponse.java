// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonReaderFactory;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.response.Group;
import org.w3c.unicorn.response.Message;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.exceptions.UnicornException;

public class ValidatorNuResponse implements Response {
	
	private List<Message> messages = new ArrayList<Message>();
	private int errorCount = 0;
	private int warningCount = 0;
	private int infoCount = 0;
	
	private List<Group> groups = new ArrayList<Group>();
	
	private String requestURI;
	
	private String observerID;

  private JsonObject jsonResponse;
	
	public ValidatorNuResponse(InputStream is, String charset, String observerID) throws UnicornException {
		
		if (charset == null)
			charset = "UTF-8";
		
		this.observerID = observerID;

    JsonReader reader      = Json.createReader(is);
    jsonResponse           = reader.readObject();
    JsonArray jsonMessages = jsonResponse.getJsonArray("messages");
    
    for(JsonValue jsonMessage : jsonMessages) {
        if (jsonMessage instanceof JsonObject) {
            Message message = new ValidatorNuMessage((JsonObject) jsonMessage, getURI());
            messages.add(message);

            switch (message.getType()) {
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
        }
    }
  }
    
	public Date getDate() {
      return new Date();
	}
	
	public String getURI() {
      return jsonResponse.getString("url");
	}

	public int getStatus() {
      return (messages.size() == 0) ? PASSED : FAILED;
	}
	
	public Integer getRating() {
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
      return new String[0];
	}

	public boolean evaluateXPath(String xpathQuery) {
      return true;
	}

	public List<Group> getGroupChildren(Group group) {
      return new ArrayList<Group>();
	}

	public boolean isPassed() {
      return (getStatus() == PASSED);
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
                (group == null || group.equals(messages.get(x).getGroupName()))) {
                return true;
            }
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
      return (getStatus() == UNDEF);
	}

	public String getLang() {
      return "en";
	}

}
