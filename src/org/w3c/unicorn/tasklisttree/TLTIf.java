// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.exceptions.UnicornException;

/**
 * Class made to manage the XML type : ifType in the tasklist. Included in a
 * TLTNode, it contains a condition and two child nodes : one if the condition
 * passed and the other if it failed.
 * 
 * @author Barouh Jonathan & Batard Florent
 * 
 */
public class TLTIf {

	private ArrayList<TLTCond> conds;

	private TLTNode ifOk;

	private TLTNode ifNotOk;

	private static final Log logger = LogFactory.getLog(TLTIf.class);

	/**
	 * Default constructor for a TLTIf.
	 * 
	 */
	public TLTIf() {
		logger.trace("Constructor");
		this.conds = new ArrayList<TLTCond>();
		this.ifOk = new TLTNode();
		this.ifNotOk = new TLTNode();
	}

	/**
	 * Constructor for a TLTIf with only an ifOk node.
	 * 
	 * @param id
	 * @param cond
	 *            The condition to check
	 * @param ifOK
	 *            The next node if the condition is ok
	 */
	public TLTIf(ArrayList<TLTCond> cond, TLTNode ifOk) {
		logger.trace("Constructor");
		logger.trace("Cond : ");
		this.conds = cond;
		this.ifOk = ifOk;
		this.ifNotOk = new TLTNode();
	}

	/**
	 * Complete constructor for a TLTif.
	 * 
	 * @param id
	 * @param cond
	 *            The condition to check
	 * @param ifOk
	 *            The next node if the condition is ok
	 * @param ifNotOK
	 *            The next node if the condition is not ok
	 */
	public TLTIf(ArrayList<TLTCond> cond, TLTNode ifOk, TLTNode ifNotOK) {
		logger.trace("Constructor");
		logger.trace("Cond : ");
		this.conds = cond;
		this.ifOk = ifOk;
		this.ifNotOk = ifNotOK;
	}

	/**
	 * Check the conditions of the if branch it makes a OR between all
	 * conditions
	 * 
	 * @param unicornCall
	 *            the UnicornCall object to check
	 * @return whether or not the conditions are true
	 * @throws UnicornException
	 */
	public boolean check(UnicornCall unicornCall) throws UnicornException {
		for (TLTCond cond : conds) {
			if (cond.check(unicornCall)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets the child node corresponding to the "ok" case
	 * 
	 * @param ifOk
	 */
	public void setIfOk(TLTNode ifOk) {
		logger.trace("setIfOk");
		this.ifOk = ifOk;
	}

	/**
	 * Sets the child node corresponding to the "notOk" case
	 * 
	 * @param ifNotOk
	 */
	public void setIfNotOk(TLTNode ifNotOk) {
		logger.trace("setIfNotOk");
		this.ifNotOk = ifNotOk;
	}

	/**
	 * Sets the condition to check.
	 * 
	 * @param cond
	 */
	public void addCond(TLTCond cond) {
		logger.trace("addCond : " + cond.getId());
		this.conds.add(cond);
	}

	/**
	 * 
	 * @return The child node corresponding to the "ok" case
	 */
	public TLTNode getIfOk() {
		logger.trace("getIfOk");
		return ifOk;
	}

	/**
	 * 
	 * @return The child node corresponding to the "notOk" case
	 */
	public TLTNode getIfNotOk() {
		logger.trace("getIfNotOk");
		return ifNotOk;
	}

	/**
	 * 
	 * @return The condition
	 */
	public ArrayList<TLTCond> getCondArray() {
		logger.trace("getCond");
		return conds;
	}

	@Override
	public String toString() {
		String res = new String("TLTIf ");
		for (TLTCond conds : this.conds) {
			res += conds.toString();
		}
		return res;
	}

}
