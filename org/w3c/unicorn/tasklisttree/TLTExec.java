package org.w3c.unicorn.tasklisttree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3.unicorn.tasklist.impl.ParamTypeImpl;
import org.w3.unicorn.tasklist.impl.ValueTypeImpl;

/**
 * Class made to manage the XML type execType of the tasklist.
 * Included in a TLTNode, its value attribute corresponds to an
 * observation which is to be launched. 
 * 
 * @author Barouh Jonathan & Batard Florent
 *
 */
public class TLTExec {

	private String id;
	private ValueTypeImpl value;
	private ParamTypeImpl param;
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklisttree");
	
	/**
	 * Constructor for a TLTExec.
	 * @param id The id of the exec
	 * @param value The observer to run
	 * @param param The parameter of the exec
	 */
	public TLTExec(String id, ValueTypeImpl value, ParamTypeImpl param) {
		TLTExec.logger.trace("Constructor");
		TLTExec.logger.trace("Id : " + id);
		TLTExec.logger.trace("Value : " + value);
		TLTExec.logger.trace("Param : " + param);
		this.id = id;
		this.value = value;
		this.param = param;
	}
	
	/**
	 * 
	 * @param id The id of the exec
	 */
	public void setId(String id) {
		TLTExec.logger.trace("setId : " + id);
		this.id = id;
	}
	
	/**
	 * 
	 * @param value The observer to run
	 */
	public void setValue(ValueTypeImpl value) {
		TLTExec.logger.trace("setValue : " + value);
		this.value = value;
	}
	
	/**
	 * 
	 * @param param The parameter of the exec
	 */
	public void setParam(ParamTypeImpl param) {
		TLTExec.logger.trace("setParam : " + param);
		this.param = param;
	}
	
	/**
	 * 
	 * @return The id of the exec
	 */
	public String getId() {
		TLTExec.logger.trace("getId");
		return id;
	}
	
	/**
	 * 
	 * @return The observer to run
	 */
	public ValueTypeImpl getValue() {
		TLTExec.logger.trace("getValue");
		return value;
	}
	
	/**
	 * 
	 * @return The parameter of the exec
	 */
	public ParamTypeImpl getParam() {
		TLTExec.logger.trace("getParam");
		return param;
	}
}
