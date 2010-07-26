// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklisttree.TLTCond;
import org.w3c.unicorn.tasklisttree.TLTExec;
import org.w3c.unicorn.tasklisttree.TLTIf;
import org.w3c.unicorn.tasklisttree.TLTNode;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Task<br />
 * Created: May 29, 2006 5:53:12 PM<br />
 */
public class Task {

	/**
	 * Id of the task
	 */
	private String sID;

	/**
	 * Longnames of the task
	 */
	private LocalizedString aLocalizedStringLongName;

	/**
	 * Descriptions of the task
	 */
	private LocalizedString aLocalizedStringDescription;

	/**
	 * Parameters of the task
	 */
	private Map<String, Parameter> mapOfTaskParameter;

	/**
	 * References to other tasks
	 */
	private List<String> listOfReference;
	
	/**
	 * List of mime-types that this task supports
	 */
	private List<String> supportedMimeTypes;

	/**
	 * Root of the execution level tree
	 */
	private TLTNode root;

	//private List<String> listOfOutput;

	private Output output;
	
	private static Log logger = LogFactory.getLog(Task.class);

	/**
	 * Creates a new Task.
	 */
	public Task() {
		this.sID = "";
		this.aLocalizedStringLongName = new LocalizedString();
		this.aLocalizedStringDescription = new LocalizedString();
		this.mapOfTaskParameter = new LinkedHashMap<String, Parameter>();
		this.listOfReference = new ArrayList<String>();
	}
	
	/**
	 * Creates a new Task.
	 * 
	 * @param aLocalizedStringDescription
	 * @param sID
	 * @param aLocalizedStringLongName
	 * @param mapOfParameter
	 * @param mapOfObservation
	 */
	public Task(final String sID,
			final LocalizedString aLocalizedStringDescription,
			final LocalizedString aLocalizedStringLongName,
			final Map<String, Parameter> mapOfParameter) {
		super();
		this.aLocalizedStringDescription = aLocalizedStringDescription;
		this.sID = sID;
		this.aLocalizedStringLongName = aLocalizedStringLongName;
		this.mapOfTaskParameter = mapOfParameter;
		this.listOfReference = new ArrayList<String>();
	}

	/**
	 * Allows to display the tree of execution level
	 * 
	 * @param root
	 *            the node to display
	 */
	public void displayTree(TLTNode root) {
		for (TLTExec exec : root.getExecutionList()) {
			logger.trace(exec);
		}
		for (TLTIf ifs : root.getIfList()) {
			displayTree(ifs.getIfOk());
			for (TLTCond conds : ifs.getCondArray()) {
				logger.trace(conds);
			}
			displayTree(ifs.getIfNotOk());
		}
	}

	/**
	 * Get the root of the execution level tree
	 * 
	 * @return the root of the tree
	 */
	public TLTNode getTree() {
		return this.root;
	}

	/**
	 * Set the root of the execution level tree
	 */
	public void setTree(TLTNode root) {
		this.root = root;
	}

	/**
	 * Returns the internationalized description of this task
	 * 
	 * @return Returns the description.
	 */
	public LocalizedString getDescription() {
		return this.aLocalizedStringDescription;
	}

	/**
	 * Returns a localized description of this task
	 * 
	 * @param sLang
	 *            requested locale (e.g. en, fr, zn-ch, ...)
	 * @return the localized description corresponding to the locale
	 */
	public String getDescription(final String sLang) {
		final String sDesc = this.aLocalizedStringDescription
				.getLocalization(sLang);
		if (sDesc == null) {
			return "";
		}
		return sDesc;
	}

	/**
	 * Sets the internationalized description of this task
	 * 
	 * @param aLocalizedString
	 *            The description to set.
	 */
	public void setDescription(final LocalizedString aLocalizedString) {
		this.aLocalizedStringDescription = aLocalizedString;
	}

	/**
	 * Adds a localized description to this task
	 * 
	 * @param sLang
	 *            the locale of the description
	 * @param sDesc
	 *            the localized description of this task
	 */
	public void addDescription(final String sLang, final String sDesc) {
		this.aLocalizedStringDescription.addLocalization(sLang, sDesc);
	}

	/**
	 * Gets the id of this task
	 * 
	 * @return Returns the id.
	 */
	public String getID() {
		return this.sID;
	}

	/**
	 * Sets the id of this task
	 * 
	 * @param sID
	 *            The id to set.
	 */
	public void setID(final String sID) {
		this.sID = sID;
	}

	/**
	 * Returns the itnernationalized long name of this task
	 * 
	 * @return Returns the longname.
	 */
	public LocalizedString getLongName() {
		return this.aLocalizedStringLongName;
	}

	/**
	 * Returns a localized long name of this task
	 * 
	 * @param sLang
	 *            requested locale (e.g. en, fr, zn-ch, ...)
	 * @return the localized long name corresponding to the locale
	 */
	public String getLongName(final String sLang) {
		final String sName = this.aLocalizedStringLongName
				.getLocalization(sLang);
		if (sName == null) {
			return "";
		}
		return sName;
	}

	/**
	 * Sets the internationalized long name of this task
	 * 
	 * @param aLocalizedString
	 *            The longname to set.
	 */
	public void setLongName(final LocalizedString aLocalizedString) {
		this.aLocalizedStringLongName = aLocalizedString;
	}

	/**
	 * Adds a localized long name to this task
	 * 
	 * @param sLang
	 *            the locale of the long name
	 * @param sLongName
	 *            the localized long name of this task
	 */
	public void addLongName(final String sLang, final String sLongName) {
		this.aLocalizedStringLongName.addLocalization(sLang, sLongName);
	}

