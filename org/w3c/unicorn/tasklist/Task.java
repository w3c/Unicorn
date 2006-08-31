// $Id: Task.java,v 1.1.1.1 2006-08-31 09:09:26 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Task<br />
 * Created: May 29, 2006 5:53:12 PM<br />
 */
public class Task {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklist");

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
	 * Observation of the task
	 */
	private Map<String, Observation> mapOfObservation;
	
	/**
	 * Parameters of the task
	 */
	private Map<String, Parameter> mapOfTaskParameter;
	
	/**
	 * References to other tasks
	 */
	private List<String> listOfReference;
	
	/**
	 * Used to expand the task
	 */
	private boolean bExpandingOrExpanded = false;
	
	/**
	 * Creates a new Task.
	 */
	public Task () {
		this.sID = "";
		this.aLocalizedStringLongName = new LocalizedString();
		this.aLocalizedStringDescription = new LocalizedString();
		this.mapOfObservation = new LinkedHashMap<String, Observation>();
		this.mapOfTaskParameter = new LinkedHashMap<String, Parameter>();
		this.listOfReference = new ArrayList<String>();
	}
	
	/**
	 * Creates a new Task.
	 * @param aLocalizedStringDescription
	 * @param sID
	 * @param aLocalizedStringLongName
	 * @param mapOfParameter
	 * @param mapOfObservation
	 */
	public Task (
			final String sID,
			final LocalizedString aLocalizedStringDescription, 
			final LocalizedString aLocalizedStringLongName,
			final Map<String, Parameter> mapOfParameter,
			final Map<String, Observation> mapOfObservation) {
		super();
		this.aLocalizedStringDescription = aLocalizedStringDescription;
		this.sID = sID;
		this.aLocalizedStringLongName = aLocalizedStringLongName;
		this.mapOfTaskParameter = mapOfParameter;
		this.mapOfObservation = mapOfObservation;
		this.listOfReference = new ArrayList<String>();
	}
	
	/**
	 * Returns the internationalized description of this task
	 * @return Returns the description.
	 */
	public LocalizedString getDescription () {
		return this.aLocalizedStringDescription;
	}

	/**
	 * Returns a localized description of this task
	 * @param sLang requested locale (e.g. en, fr, zn-ch, ...)
	 * @return the localized description corresponding to the locale
	 */
	public String getDescription (final String sLang) {
		final String sDesc = this.aLocalizedStringDescription.getLocalization(sLang);
		if (sDesc == null) {
			return "";
		}
		return sDesc;
	}

	/**
	 * Sets the internationalized description of this task
	 * @param aLocalizedString The description to set.
	 */
	public void setDescription (final LocalizedString aLocalizedString) {
		this.aLocalizedStringDescription = aLocalizedString;
	}

	/**
	 * Adds a localized description to this task
	 * @param sLang the locale of the description
	 * @param sDesc the localized description of this task
	 */
	public void addDescription (final String sLang, final String sDesc) {
		this.aLocalizedStringDescription.addLocalization(sLang, sDesc);
	}

	/**
	 * Gets the id of this task
	 * @return Returns the id.
	 */
	public String getID() {
		return this.sID;
	}

	/**
	 * Sets the id of this task
	 * @param sID The id to set.
	 */
	public void setID (final String sID) {
		this.sID = sID;
	}

	/**
	 * Returns the itnernationalized long name of this task
	 * @return Returns the longname.
	 */
	public LocalizedString getLongName () {
		return this.aLocalizedStringLongName;
	}
	
	/**
	 * Returns a localized long name of this task
	 * @param sLang requested locale (e.g. en, fr, zn-ch, ...)
	 * @return the localized long name corresponding to the locale
	 */
	public String getLongName (final String sLang) {
		final String sName = this.aLocalizedStringLongName.getLocalization(sLang);
		if (sName == null) {
			return "";
		}
		return sName;
	}
	
	/**
	 * Sets the internationalized long name of this task
	 * @param aLocalizedString The longname to set.
	 */
	public void setLongName (final LocalizedString aLocalizedString) {
		this.aLocalizedStringLongName = aLocalizedString;
	}
	
	/**
	 * Adds a localized long name to this task
	 * @param sLang the locale of the long name
	 * @param sLongName the localized long name of this task
	 */
	public void addLongName (final String sLang, final String sLongName) {
		this.aLocalizedStringLongName.addLocalization(sLang, sLongName);
	}
	
	/**
	 * Returns the parameters list of this task
	 * @return Returns the parameters.
	 */
	public Map<String, Parameter> getMapOfParameter () {
		return this.mapOfTaskParameter;
	}
	
	/**
	 * Sets the parameters list of this task
	 * @param mapOfParameter The parameters to set.
	 */
	public void setMapOfParameter (final Map<String, Parameter> mapOfParameter) {
		this.mapOfTaskParameter = mapOfParameter;
	}
	
	/**
	 * Adds a parameter to this task
	 * @param aParameter
	 */
	public void addParameter (final Parameter aParameter) {
		this.mapOfTaskParameter.put(aParameter.getName(), aParameter);
	}
	
