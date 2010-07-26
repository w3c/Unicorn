//Author: Thomas Gambet
//(c) COPYRIGHT MIT, ERCIM and Keio, 2010.
//Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.ArrayList;
import java.util.List;

public class Output {

	private List<Group> groupList;

	public List<Group> getGroupList() {
		if (groupList == null)
			groupList = new ArrayList<Group>();
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	
}
