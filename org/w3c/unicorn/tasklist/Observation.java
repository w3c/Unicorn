// $Id: Observation.java,v 1.2 2008-06-17 13:45:32 jbarouh Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.activation.MimeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.generated.tasklist.TPriority;

/**
 * Observation<br />
 * Created: May 29, 2006 6:00:05 PM<br />
 * An observation is composed by an observer and a map of mimetypes and priorities.
 * @author Jean-Guilhem ROUEL
 */
public class Observation {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklist");

	private Observer aObserver;
	private Map<MimeType, TPriority> mapOfMimeTypePriority;

	/**
	 * Creates a new Observation.
	 * @param mimeTypeList
	 * @param aObserver
	 */
	public Observation (
			final Observer aObserver,
			final Map<MimeType, TPriority> mapOfMimeTypePriority) {
		Observation.logger.trace("Constructor.");
		if (Observation.logger.isDebugEnabled()) {
			Observation.logger.debug("Observer id : " + aObserver.getID() + ".");
			Observation.logger.debug("Map of mime type priority : " + mapOfMimeTypePriority + ".");
		}
		this.setObserver(aObserver);
		this.mapOfMimeTypePriority = new LinkedHashMap<MimeType, TPriority>();
		this.addMapOfMimeTypePriority(mapOfMimeTypePriority);
	}

	/**
	 * @return Returns the mimeTypeList.
	 */
	public Map<MimeType, TPriority> getMapOfMimeTypePriority () {
		return this.mapOfMimeTypePriority;
	}

	/**
	 * Precondition : the observer is set to an existing one.
	 * @param mimeTypeList The mimeTypeList to set.
	 */
	private void addMapOfMimeTypePriority (
			final Map<MimeType, TPriority> mapOfMimeTypePriority) {
		Observation.logger.trace("addMapOfMimeTypePriority");
		if (Observation.logger.isDebugEnabled()) {
			Observation.logger.debug("Map of mime type -> priority : " + mapOfMimeTypePriority + ".");
		}
		// if the observer has not been set ... no, it MUST be set !!!
		for (final MimeType aMimeType : mapOfMimeTypePriority.keySet()) {
			if (!this.aObserver.canHandleMimeType(aMimeType)) {
				Observation.logger.warn(
						this.aObserver.getID() + " does not support " + 
						aMimeType.getBaseType() + "... Skipping.");
				continue;
			}
			this.addMimeType(
					aMimeType,
					mapOfMimeTypePriority.get(aMimeType));
		}
	}

	/**
	 * Adds a mimetype to the list of mimetypes.<br/>
	 * The mimetype is added if is not already present in the list or if it is
	 * already present with a lower priority.
	 * @param aMimeType the mimetype to add
	 * @param aTPriority the priority associated to the mimetype
	 * @return <code>true</code> if the mimetype has effectively been added to the list
	 */
	public void addMimeType (
			final MimeType aMimeType,
			final TPriority aTPriority) {
		Observation.logger.trace("addMimeType");
		if (Observation.logger.isDebugEnabled()) {
			Observation.logger.debug("MimeType : " + aMimeType + ".");
			Observation.logger.debug("TPriority : " + aTPriority + ".");
		}
		// check if the mime type already exist in the map
		final TPriority aLocalTPriority = this.mapOfMimeTypePriority.get(aMimeType);
		// if not exist add it and return
		if (null == aLocalTPriority) {
			this.mapOfMimeTypePriority.put(aMimeType, aTPriority);
			return;
		}
		// we change the priority only if the new is higher than the old
		switch (aLocalTPriority) {
			case LOW :
				if (aTPriority != TPriority.LOW) {
					this.mapOfMimeTypePriority.put(aMimeType, aTPriority);
				}
				break;
			case MEDIUM :
				if (aTPriority == TPriority.HIGH) {
					this.mapOfMimeTypePriority.put(aMimeType, aTPriority);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Parses the mapOfMimeTypePriority in order to check if the given type
	 * matches an existing one.
	 * @param aMimeType The type to check.
	 * @return True if the given type matches an existing one, else false.
	 */
	public boolean allowMimeType (
			final MimeType aMimeType) {
		for (final MimeType aLocalMimeType : this.mapOfMimeTypePriority.keySet()) {
			if (aLocalMimeType.match(aMimeType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Merges an observation with this one
	 * @param otherObservation
	 * @return
	 */
	public void merge (final Observation aOtherObservation) {
		for (final MimeType aMimeType : this.mapOfMimeTypePriority.keySet()) {
			this.addMimeType(
					aMimeType,
					this.mapOfMimeTypePriority.get(aMimeType));
		}	
	}

	/**
	 * Get the priority associted to a mimetype
	 * @param aMimeType the mimetype to get the priority
	 * @return the priority associated to the mimetype
	 */
	public TPriority getPriority (
			final MimeType aMimeType) {
		Observation.logger.trace("getPriority");
		if (Observation.logger.isDebugEnabled()) {
			Observation.logger.debug("MimeType : " + aMimeType + ".");
		}
		for (final MimeType aLocalMimeType : this.mapOfMimeTypePriority.keySet()) {
			if (aLocalMimeType.match(aMimeType)) {
				return this.mapOfMimeTypePriority.get(aLocalMimeType);
			}
		}
		Observation.logger.info("Priority not found for this mime type.");
		return null;
	}

	/**
	 * Returns the observer of this observation
	 * @return Returns the observer.
	 */
	public Observer getObserver() {
		return this.aObserver;
	}

	/**
	 * Sets the observer of this observation
	 * @param aObserver The observer to set.
	 */
	private void setObserver (final Observer aObserver) {
		Observation.logger.trace("setObserver");
		if (Observation.logger.isDebugEnabled()) {
			Observation.logger.debug("Observer id : " + aObserver.getID() + ".");
		}
		this.aObserver = aObserver;
	}

	public String toString() {
		final int iStringBufferSize = 1000;
		final String sVariableSeparator = " | ";
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		aStringBuffer.append("(");
		aStringBuffer.append(this.aObserver.getID());
		aStringBuffer.append(sVariableSeparator);
		aStringBuffer.append(this.mapOfMimeTypePriority);
		aStringBuffer.append(")");

		return aStringBuffer.toString();
	}

}
