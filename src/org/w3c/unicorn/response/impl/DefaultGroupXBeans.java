// $Id: DefaultGroupXBeans.java,v 1.3 2009-10-20 12:41:46 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import org.apache.xmlbeans.XmlOptions;
import org.w3.x2009.x10.unicorn.observationresponse.GroupType;
import org.w3c.unicorn.response.Group;

public class DefaultGroupXBeans implements Group {

	private String title;
	
	private String description;
	
	private String name;
	
	private String parent;
	
	public DefaultGroupXBeans(GroupType group) {
		if (group.isSetDescription() && group.getDescription().validate())
			description = group.getDescription().xmlText(new XmlOptions().setUseDefaultNamespace()
					.setSavePrettyPrint()).replaceAll("</?xml-fragment[^>]*>", "").replaceAll("xmlns=\".*\"", "");
		title = group.getTitle();
		name = group.getName();
		if (group.isSetParent())
			parent = group.getParent();
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getParentName() {
		return parent;
	}

	public String getTitle() {
		return title;
	}

	public boolean hasParent() {
		if (parent != null)
			return true;
		return false;
	}

}
