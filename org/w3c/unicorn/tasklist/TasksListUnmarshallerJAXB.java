// $Id: TasksListUnmarshallerJAXB.java,v 1.1.1.1 2006-08-31 09:09:27 dleroy Exp $
// Author: Jean-Guilhem Rouel
// (c) COPYRIGHT MIT, ERCIM and Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklist;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.contract.CallMethod;
import org.w3c.unicorn.contract.CallParameter;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.contract.InputMethod;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.ParameterException;
import org.w3c.unicorn.generated.tasklist.Handle;
import org.w3c.unicorn.generated.tasklist.Mapped;
import org.w3c.unicorn.generated.tasklist.Observation;
import org.w3c.unicorn.generated.tasklist.Parameter;
import org.w3c.unicorn.generated.tasklist.Parameters;
import org.w3c.unicorn.generated.tasklist.Subtask;
import org.w3c.unicorn.generated.tasklist.TInputMethod;
import org.w3c.unicorn.generated.tasklist.TParamType;
import org.w3c.unicorn.generated.tasklist.TPriority;
import org.w3c.unicorn.generated.tasklist.TUi;
import org.w3c.unicorn.generated.tasklist.Task;
import org.w3c.unicorn.generated.tasklist.Tasklist;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.ParameterFactory;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.util.LocalizedString;

/**
 * TasksListUnmarshallerJAXB<br />
 * Created: May 30, 2006 5:58:26 PM<br />
 * This class is used to unmarshall tasks list files using JAXB
 */
public class TasksListUnmarshallerJAXB implements TasksListUnmarshaller {
	
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklist");

	protected JAXBContext aJAXBContext;
	protected Unmarshaller aUnmarshaller;

	/**
	 * The tasklist corresponding to the xml file
	 */
	private Map<String, org.w3c.unicorn.tasklist.Task> mapOfTask;
	
	/**
	 * The observers' list used to check some constraints on the tasks
	 */
	private Map<String, Observer> mapOfObserver;
	
	/**
	 * The tasklist created by JAXB
	 */
	private Tasklist aTaskListJAXB;
	
	/**
	 * Creates a new TasksListUnmarshallerJAXB.
	 * Private to prevent default construction
	 */
	private TasksListUnmarshallerJAXB() {}
	
	/**
	 * Creates a new TasksListUnmarshallerJAXB.
	 * @param mapOfObserver A list containing the observers referenced by the tasklist
	 * @throws JAXBException If an exception occured during the parsing
	 */
	public TasksListUnmarshallerJAXB (
			final Map<String, Observer> mapOfObserver) throws JAXBException {
		TasksListUnmarshallerJAXB.logger.trace("Constructor");
		this.aJAXBContext = JAXBContext.newInstance("org.w3c.unicorn.generated.tasklist");
		this.aUnmarshaller = this.aJAXBContext.createUnmarshaller();
		this.mapOfTask = new LinkedHashMap<String, org.w3c.unicorn.tasklist.Task>();
		this.mapOfObserver = mapOfObserver;
	}
	
