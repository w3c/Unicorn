// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Response {
	
	public static final int PASSED = 0;
	
	public static final int FAILED = 1;
	
	public static final int UNDEF = 2;
	
	public Date getDate();

	public List<Group> getGroups();
	
	public List<Group> getGroupChildren(Group group);
	
	public String getURI();

	public int getErrorCount();

	public int getInfoCount();
	
	public int getWarningCount();
	
	public Map<String, Iterable<Message>> getURISortedMessages(int type);
	
	public Map<String, Iterable<Message>> getURISortedMessages(String group);
	
	public Iterable<Message> getMessages();
	
	public Iterable<Message> getMessages(String uri, Integer type);
	
	public Iterable<Message> getMessages(String group);

	public Iterable<Message> getErrorMessages();
	
	public Iterable<Message> getErrorMessages(String uri);

	public Iterable<Message> getWarningMessages();
	
	public Iterable<Message> getWarningMessages(String uri);

	public Iterable<Message> getInfoMessages();
	
	public Iterable<Message> getInfoMessages(String uri);

	public Integer getRating();

	public int getStatus();
	
	public boolean isPassed();
	
	public boolean isUndef();

	public boolean isSetRating();
	
	public boolean hasGroups();
	
	public String getObserverID();
	
	public void setObserverId(String obsId);
	
	public String[] selectPath(String xpath);
	
	public boolean evaluateXPath(String xpathQuery);
	
	public void setRequestUri(String uri);
	
	public String getRequestUri();
	
	public String getHTMLRequestUri();
	
	public String getHTMLIndexUri();
	
	public String getLang();
}
