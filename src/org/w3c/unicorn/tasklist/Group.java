//Author: Thomas Gambet
//(c) COPYRIGHT MIT, ERCIM and Keio, 2010.
//Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.List;

public class Group {

	public enum Type {
		FIRSTPASSED
	}
	
	private Type type;
	
	private List<String> observationList; 

	public boolean isSetType() {
		if (type != null)
			return true;
		return false;
	}

	public List<String> getObservationList() {
		return observationList;
	}

	public void setObservationList(List<String> observationList) {
		this.observationList = observationList;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setType(String type) {
		if (type.equalsIgnoreCase(Type.FIRSTPASSED.toString()))
			this.type = Type.FIRSTPASSED;
	}
	
}
