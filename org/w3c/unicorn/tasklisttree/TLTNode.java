package org.w3c.unicorn.tasklisttree;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class made to manage the execution
 * of a tasklist. It can contain several TLTExec objects and
 * TLTIf.
 * 
 * @author Barouh Jonathan & Batard Florent
 *
 */
public class TLTNode {
	
	private int level;
	private ArrayList<TLTExec> executionList; // list of observations to perform
	private ArrayList<TLTIf> ifList; // list of conditions and child nodes
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklisttree");
	
	public boolean bExpandingOrExpanded = false;
	
	/**
	 * Default constructor for a node.
	 *
	 */
	public TLTNode() {
		TLTNode.logger.trace("Constructor");
		level = 1;
		executionList = new ArrayList<TLTExec>();
		ifList = new ArrayList<TLTIf>();
	}
	
	/**
	 * Advanced constructor for a node.
	 * @param level The level of execution
	 * @param executionList The list of executions for the node
	 * @param ifList The list of tltIf for the node
	 */
	public TLTNode(int level,ArrayList<TLTExec> executionList, ArrayList<TLTIf> ifList) {
		TLTNode.logger.trace("Constructor");
		TLTNode.logger.trace("Level : " + level);
		TLTNode.logger.trace("Number of executions : " + executionList.size());
		TLTNode.logger.trace("Number of ifs : " + ifList.size());
		this.level = level;
		this.executionList  = executionList;
		this.ifList = ifList;
	}
	

	/**
	 * 
	 * @param level The level of execution
	 */
	public void setLevel(int level) {
		TLTNode.logger.trace("setLevel : " + level);
		this.level = level;
	}
	
	/**
	 * Adds an "exec" object to the executionList
	 * @param exec The "exec" to add
	 */
	public void addExec(TLTExec exec) {
		TLTNode.logger.trace("addExec : " + exec.getId());
		if (!executionList.contains(exec))
			executionList.add(exec);
	}
	
	/**
	 * Adds an "if" object to the ifList.
	 * @param tltIf The "if" to add
	 */
	public void addIf(TLTIf tltIf) {
		TLTNode.logger.trace("addIf : ");
		ifList.add(tltIf);
	}
	
	/**
	 * 
	 * @return The list of executions
	 */
	public ArrayList<TLTExec> getExecutionList() {
		TLTNode.logger.trace("getExecutionList");
		return executionList;
	}
	
	/**
	 * 
	 * @return The list of "ifs"
	 */
	public ArrayList<TLTIf> getIfList() {
		TLTNode.logger.trace("getIfList");
		return ifList;
	}
	
	/**
	 * 
	 * @return The level of execution
	 */
	public int getLevel() {
		TLTNode.logger.trace("getLevel");
		return level;
	}

	public String toString(){
		String res=new String("TLTNode ");
			for(TLTIf conds : this.ifList)
				res+=conds.toString();
			for(TLTExec exec : this.executionList)
				res+=exec.toString();
		return res;
	}
}
