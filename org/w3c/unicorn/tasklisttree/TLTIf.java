package org.w3c.unicorn.tasklisttree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class made to manage the XML type : ifType in the tasklist.
 * Included in a TLTNode, it contains a condition and two child
 * nodes : one if the condition passed and the other if it failed.
 * 
 * @author Barouh Jonathan & Batard Florent
 *
 */
public class TLTIf {

	private String id;
	private TLTCond cond;
	private TLTNode ifOk;
	private TLTNode ifNotOk;
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklisttree");
	
	/**
	 * Default constructor for a TLTIf.
	 *
	 */
	public TLTIf() {
		TLTIf.logger.trace("Constructor");
		this.id = "defaultIf";
		this.cond = new TLTCond();
		this.ifOk = new TLTNode();
		this.ifNotOk = new TLTNode();
	}
	
	/**
	 * Constructor for a TLTIf with only an ifOk node.
	 * @param id
	 * @param cond The condition to check
	 * @param ifOK The next node if the condition is ok
	 */
	public TLTIf(String id, TLTCond cond, TLTNode ifOk) {
		TLTIf.logger.trace("Constructor");
		TLTIf.logger.trace("Id : " + id);
		TLTIf.logger.trace("Cond : " + cond.getId());
		this.id = id;
		this.cond = cond;
		this.ifOk = ifOk;
		this.ifNotOk = new TLTNode();
	}
	
	/**
	 * Complete constructor for a TLTif.
	 * @param id
	 * @param cond The condition to check
	 * @param ifOk The next node if the condition is ok
	 * @param ifNotOK The next node if the condition is not ok
	 */
	public TLTIf(String id,TLTCond cond, TLTNode ifOk, TLTNode ifNotOK) {
		TLTIf.logger.trace("Constructor");
		TLTIf.logger.trace("Id : " + id);
		TLTIf.logger.trace("Cond : " + cond.getId());
		this.id = id;
		this.cond = cond;
		this.ifOk = ifOk;
		this.ifNotOk = ifNotOK;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		TLTIf.logger.trace("setId : " + id);
		this.id = id;
	}
	
	/**
	 * Sets the child node corresponding to the "ok" case
	 * @param ifOk
	 */
	public void setIfOk(TLTNode ifOk) {
		TLTIf.logger.trace("setIfOk");
		this.ifOk = ifOk;
	}

	/**
	 * Sets the child node corresponding to the "notOk" case
	 * @param ifNotOk
	 */
	public void setIfNotOk(TLTNode ifNotOk) {
		TLTIf.logger.trace("setIfNotOk");
		this.ifNotOk = ifNotOk;
	}
	
	/**
	 * Sets the condition to check.
	 * @param cond
	 */
	public void setCond(TLTCond cond) {
		TLTIf.logger.trace("setCond : " + cond.getId());
		this.cond = cond;
	}
	
	/**
	 * 
	 * @return The child node corresponding to the "ok" case
	 */
	public TLTNode getIfOk() {
		TLTIf.logger.trace("getIfOk");
		return ifOk;
	}
	
	/**
	 * 
	 * @return The child node corresponding to the "notOk" case
	 */
	public TLTNode getIfNotOk() {
		TLTIf.logger.trace("getIfNotOk");
		return ifNotOk;
	}
	
	/**
	 * 
	 * @return The condition
	 */
	public TLTCond getCond() {
		TLTIf.logger.trace("getCond");
		return cond;
	}
	
	public String getId() {
		TLTIf.logger.trace("getId");
		return id;
	}
	
	
	
}
