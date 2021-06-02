// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;

import org.w3c.unicorn.response.Context;
import org.w3c.unicorn.response.Message;

public class ValidatorNuMessage implements Message {
	
	private List<Context> contexts = new ArrayList<Context>();
	
	private String description;
	
	private String title;
	
	private int level;
	
	private int type;
	
	private String uri;
	
	private String group;
	
	private String lang;

    public ValidatorNuMessage(JsonObject jsonMessage, String uri) {
        this.uri = uri;
      String respType = jsonMessage.getString("type");
      switch(respType) {
      case "info":
          type = Message.INFO;
          try {
              String subType  = jsonMessage.getString("subtype");
              if (subType == "warning") {
                  type = Message.WARNING;
              }
          } catch(NullPointerException e) {
              // No subtype, don't do anything
          }
          break;
      case "error":
          type = Message.ERROR;
          break;
      }
      
      title = jsonMessage.getString("message").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;").replace("\"", "&quot;");

      contexts.add(new ValidatorNuContext(jsonMessage, uri));
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
