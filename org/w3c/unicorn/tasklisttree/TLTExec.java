package org.w3c.unicorn.tasklisttree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3.unicorn.tasklist.ParamType;
import org.w3c.unicorn.contract.Observer;

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
	private String value;
	private String type;
	private ParamType param;
	private Observer observer;
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklisttree");
	
	/**
	 * Constructor for a TLTExec.
	 * @param id The id of the exec
	 * @param value The observer to run
	 * @param param The parameter of the exec
	 */
	public TLTExec(String id,Observer obs, String value,String type, ParamType param) {
      String toTrace = "constructor(" + id + ", ";
		if (type.equals("observation")) {
			toTrace += obs.getID() + ", ";
			this.observer=obs;
		}
		TLTExec.logger.trace(toTrace + type + ", " + value + ", " + param + ")"); 
		this.id = id;
		this.value = value;
		this.type=type;
		this.param = param;

	}
	
	/**
	 * 
	 * @param id The id of the exec
	 */
	public void setObserver(Observer obs) {
		TLTExec.logger.trace("setObserver(" + obs.getID() + ")");
		this.observer = obs;
	}
	
	/**
	 * 
	 */
	public Observer getObserver() {
		TLTExec.logger.trace("getObserver()");
		return this.observer;
	}
	
	
	/**
	 * 
	 * @param id The id of the exec
	 */
	public void setId(String id) {
		TLTExec.logger.trace("setId(" + id + ")");
		this.id = id;
	}
	
	/**
	 * 
	 * @param value The observer to run
	 */
	public void setValue(String value) {
		TLTExec.logger.trace("setValue(" + value + ")");
		this.value = value;
	}
	
	/**
	 * 
	 * @param value The observer to run
	 */
	public void setType(String type) {
		TLTExec.logger.trace("setType(" + type + ")");
		this.type = type;
	}
	
	/**
	 * 
	 * @param param The parameter of the exec
	 */
	public void setParam(ParamType param) {
		TLTExec.logger.trace("setParam(" + param + ")");
		this.param = param;
	}
	
	/**
	 * 
	 * @return The id of the exec
	 */
	public String getId() {
		TLTExec.logger.trace("getId()");
		return id;
	}
	
	/**
	 * 
	 * @return The observer or subtask to run
	 */
	public String getValue() {
		TLTExec.logger.trace("getValue()");
		return value;
	}
	
	
	/**
	 * 
	 * @return The type of execution Observer or subtask
	 */
	public String getType() {
		TLTExec.logger.trace("getType()");
		return type;
	}
	
	/**
	 * 
	 * @return The parameter of the exec
	 */
	public ParamType getParam() {
		TLTExec.logger.trace("getParam()");
		return param;
	}
	
	public String toString(){
		return "TLTExec{id: "+this.id+", value: "+this.value+", type: "+this.getType()+", param: "+this.param+"}";
	}
}
