package org.w3c.unicorn.response.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.w3.unicorn.observationresponse.*;
import org.w3.unicorn.observationresponse.ErrorlistDocument.*;
import org.w3.unicorn.observationresponse.ErrorDocument.Error;
import org.w3.unicorn.observationresponse.InfoDocument.Info;
import org.w3.unicorn.observationresponse.InfolistDocument.Infolist;
import org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse;
import org.w3.unicorn.observationresponse.WarningDocument.Warning;
import org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.response.Group;
import org.w3c.unicorn.response.Message;
import org.w3c.unicorn.response.Response;

public class OldResponseXBeans implements Response {

	private ObservationresponseDocument ord;
	private Observationresponse or;
	
	private List<Message> messages = new ArrayList<Message>();
	private List<Message> errorMessages = new ArrayList<Message>();
	private List<Message> warningMessages = new ArrayList<Message>();
	private List<Message> infoMessages = new ArrayList<Message>();
	
	private String requestURI;
	
	private String observerID;
	
	public OldResponseXBeans(InputStream is, String charset) throws UnicornException {
		
		if (charset == null)
			charset = "UTF-8";
		
		try {
			ord = ObservationresponseDocument.Factory.parse(is, new XmlOptions().setCharacterEncoding(charset));
			or = ord.getObservationresponse();
			
			//if (!or.validate())
			//	throw new UnicornException(new org.w3c.unicorn.util.Message(2, "$message_response_validation_error"));
			
		} catch (XmlException e) {
			if (e.getMessage().contains("is not a valid observationresponse"))
				throw new UnicornException(new org.w3c.unicorn.util.Message(org.w3c.unicorn.util.Message.ERROR, "$message_observer_invalid_response_schema"));
			else
				throw new UnicornException(new org.w3c.unicorn.util.Message(e));
		} catch (IOException e) {
			throw new UnicornException(new org.w3c.unicorn.util.Message(e));
		}
		
		if (or.getResult().getErrors() != null) {
			for (Errorlist errorList : or.getResult().getErrors().getErrorlistList()) {
				for (Error error : errorList.getErrorList()) {
					OldMessageXBeans mess = new OldMessageXBeans(error);
					if (!"".equals(errorList.getUri()) && errorList.getUri() != null )
						mess.setURI(errorList.getUri());
					else
						mess.setURI(or.getUri());
					messages.add(mess);
					errorMessages.add(mess);
				}
			}
		}
		if (or.getResult().getInformations() != null) {
			for (Infolist infoList : or.getResult().getInformations().getInfolistList()) {
				for (Info info : infoList.getInfoList()) {
					OldMessageXBeans mess = new OldMessageXBeans(info);
					if (!"".equals(infoList.getUri()) && infoList.getUri() != null )
						mess.setURI(infoList.getUri());
					else
						mess.setURI(or.getUri());
					messages.add(mess);
					infoMessages.add(mess);
				}
			}
		}
		if (or.getResult().getWarnings() != null) {
			for (Warninglist warningList : or.getResult().getWarnings().getWarninglistList()) {
				for (Warning warning : warningList.getWarningList()) {
					OldMessageXBeans mess = new OldMessageXBeans(warning);
					if (!"".equals(warningList.getUri()) && warningList.getUri() != null )
						mess.setURI(warningList.getUri());
					else
						mess.setURI(or.getUri());
					messages.add(mess);
					warningMessages.add(mess);
				}
			}
		}
		
	}
	
	public boolean evaluateXPath(String xpathQuery) {
		return false;
	}

	public String[] execQuery(String query) {
		return null;
	}

	public Date getDate() {
		if (or.getDate() != null)
			return or.getDate().getTime();
		return null;
	}

	public int getErrorCount() {
		return errorMessages.size();
	}

	public List<Message> getErrorMessages() {
		return errorMessages;
	}

	public List<Group> getGroupChildren(Group group) {
		return null;
	}

	public List<Group> getGroups() {
		return null;
	}

	public String getHTMLRequestUri() {
		if (requestURI != null) {
			String outputParamName = Framework.mapOfObserver.get(observerID).getParamOutputName();
			return requestURI.replaceAll("&?" + outputParamName + "=[^&]*", "");
		} else {
			return null;
		}
	}

	public int getInfoCount() {
		return infoMessages.size();
	}

	public List<Message> getInfoMessages() {
		return infoMessages;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Integer getRating() {
		return or.getRating();
	}

	public String getRequestUri() {
		return requestURI;
	}

	public int getStatus() {
		if (or.getPassed())
			return PASSED;
		return FAILED;
	}

	public String getURI() {
		return or.getUri();
	}

	public int getWarningCount() {
		return warningMessages.size();
	}

	public List<Message> getWarningMessages() {
		return warningMessages;
	}

	public boolean hasGroups() {
		return false;
	}

	public boolean isSetRating() {
		return or.isSetRating();
	}

	public String[] selectPath(String xpath) {
		return null;
	}

	public void setObserverId(String obsId) {
		observerID = obsId;
		
	}

	public String getObserverID() {
		return observerID;
	}
	
	public void setRequestUri(String uri) {
		requestURI = uri;
	}

	public boolean isPassed() {
		if (getStatus() == PASSED)
			return true;
		return false;
	}

}