	/**
	 * Builds a usable task based on a JAXB one
	 * @param aTaskJAXB the JAXB-generated task
	 * @throws ParameterException 
	 */
	private void addTask (final Task aTaskJAXB) throws ParameterException {
		TasksListUnmarshallerJAXB.logger.trace("addTask");

		if (aTaskJAXB == null) {
			TasksListUnmarshallerJAXB.logger.warn("Task : null.");
			return;
		}
		
		if (TasksListUnmarshallerJAXB.logger.isDebugEnabled()) {
			TasksListUnmarshallerJAXB.logger.debug("Add task : "+aTaskJAXB.getId()+".");
		}
		
		final org.w3c.unicorn.tasklist.Task aTaskCurrent = new org.w3c.unicorn.tasklist.Task();
		
		// id
		aTaskCurrent.setID(aTaskJAXB.getId());
		
		// subtasks
		for (final Object oObserverOrTask : aTaskJAXB.getSubtasks().getObservationOrSubtask()) {
			if (oObserverOrTask instanceof Observation) {
				// observer
				try {
					this.addObservation(
							aTaskCurrent,
							(Observation) oObserverOrTask);
				}
				catch (final MimeTypeParseException e) {
					TasksListUnmarshallerJAXB.logger.warn("MimeTypeParseException : "+e.getMessage(), e);
				}
				continue;
			}
			if (oObserverOrTask instanceof Subtask) {
				final Subtask aSubtask = (Subtask) oObserverOrTask;
				final String sReference = aSubtask.getRef();		
				
				if (sReference.equals(aTaskCurrent.getID())) {
					TasksListUnmarshallerJAXB.logger.warn(
							"Simple reference loop detected in " +
							"task " + sReference + "... Ignoring");
					continue;
				}
				aTaskCurrent.addReference(sReference);
			}
		}
		
		// parameters
		final Parameters aParametersJAXB = aTaskJAXB.getParameters();
		if (aParametersJAXB != null) {
			final List<Parameter> listOfParameterJAXB;
			listOfParameterJAXB = aParametersJAXB.getParameter();		
			
			for (final Parameter aParameterJAXB : listOfParameterJAXB) {
				
				final TUi aTUi = aParameterJAXB.getUi();
				final String sObserver = aParameterJAXB.getObserver();
				final String sName = aParameterJAXB.getName();
				
				// default values as string
				final String sDefaultValues = aParameterJAXB.getDefault();
				
				final TParamType aTParamType = aParameterJAXB.getType();				
				
				org.w3c.unicorn.tasklist.parameters.Parameter aParameter = null;
				
				if (sObserver != null && !"".equals(sObserver)) {
					aParameter = getParameterFromObserver(
							sName,
							sObserver,
							aTUi,
							sDefaultValues,
							aTParamType);					
				}								
				else {
					// Values
					final Map<String, Value> mapOfValue = new LinkedHashMap<String, Value>();
					for (final org.w3c.unicorn.generated.tasklist.Value aValueJAXB : 
						aParameterJAXB.getValue()) {
						
						// name of the value
						String sValueName = aValueJAXB.getName();
						if (sValueName == null) {
							sValueName = "";
						}
						
						// Mappings of the value
						final Map<String, List<Mapping>> mapOfMapping = new LinkedHashMap<String, List<Mapping>>();
						for (final Mapped aMappedJAXB : aValueJAXB.getMapped()) {
							final Mapping aMapping = this.createMapping(aMappedJAXB);
							if (aMapping != null) {
								final String sObs = aMapping.getObserver().getID();
								List<Mapping> listOfMapping = mapOfMapping.get(sObs);
								if (null == listOfMapping) {
									listOfMapping = new ArrayList<Mapping>();
									mapOfMapping.put(sObs, listOfMapping);
								}
								listOfMapping.add(aMapping);
							}
						}
						mapOfValue.put(
								sValueName,
								new Value(
										mapOfMapping,
										sValueName));
					}
					
					aParameter = this.createParameter(
							aTParamType,
							sName,
							aTUi,
							sDefaultValues, 
							mapOfValue);
				}
				
				if (aParameter != null) {					
					aTaskCurrent.addParameter(aParameter);
				}
			}
		}
		this.mapOfTask.put(new String(aTaskCurrent.getID()), aTaskCurrent);
	}
	
