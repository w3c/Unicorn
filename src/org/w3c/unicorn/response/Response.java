// $Id: Response.java,v 1.11 2009-10-19 12:38:30 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response;

import java.util.Date;
import java.util.List;

public interface Response {
	
	public static final int PASSED = 0;
	
	public static final int FAILED = 1;
	
	public static final int UNDEF = 2;
	
	public Date getDate();

	public List<Group> getGroups();
	
	public List<Group> getGroupChildren(Group group);

	public List<Message> getMessages();

	public List<Message> getErrorMessages();

	public List<Message> getWarningMessages();

	public List<Message> getInfoMessages();

	public Integer getRating();

	public String getURI();

	public int getErrorCount();

	public int getInfoCount();
	
	public int getWarningCount();

	public int getStatus();
	
	public boolean isPassed();

	public boolean isSetRating();
	
	public boolean hasGroups();
	
	public String getObserverID();
	
	public void setObserverId(String obsId);
	
	public String[] execQuery(String query);
	
	public String[] selectPath(String xpath);
	
	public boolean evaluateXPath(String xpathQuery);
	
	public void setRequestUri(String uri);
	
	public String getRequestUri();
	
	public String getHTMLRequestUri();
	
}
