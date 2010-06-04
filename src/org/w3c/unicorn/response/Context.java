// $Id: Context.java,v 1.1 2009-10-16 16:25:00 tgambet Exp $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response;

public interface Context {
	
	public Integer getLine();
	
	public Integer getColumn();
	
	public Integer getLineMin();
	
	public Integer getLineMax();
	
	public Integer getColumnMin();
	
	public Integer getColumnMax();
	
	public Integer getOffset();
	
	public String getPosition();
	
	public String getContext();
	
	public String getURI();
	
}
