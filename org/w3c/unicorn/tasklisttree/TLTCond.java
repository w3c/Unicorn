package org.w3c.unicorn.tasklisttree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;

/**
 * Class made to manage the XML type condType of the tasklist.
 * Included in a TLTIf, it will decide of the next node to be
 * executed. 
 * 
 * @author Barouh Jonathan & Batard Florent
 *
 */
public class TLTCond {
	
	private String id;
	private Observer observer;
	private EnumCondType type; 
	private boolean result;
	private String value;
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklisttree");
	
	
	/**
	 * Advanced constructor for a TLTCond.
	 * @param id 
	 * @param observer The name of the observer corresponding to the condition
	 * @param type The type of the condition
	 */
	public TLTCond(String id, Observer observer, EnumCondType type, String value) {
		TLTCond.logger.trace("Constructor");
		TLTCond.logger.trace("Id : " + id);
		TLTCond.logger.trace("Observer : " + observer);
		TLTCond.logger.trace("Type : " + type.value());
		this.id = id;
		this.observer = observer;
		this.type = type;
	}
	
	/**
	 * Default constructor for a TLTCond.
	 *
	 */
	public TLTCond() {
		TLTCond.logger.trace("Constructor");
	}
	
	/**
	 * 
	 * @param id The id of the condition
	 */
	public void setId(String id) {
		TLTCond.logger.trace("setId : " + id);
		this.id = id;
	}
	
	/**
	 * 
	 * @param observer The observer the condition depends on
	 */
	public void setObserver(Observer observer) {
		TLTCond.logger.trace("setObserver : " + observer);
		this.observer = observer;
	}
	
	/**
	 * 
	 * @param result The result of the test
	 */
	public void setResult(boolean result) {
		TLTCond.logger.trace("setResult : " + result);
		this.result = result;
	}
	/**
	 * 
	 * @param type The type of the condition
	 */
	public void setType(EnumCondType type){ 
		TLTCond.logger.trace("setType : " + type.value());
		this.type = type;
		}
	
	
	/**
	 * 
	 * @param type The type of the condition
	 */
	public void setType(String type){ 
		TLTCond.logger.trace("setType : " + type);
		for(EnumCondType val : EnumCondType.values()){
			if(val.value().equals(type))this.type=val;
		}
		}
	
	/**
	 * 
	 * @param value value of the condition
	 */
	public void setValue(String value){
		TLTCond.logger.trace("setValue : " + value);
		this.value=value;
	}
	
	/**
	 * @return The id of the condition
	 */
	public String getId() {
		TLTCond.logger.trace("getId");
		return id;
	}
	
	/**
	 * 
	 * @return The observer the condition depends on
	 */
	public Observer getObserver() {
		TLTCond.logger.trace("getObserver");
		return observer;
	}
	
	/**
	 * 
	 * @return The result of the test
	 */
	public boolean getResult() {
		TLTCond.logger.trace("getResult");
		return result;
	}
	
	/**
	 * 
	 * @return The type of the condition
	 */
	public EnumCondType getType(){ 
		TLTCond.logger.trace("getType");
		return type;	
	}
	
	/**
	 * 
	 * @return The value of the condition
	 */
	public String getValue(){ 
		TLTCond.logger.trace("getValue");
		return value;	
	}
	
	public String toString(){
		return new String("TLTCond "+this.id+" observer "+this.observer+" value "+this.value);
	}
	
}
