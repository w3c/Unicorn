// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
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
	
	private String lang;
	
	public DefaultGroupXBeans(GroupType group) {
		if (group.isSetDescription() && group.getDescription().validate())
			description = group.getDescription().xmlText(new XmlOptions().setUseDefaultNamespace()
					.setSavePrettyPrint()).replaceAll("</?xml-fragment[^>]*>", "").replaceAll("xmlns=\".*\"", "");
		title = group.getTitle();
		name = group.getName();
		if (group.isSetParent())
			parent = group.getParent();
		lang = group.getLang();
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

	public String getLang() {
		return lang;
	}

}