	/**
	 * Creates a parameter based on a parameter of an observer
	 * @param sName the name of the parameter
	 * @param observer the observer which has this parameter
	 * @param aTUi ui level
	 * @throws ParameterException 
	 */
	private org.w3c.unicorn.tasklist.parameters.Parameter getParameterFromObserver (
			final String sParamName,
			final String sObserverName,
			final TUi aTUi,
			final String sDefaultValues,
			final TParamType aTParamType) throws ParameterException {
		TasksListUnmarshallerJAXB.logger.trace("getParameterFromObserver");
		if (TasksListUnmarshallerJAXB.logger.isDebugEnabled()) {
			TasksListUnmarshallerJAXB.logger.debug("Parameter name : "+sParamName+".");
			TasksListUnmarshallerJAXB.logger.debug("Observer name : "+sObserverName+".");
			TasksListUnmarshallerJAXB.logger.debug("TUi : "+aTUi+".");
			TasksListUnmarshallerJAXB.logger.debug("Default values : "+sDefaultValues+".");
			TasksListUnmarshallerJAXB.logger.debug("TParamType : "+aTParamType+".");
		}
		
		final Observer aObserver = this.mapOfObserver.get(sObserverName);
		
		// does the requested observer exist?
		if (aObserver == null) {
			TasksListUnmarshallerJAXB.logger.warn(
					"The parameter " + sParamName + "refers to a " +
					"non-existing observer: " + sObserverName + ".");
			return null;
		}
		
		// the name of the parameter
		// TODO Change when the RDF is done
		final LocalizedString aLocalizedString = new LocalizedString();
		aLocalizedString.addLocalization("en", sParamName);		
		
		final Map<String, Value> mapOfValue = new LinkedHashMap<String, Value>();
		
		// iterate over the observer's methods
		final Map<EnumInputMethod, InputMethod> mapOfInputMethod = aObserver.getMapOfInputMethod();
		for (final EnumInputMethod aEnumInputMethod : mapOfInputMethod.keySet()) {
			final InputMethod aInputMethod = mapOfInputMethod.get(aEnumInputMethod);			
			
			if (aInputMethod.getCallParameter().getName().equals(sParamName)) {
				// the referenced parameter is an input one for the current 
				// method, so it must be ignored
				TasksListUnmarshallerJAXB.logger.info(
						"The referenced parameter is an input one for the current method, so it must be ignored.");
				continue;
			}			
			
			final CallMethod aCallMethod = aInputMethod.getCallMethod();
			final CallParameter aCallParameter = aCallMethod.getCallParameterByName(sParamName);			
			// A parameter with this name exists for this method			
			if (aCallParameter != null) {
				for (final String sValue : aCallParameter.getListOfPossibleValue()) {
					final Value aValueCurrent = mapOfValue.get(sValue);
					if (aValueCurrent != null) {
						// the newly created parameter already contains a
						// similar value
						
						// we know that the created parameter contains exactly 
						// ONE mapping for this value
						//aValueCurrent.getMappings().get(sObserverName).get(0).addInputMethod(aEnumInputMethod);
					}
					else {
						// the newly created parameter does not contain a
						// similar value
						final LocalizedString aInternationalizedMessageValueName;
						aInternationalizedMessageValueName = new LocalizedString();
						// TODO Add localized names in RDF contract
						aInternationalizedMessageValueName.addLocalization("en", sValue);
						
						final List<EnumInputMethod> mapOfNewInputMethod = new ArrayList<EnumInputMethod>();
						mapOfNewInputMethod.add(aEnumInputMethod);
						final Mapping aMapping = new Mapping(
								aObserver,
								sParamName, 
								sValue);

						final List<Mapping> listOfMapping = new ArrayList<Mapping>();
						listOfMapping.add(aMapping);

						final Map<String, List<Mapping>> mapOfListOfMapping;
						mapOfListOfMapping = new LinkedHashMap<String, List<Mapping>>();
						mapOfListOfMapping.put(sObserverName, listOfMapping);

						final Value aValueToAdd = new Value(
								aInternationalizedMessageValueName,
								mapOfListOfMapping,
								sValue);
						mapOfValue.put(sValue, aValueToAdd);
					}
				}				
			}
		}
		return this.createParameter(
				aTParamType,
				sParamName,
				aTUi,
				sDefaultValues, 
				mapOfValue);	
	}
	
	/**
	 * Creates a parameter of the correct type (checkbox, dropdown, ...) given
	 * its different information
	 * @param aTParamType Type of the parameter
	 * @param sName Name of the parameter
	 * @param aTUi UI level of the parameter
	 * @param sDefaultValues Default values of the parameter (must match valid values)
	 * @param mapOfValue List of values the paramter can have
	 * @return The parameter created
	 * @throws ParameterException 
	 */
	private org.w3c.unicorn.tasklist.parameters.Parameter createParameter (
			final TParamType aTParamType,
			final String sName,
			final TUi aTUi,
			final String sDefaultValues,
			final Map<String, Value> mapOfValue) throws ParameterException {
		TasksListUnmarshallerJAXB.logger.trace("createParameter");
		if (TasksListUnmarshallerJAXB.logger.isDebugEnabled()) {
			TasksListUnmarshallerJAXB.logger.debug("TParamType : "+aTParamType+".");
			TasksListUnmarshallerJAXB.logger.debug("Name : "+sName+".");
			TasksListUnmarshallerJAXB.logger.debug("TUi : "+aTUi+".");
			TasksListUnmarshallerJAXB.logger.debug("Default values : "+sDefaultValues+".");
			TasksListUnmarshallerJAXB.logger.debug("Map of value : "+mapOfValue+".");
		}

		final org.w3c.unicorn.tasklist.parameters.Parameter aParameter = ParameterFactory.getParameter(aTParamType);
		if (null == aParameter) {
			return null;
		}
		aParameter.setName(sName);
		aParameter.setUiLevel(aTUi);
		aParameter.setMapOfValue(mapOfValue);
		aParameter.setDefaultValues(sDefaultValues);
		return aParameter;
	}

