// $Id: Property.java,v 1.1.1.1 2006-08-31 09:09:28 dleroy Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Damien LEROY
 */
public class Property {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.util");
	private static final Map<String, Property> mapOfProperty = new Hashtable<String, Property>();

	private static void addProperty (final Property aProperty) {
		Property.logger.trace("addProperty");
		if (Property.logger.isDebugEnabled()) {
			Property.logger.debug("Name : "+aProperty.getID()+".");
			Property.logger.debug("Value : "+aProperty.getValue()+".");
		}
		Property.mapOfProperty.put(aProperty.getID(), aProperty);
	}

	public static Property getProperty (final String sID) {
		return Property.mapOfProperty.get(sID);
	}

	public static String get (final String sID) {
		return Property.mapOfProperty.get(sID).getValue();
	}

	public static void load (final Properties aProperties) {
		Property.logger.trace("load");
		for (
				final Enumeration aEnumeration = aProperties.propertyNames();
				aEnumeration.hasMoreElements();) {
			final String sPropName = (String) aEnumeration.nextElement();
			final String sPropValue = aProperties.getProperty(sPropName);
			Property.parseValue(sPropName, sPropValue);
		}
	}

	private static void parseValue (final String sPropName, final String sPropValue) {
		Property.logger.trace("parseValue");
		if (Property.logger.isDebugEnabled()) {
			Property.logger.debug("Name : "+sPropName+".");
			Property.logger.debug("Value : "+sPropValue+".");
		}
		Property aProperty = Property.getProperty(sPropName);
		if (aProperty == null) {
			Property.logger.debug("Property "+sPropName+" not already exist.");
			aProperty = new Property();
			aProperty.setID(sPropName);
		} else {
			Property.logger.debug("Property "+sPropName+" already exist.");
			aProperty.clear();
		}
		final String[] tStringElement = sPropValue.split(",");
		for (String sElement : tStringElement) {
			sElement = sElement.trim();
			if (sElement.equals("")) {
				Property.logger.warn("Empty element found in property "+sPropName+".");
				continue;
			}
			if (sElement.startsWith("$")) {
				// this is a reference to another property
				final String sPropRef = sElement.substring(1);
				Property aNotherProperty;
				aNotherProperty = Property.getProperty(sPropRef);
				if (null == aNotherProperty) {
					Property.logger.warn("Property "+sPropRef+" not found.");
					aNotherProperty = new Property();
					aNotherProperty.setID(sPropRef);
					Property.addProperty(aNotherProperty);
				}
				aProperty.addElement(aNotherProperty);
			}
			else {
				aProperty.setSpecific(sElement);
				// the specific part is the last part of the property
				break;
			}
		}
		Property.addProperty(aProperty);
	}

	private List<Property> listOfElement = new ArrayList<Property>();
	private String sSpecificElement = "";
	private String sID = null;

	private String getID () {
		return this.sID;
	}

	private void setID (final String sID) {
		this.sID = sID;
	}

	private void addElement (final Property aProperty) {
		this.listOfElement.add(aProperty);
	}
	
	public void clear () {
		this.listOfElement.clear();
		this.sSpecificElement = "";
	}
	
	public void setSpecific (final String sSpecific) {
		this.sSpecificElement = sSpecific;
	}

	public String getValue () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		for (final Property aProperty : this.listOfElement) {
			aStringBuffer.append(aProperty.toString());
		}
		aStringBuffer.append(this.sSpecificElement);

		return aStringBuffer.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		final int iStringBufferSize = 500;
		final StringBuffer aStringBuffer = new StringBuffer(iStringBufferSize);

		for (final Property aProperty : this.listOfElement) {
			aStringBuffer.append(aProperty.toString());
		}
		aStringBuffer.append(this.sSpecificElement);

		return aStringBuffer.toString();
	}

	static {
		try {
			final URL aURLPropFile = Property.class.getResource("unicorn.properties");
			final Properties aProperties = new Properties();
			aProperties.load(aURLPropFile.openStream());
			Property.load(aProperties);
		} catch (final IOException e) {
			Property.logger.error("IOException : "+e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public static void main (final String[] tArgument) {
		System.out.println("Begin.");
		
		for (final Property aProperty : Property.mapOfProperty.values()) {
			System.out.println(aProperty.getID() + " = " + aProperty.getValue());
		}

		System.out.println("End.");
	}

}
