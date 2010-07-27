// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
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