	/**
	 * Creates a usable mapping from a JAXB-generated one.
	 * @param aMappedJAXB the JAXB-generated mapping
	 * @return the created mapping
	 */
	private Mapping createMapping (final Mapped aMappedJAXB) {
		TasksListUnmarshallerJAXB.logger.trace("createMapping");

		// The mapped observer
		final String sMappingObserver = aMappedJAXB.getObserver();
		final Observer aObserverMapped = this.mapOfObserver.get(sMappingObserver);

		if (aObserverMapped == null) {
			TasksListUnmarshallerJAXB.logger.error(
					"The observer " + sMappingObserver +
					" does not seem to exist... Skipping mapping.");
			return null;
		}

		// the mapped parameter
		final String sMappingParam = aMappedJAXB.getParam();		
		// the value mapped
		String sMappingValue = aMappedJAXB.getValue();
		if (sMappingValue == null) {
			sMappingValue = "";
		}
		
		// TODO check if is useful to add input method in mapping
		final List<EnumInputMethod> listOfEnumInputMethod = new ArrayList<EnumInputMethod>();
		
		// The list of mapped input methods
		final List<TInputMethod> listOfTInputMethodJAXB = aMappedJAXB.getInputmethod();									
		
		// by default a parameter is mapped to all input methods
		if (listOfTInputMethodJAXB.size() == 0) {
			listOfTInputMethodJAXB.add(TInputMethod.DIRECT);
			listOfTInputMethodJAXB.add(TInputMethod.FILE);
			listOfTInputMethodJAXB.add(TInputMethod.URI);
		}
		
		/*
		 * For each JAXB input method, we check that the mapped observer:
		 *  - can handle this input method
		 *  - has a parameter with the corresponding name for this input 
		 *    method
		 *  - can handle this value for this parameter 
		 */
		
		for (final TInputMethod aTInputMethod : listOfTInputMethodJAXB) {
			final EnumInputMethod aEnumInputMethod;
			aEnumInputMethod = TasksListUnmarshallerJAXB.getEnumInputMethod(aTInputMethod);
			// the observer can handle this input method
			if (aObserverMapped.getInputMethod(aEnumInputMethod) == null) {
				TasksListUnmarshallerJAXB.logger.warn(
						sMappingObserver + " does not support " + 
						aEnumInputMethod.value() + " input method.");
				continue;
			}
			final CallParameter aCallParameterMapped;
			aCallParameterMapped = aObserverMapped.getInputMethod(aEnumInputMethod).getCallParameterByName(sMappingParam);		
			// the parameter exists
			if (aCallParameterMapped == null) {
				TasksListUnmarshallerJAXB.logger.error(
						sMappingObserver + " does not have " +
						"a parameter named " + sMappingParam + ".");
				continue;
			}
			// the value exists
			if (!aCallParameterMapped.contains(sMappingValue)) {
				TasksListUnmarshallerJAXB.logger.error(
						"Parameter " + sMappingParam + 
						" does not accept " + sMappingValue +
						" as a value.");
				continue;
			}
			listOfEnumInputMethod.add(aEnumInputMethod);
		}
		
		if (listOfEnumInputMethod.size() == 0) {
			return null;
		}
		
		return new Mapping(
				aObserverMapped,
				sMappingParam,
				sMappingValue/*, 
				listOfEnumInputMethod*/);
	}

