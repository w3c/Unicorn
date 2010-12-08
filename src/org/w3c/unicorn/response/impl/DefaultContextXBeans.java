// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.response.impl;

import org.apache.xmlbeans.XmlOptions;
import org.w3.x2009.x10.unicorn.observationresponse.ContextType;
import org.w3c.unicorn.response.Context;

public class DefaultContextXBeans implements Context {

	private ContextType context;
	
	private String value;
	private Integer lineMin;
	private Integer lineMax;
	private Integer colMin;
	private Integer colMax;
	
	public DefaultContextXBeans(ContextType context) {
		
		this.context = context;
		
		if (context.isSetLineRange()) {
			String[] values = context.getLineRange().split("-");
			lineMin = Integer.parseInt(values[0]);
			lineMax = Integer.parseInt(values[1]);
		}
		if (context.isSetColumnRange()) {
			String[] values = context.getColumnRange().split("-");
			colMin = Integer.parseInt(values[0]);
			colMax = Integer.parseInt(values[1]);
		}
		
		XmlOptions opts = new XmlOptions();
		opts.setSaveCDataLengthThreshold(10000000);
		opts.setSaveCDataEntityCountThreshold(-1);
		opts.setUseDefaultNamespace();
		value = context.xmlText(opts).replaceAll("[ ]*xmlns=\"[^>]*\"", "").replaceAll("</?xml-fragment[^>]*>", "");
	}
	
	public Integer getLine() {
		if (context.isSetLine())
			return context.getLine().intValue();
		return null;
	}
	
	public Integer getColumn() {
		if (context.isSetColumn())
			return context.getColumn().intValue();
		return null;
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
		if (context.isSetOffset())
			return context.getOffset().intValue();
		return null;
	}
	
	public String getPosition() {
		return context.getPosition();
	}
	
	public String getURI() {
		return context.getRef();
	}
	
}
