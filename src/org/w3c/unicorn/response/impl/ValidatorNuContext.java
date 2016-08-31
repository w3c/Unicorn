// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import javax.json.JsonObject;

import org.w3c.unicorn.response.Context;

public class ValidatorNuContext implements Context {

	private String value;
	private Integer lineMin;
	private Integer lineMax;
	private Integer colMin;
	private Integer colMax;
  private String uri;
	
    public ValidatorNuContext(JsonObject jsonMessage, String uri) {
      this.uri = uri;
      try {
          value = jsonMessage.getString("extract").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&apos;").replace("\"", "&quot;");
      } catch (NullPointerException e) {}
      try {
          lineMin = jsonMessage.getInt("firstLine");
      } catch (NullPointerException e) {}
      try {
          lineMax = jsonMessage.getInt("lastLine");
      } catch (NullPointerException e) {}
      try {
          colMin = jsonMessage.getInt("firstCol");
      } catch (NullPointerException e) {}
      try {
          colMax = jsonMessage.getInt("lastCol");
      } catch (NullPointerException e) {}
  }
	
	public Integer getLine() {
      if (lineMin != null) {
          return lineMin;
      }
      return lineMax;
	}
	
	public Integer getColumn() {
      if (colMin != null) {
          return colMin;
      }
      return colMax;
	}

	public Integer getLineMin() {
		return lineMin;
	}
	
	public Integer getLineMax() {
		return lineMax;
	}
	
	public Integer getColumnMin() {
		return colMin;
	}
	
	public Integer getColumnMax() {
		return colMax;
	}
	
	public String getContext() {
		return value;
	}
	
	public Integer getOffset() {
		return null;
	}
	
	public String getPosition() {
		return null;
	}
	
	public String getURI() {
		return uri;
	}
	
}
