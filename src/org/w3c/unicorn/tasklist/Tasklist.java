//Author: Thomas Gambet
//(c) COPYRIGHT MIT, ERCIM and Keio, 2010.
//Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.LinkedHashMap;

import org.w3c.unicorn.Framework;

public class Tasklist extends LinkedHashMap<String, Task> {

	private static final long serialVersionUID = -868579272922431097L;

	private String defaultTaskId;

	public Task getDefaultTask() {
		return Framework.mapOfTask.get(getDefaultTaskId());
	}
	
	public String getDefaultTaskId() {
		if (defaultTaskId == null) {
			String id = Framework.mapOfTask.values().toArray(new Task[0])[0].getID();
			Framework.logger.error("There is no default task defined in task rdf files. Returning first found id: \"" + id + "\" . Please add ucn:default=\"true\" to the default task.");
			this.defaultTaskId = id;
			return Framework.mapOfTask.values().toArray(new Task[0])[0].getID();
		}			
		else
			return defaultTaskId;
	}

	public void setDefaultTaskId(String defaultTaskId) {
		this.defaultTaskId = defaultTaskId;
	}

	@Override
	public java.lang.String toString() {
		String s = "";
		for (String key : this.keySet()) {
			s += get(key) + "\n";
		}
		return s;
	}

}