	/**
	 * Returns the observations composing this task
	 * @return Returns the observations.
	 */
	public Map<String, Observation> getMapOfObservation() {
		return this.mapOfObservation;
	}

	/**
	 * Sets the observations of this task
	 * @param mapOfObservation The observations to set.
	 */
	public void setMapOfObservation (final Map<String, Observation> mapOfObservation) {
		this.mapOfObservation = mapOfObservation;
	}

	/**
	 * Adds an observation to this task
	 * @param sName name of the observation
	 * @param aObservation the observation to add
	 */
	public void addObservation (final String sName, final Observation aObservation) {
		this.mapOfObservation.put(sName, aObservation);
	}
	
	/**
	 * Returns a list of tasknames referenced bye this task 
	 * @return Returns the references.
	 */
	public List<String> getListOfReference () {
		return this.listOfReference;
	}
	
	/**
	 * Recursively expands this task and referenced ones and merges observations
	 * and parameters.<br/>
	 * If a task A includes a task B that includes a task C, expand will put 
	 * both B and C in A's referenced tasks.
	 * @param mapOfTask
	 */
	public void expand (final Map<String, Task> mapOfTask) {
		this.bExpandingOrExpanded = true;
		final List<String> listOfOldReference = new ArrayList<String>();
		// re-ask why there build another list of reference
		// it's because he add reference in this.references
		for (final String sReference : this.listOfReference) {
			listOfOldReference.add(sReference);
		}

		for (final String sReference : listOfOldReference) {
			final Task aTask = mapOfTask.get(sReference);
			
			if (aTask == null) {
				Task.logger.error("The task " + sReference + " directly referenced " +
						"by the task" + this.getID() + " does not seem to" +
						" exist... Ignoring reference");
				continue;
			}
			
			this.merge(aTask);
			
			if (!aTask.bExpandingOrExpanded) {
				aTask.expand(mapOfTask);
			}
			
			for (final String sNewReference : aTask.listOfReference) {
				if (this.listOfReference.contains(sNewReference) || this.sID.equals(sNewReference)) {
					continue;
				}
				final Task aTaskCurrentRef = mapOfTask.get(sNewReference);
				if (aTaskCurrentRef == null) {
					Task.logger.error("The task " + sReference + " recursively" +
							" referenced by the task" + getID() +
							" does not seem to exist... Ignoring " +
							"reference");
					continue;
				}
				this.listOfReference.add(sNewReference);
				this.merge(aTaskCurrentRef);
			}
		}
	}
	
	/**
	 * Merges another task with this one
	 * @param aNotherTask the task to merge
	 */
	private void merge (final Task aNotherTask) {
		this.mergeObservations(aNotherTask);
		this.mergeParameters(aNotherTask);
	}
	
	/**
	 * Merges observations of another task with this one
	 * @param aNotherTask the task to merge
	 */
	private void mergeObservations (final Task aNotherTask) {
		Task.logger.trace("mergeObservations");
		if (Task.logger.isDebugEnabled()) {
			Task.logger.debug("Other task : " + aNotherTask + ".");
		}
		final Map<String, Observation> mapOfObservation = aNotherTask.getMapOfObservation();
		for (final String sObservationID : mapOfObservation.keySet()) {				   
			final Observation aObservation = mapOfObservation.get(sObservationID);
			if (this.mapOfObservation.containsKey(sObservationID)) {
				this.mapOfObservation.get(sObservationID).merge(aObservation);
			}
			else {
				this.mapOfObservation.put(sObservationID, aObservation);
			}
		}
	}
	
	/**
	 * Merges parameters of another task with this one.
	 * @param aNotherTask the other task to merge
	 */
	private void mergeParameters (final Task aNotherTask) {
		final Map<String, Parameter> mapOfParameter = aNotherTask.getMapOfParameter();
		for (final String sParameterName : mapOfParameter.keySet()) {
			final Parameter aLocalParameter = this.mapOfTaskParameter.get(sParameterName);
			final Parameter aNotherParameter = mapOfParameter.get(sParameterName);
			if(aLocalParameter != null) {
				aLocalParameter.merge(aNotherParameter);
			}
			else {
				this.mapOfTaskParameter.put(sParameterName, aNotherParameter);
			}
		}
	}
	
	/**
	 * Adds a reference to another task
	 * @param sReference the referenced task
	 */
	public void addReference (final String sReference) {
		this.listOfReference.add(sReference);
	}
		
	public String toString () {
		final int iStringBufferSize = 5000;
		final String sVariableSeparator = "\n";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append("ID:").append(sID);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("observations:").append(this.mapOfObservation);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("parameters:").append(this.mapOfTaskParameter);
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append("references:").append(this.listOfReference);

		return aStringBuffer.toString();
	}

	/**
	 * Test if the mimetype is handle by a observer in this task.
	 * @param aMimeType The mimetype to test.
	 * @return Result of the test.
	 */
	public boolean allowMimeType (final MimeType aMimeType) {
		for (final Observation aObservation : this.mapOfObservation.values()) {
			if (aObservation.allowMimeType(aMimeType)) {
				return true;
			}
		}
		return false;
	}

}