	public void addURL (final URL aURL) throws JAXBException, IOException {
		TasksListUnmarshallerJAXB.logger.trace("addURL");
		if (TasksListUnmarshallerJAXB.logger.isDebugEnabled()) {
			TasksListUnmarshallerJAXB.logger.debug("URL : "+aURL+".");
		}

		this.aTaskListJAXB = (Tasklist) this.aUnmarshaller.unmarshal(aURL.openStream());		
	}

	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.TasksListUnmarshaller#unmarshal(java.net.URL)
	 */
	public void unmarshal () throws Exception {
		TasksListUnmarshallerJAXB.logger.trace("unmarshal");

		// creates the tasklist without computing references
		for (final Task aTaskJAXB : this.aTaskListJAXB.getTask()) {
			if (this.mapOfTask.containsKey(aTaskJAXB.getId())) {
				TasksListUnmarshallerJAXB.logger.warn("Task with id "+aTaskJAXB.getId()+" already defined.");
			} else {
				this.addTask(aTaskJAXB);
			}
		}
		
		// computes and replaces references by their corresponding observations
		// and parameters
		for (final org.w3c.unicorn.tasklist.Task aTask : this.mapOfTask.values()) {
			TasksListUnmarshallerJAXB.logger.debug("Expand task : "+aTask.getID()+".");
			aTask.expand(this.mapOfTask);
		}
	}
	
	/**
	 * Adds an observation to a task.
	 * @param aTaskCurrent task in which to add the observation
	 * @param aObservation the observation to add
	 * @throws MimeTypeParseException
	 */
	private void addObservation(
			final org.w3c.unicorn.tasklist.Task aTaskCurrent, 
			final Observation aObservation) throws MimeTypeParseException {
		TasksListUnmarshallerJAXB.logger.trace("addObservation");
		if (TasksListUnmarshallerJAXB.logger.isDebugEnabled()) {
			TasksListUnmarshallerJAXB.logger.debug("Task : "+aTaskCurrent.getID()+".");
			TasksListUnmarshallerJAXB.logger.debug("Observation : "+aObservation.getRef()+".");
		}

		final Observer aObserver = mapOfObserver.get(aObservation.getRef());
		if (aObserver == null) {
			TasksListUnmarshallerJAXB.logger.error(
					"The observer " + aObservation.getRef() +
					" does not seem to exist... Skipping observation.");
			return;
		}
		// mimetypes
		final Map<MimeType, TPriority> mapOfMimeTypePriority = new LinkedHashMap<MimeType, TPriority>();
		for (final Handle aHandle : aObservation.getHandle()) {
			mapOfMimeTypePriority.put(
					new MimeType(aHandle.getMimetype()),
					aHandle.getPriority());
		}
		aTaskCurrent.addObservation(
				new String(aObserver.getID()),
				new org.w3c.unicorn.tasklist.Observation(aObserver, mapOfMimeTypePriority));
	}
	
	/* (non-Javadoc)
	 * @see org.w3c.unicorn.tasklist.TasksListUnmarshaller#getTasksList()
	 */
	public Map<String, org.w3c.unicorn.tasklist.Task> getMapOfTask() {
		return this.mapOfTask;
	}
	
	/**
	 * Wraps a TInputMethod instance on an EnumInputMethod
	 * @param aTInputMethod
	 * @return
	 */
	private static EnumInputMethod getEnumInputMethod (final TInputMethod aTInputMethod) {
		switch (aTInputMethod) {
			case DIRECT:
				return EnumInputMethod.DIRECT;
			case FILE:
				return EnumInputMethod.UPLOAD;
			case URI:
				return EnumInputMethod.URI;
			default:
				return EnumInputMethod.URI;
		}		
	}
	
	/**
	 * Main method used to test this class
	 * @param args
	 * @throws Exception
	 *//*
	public static void main(String[] args) throws Exception {
		Map<String, Observer> observers = new LinkedHashMap<String, Observer>();
		
		Observer o = new Observer();
		observers.put("css-validator", o);
		
		Observer o2 = new Observer();
		observers.put("link-checker", o2);

		TasksListUnmarshaller tlu = new TasksListUnmarshallerJAXB(observers);
		tlu.addURL(new URL("http://w3cstag8/~jean/xml/task2.xml"));
		tlu.unmarshal();
		
		RDFUnmarshaller rdfu = new RDFUnmarshallerJena(tlu.getMapOfTask());
		rdfu.addURL(new URL("http://w3cstag8/~jean/xml/task.rdf"));
		rdfu.unmarshal();
		
		for (org.w3c.unicorn.tasklist.Task t : tlu.getMapOfTask().values()) {
			System.out.println(t);
			System.out.println("---------------------------------------");
		}
	}*/
	
}
