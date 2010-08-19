// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;

/**
 * Class made to manage the execution of a tasklist. It can contain several
 * TLTExec objects and TLTIf.
 * 
 * @author Barouh Jonathan & Batard Florent
 * 
 */
public class TLTNode {

	private int NodeID;

	private ArrayList<TLTExec> executionList; // list of observations to
												// perform

	private ArrayList<TLTIf> ifList; // list of conditions and child nodes

	private static final Log logger = LogFactory.getLog(TLTNode.class);

	public boolean bExpandingOrExpanded = false;

	/**
	 * Default constructor for a node.
	 * 
	 */
	public TLTNode() {
		logger.trace("Constructor");
		NodeID = 0;
		executionList = new ArrayList<TLTExec>();
		ifList = new ArrayList<TLTIf>();
	}

	/**
	 * Advanced constructor for a node.
	 * 
	 * @param level
	 *            The level of execution
	 * @param executionList
	 *            The list of executions for the node
	 * @param ifList
	 *            The list of tltIf for the node
	 */
	public TLTNode(int NodeID, ArrayList<TLTExec> executionList,
			ArrayList<TLTIf> ifList) {
		logger.trace("Constructor");
		logger.trace("NodeID : " + NodeID);
		logger.trace("Number of executions : " + executionList.size());
		logger.trace("Number of ifs : " + ifList.size());
		this.NodeID = NodeID;
		this.executionList = executionList;
		this.ifList = ifList;
	}

	/**
	 * 
	 * @param level
	 *            The level of execution
	 */
	public void setID(int NodeID) {
		logger.trace("setID : " + NodeID);
		this.NodeID = NodeID;
	}

	/**
	 * Adds an "exec" object to the executionList
	 * 
	 * @param exec
	 *            The "exec" to add
	 */
	public void addExec(TLTExec exec) {
		logger.trace("addExec : " + exec.getId());
		if (!executionList.contains(exec)) {
			executionList.add(exec);
		}
	}

	/**
	 * Adds an "if" object to the ifList.
	 * 
	 * @param tltIf
	 *            The "if" to add
	 */
	public void addIf(TLTIf tltIf) {
		logger.trace("addIf : ");
		ifList.add(tltIf);
	}

	/**
	 * 
	 * @return The list of executions
	 */
	public List<TLTExec> getExecutionList() {
		logger.trace("getExecutionList");
		return executionList;
	}

	public List<Observer> getAllObservers() {
		List<Observer> res = new ArrayList<Observer>();
		getAllObserversRec(res);
		return res;
	}

	private void getAllObserversRec(List<Observer> res) {
		// Add every observers directly under this TLTNode
		for (TLTExec exec : this.executionList) {
			Observer o = exec.getObserver();
			if (!res.contains(o)) {
				res.add(o);
			}
		}

		// Recursively add observers in <if> elements
		for (TLTIf ifNode : this.ifList) {
			ifNode.getIfOk().getAllObserversRec(res);
			ifNode.getIfNotOk().getAllObserversRec(res);
		}
	}

	/**
	 * 
	 * @return The list of "ifs"
	 */
	public ArrayList<TLTIf> getIfList() {
		logger.trace("getIfList");
		return ifList;
	}

	/**
	 * 
	 * @return The level of execution
	 */
	public int getID() {
		logger.trace("getLevel");
		return NodeID;
	}

	@Override
	public String toString() {
		String res = new String("TLTNode level" + this.NodeID + " ");
		for (TLTIf conds : this.ifList) {
			res += conds.toString();
		}
		for (TLTExec exec : this.executionList) {
			res += exec.toString();
		}
		return res;
	}
}