	/**
	 * Returns the parameters list of this task
	 * 
	 * @return Returns the parameters.
	 */
	public Map<String, Parameter> getMapOfParameter() {
		return this.mapOfTaskParameter;
	}
	
	public Map<String, Parameter> getMapOfParameter(String uiLevel) {
		//Map<String, Parameter> map = new LinkedHashMap<String, Parameter>();
		Hashtable <String, Parameter> map = new Hashtable <String, Parameter>();
		for (String key : mapOfTaskParameter.keySet()) {
			if (mapOfTaskParameter.get(key).getUiLevel().toString().equals(uiLevel)) {
				map.put(key, mapOfTaskParameter.get(key));
			}
		}
		return map;
	}

	/**
	 * Sets the parameters list of this task
	 * 
	 * @param mapOfParameter
	 *            The parameters to set.
	 */
	public void setMapOfParameter(final Map<String, Parameter> mapOfParameter) {
		this.mapOfTaskParameter = mapOfParameter;
	}

	/**
	 * Adds a parameter to this task
	 * 
	 * @param aParameter
	 */
	public void addParameter(final Parameter aParameter) {
		this.mapOfTaskParameter.put(aParameter.getName(), aParameter);
	}

	/**
	 * Returns a list of tasknames referenced bye this task
	 * 
	 * @return Returns the references.
	 */
	public List<String> getListOfReference() {
		return this.listOfReference;
	}

	public List<Observer> getAllObservers() {
		if (this.getTree() != null) {
			return this.getTree().getAllObservers();
		}
		return new ArrayList<Observer>();
	}

	// MimeType's equals() doesn't work as expected
	// so it's easier to store the String representation
	// of mime types :-/
	public List<String> getSupportedMimeTypes() {
		if (supportedMimeTypes == null) {
			supportedMimeTypes = new ArrayList<String>();
			List<Observer> observers = getAllObservers();
			for (Observer o : observers) {
				List<MimeType> mimes = o.getSupportedMimeTypes();
				for (MimeType m : mimes) {
					if (!supportedMimeTypes.contains(m.toString())) {
						supportedMimeTypes.add(m.toString());
					}
				}
			}
		}
		return supportedMimeTypes;
	}

	/**
	 * 
	 */
	public TLTNode expandNode(final Map<String, Task> mapOfTask, TLTNode aRoot) {
		aRoot.bExpandingOrExpanded = true;

		TLTNode finalRoot = new TLTNode();

		for (TLTExec exec : aRoot.getExecutionList()) {
			if (exec.getType().equals("subtask")) {
				finalRoot = mergeNode(mapOfTask, finalRoot, mapOfTask.get(
						exec.getValue()).getTree());
			} else if (exec.getType().equals("observation")) {
				finalRoot.addExec(exec);
			}
		}

		for (TLTIf tltIf : aRoot.getIfList()) {
			tltIf = expandIf(mapOfTask, tltIf);
			finalRoot.addIf(tltIf);
		}

		return finalRoot;
	}

	public TLTNode mergeNode(final Map<String, Task> mapOfTask,
			TLTNode firstNode, TLTNode secondNode) {
		TLTNode finalNode = firstNode;
		for (TLTExec exec : secondNode.getExecutionList()) {
			if (exec.getType().equals("observation")) {
				finalNode.addExec(exec);
			} else if (exec.getType().equals("subtask")) {
				TLTNode newNode = mapOfTask.get(exec.getValue()).getTree();
				if (!mapOfTask.get(exec.getValue()).getTree().bExpandingOrExpanded) {
					newNode = expandNode(mapOfTask, mapOfTask.get(
							exec.getValue()).getTree());
				}
				finalNode = mergeNode(mapOfTask, finalNode, newNode);
			}
		}
		for (TLTIf tltIf : secondNode.getIfList()) {
			tltIf = expandIf(mapOfTask, tltIf);
			finalNode.addIf(tltIf);
		}
		return finalNode;
	}

	public TLTIf expandIf(final Map<String, Task> mapOfTask, TLTIf tltIf) {
		if (!tltIf.getIfOk().bExpandingOrExpanded) {
			TLTNode tltIfOk = expandNode(mapOfTask, tltIf.getIfOk());
			tltIf.setIfOk(tltIfOk);
		}
		if (!tltIf.getIfNotOk().bExpandingOrExpanded) {
			TLTNode tltIfNotOk = expandNode(mapOfTask, tltIf.getIfNotOk());
			tltIf.setIfNotOk(tltIfNotOk);
		}
		return tltIf;
	}

	/**
	 * Adds a reference to another task
	 * 
	 * @param sReference
	 *            the referenced task
	 */
	public void addReference(final String sReference) {
		this.listOfReference.add(sReference);
	}

	@Override
	public String toString() {
		final int iStringBufferSize = 5000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);
		
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append(sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("\tparameters:\n"); //.append(this.mapOfTaskParameter);
		for (String key : mapOfTaskParameter.keySet()) {
			Parameter param = mapOfTaskParameter.get(key);
			aStringBuffer.append("\t\t" + key + " (" + param.getUiLevel() + "-" + param.getType() + ") => \n");
			if (param.getMapOfValue() != null) {
				for (String key1: param.getMapOfValue().keySet()) {
					aStringBuffer.append("\t\t\t" + key1 + " => " + mapOfTaskParameter.get(key).getMapOfValue().get(key1) + "\n");
				}
			}
		}
		aStringBuffer.append("\treferences:").append(this.listOfReference);

		return aStringBuffer.toString();
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public Output getOutput() {
		return output;
	}

}
