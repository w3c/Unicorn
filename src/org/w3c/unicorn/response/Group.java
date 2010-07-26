// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response;

public interface Group {

	public String getName();
	
	public String getParentName();
	
	public String getTitle();
	
	public String getDescription();
	
	public boolean hasParent();
	
	public String getLang();
	
}