 package org.w3c.unicorn.tasklist;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlException;
import org.w3.unicorn.tasklist.CondType;
import org.w3.unicorn.tasklist.ExecType;
import org.w3.unicorn.tasklist.IfType;
import org.w3.unicorn.tasklist.MappedType;
import org.w3.unicorn.tasklist.ParameterType;
import org.w3.unicorn.tasklist.ParametersType;
import org.w3.unicorn.tasklist.TInputMethod;
import org.w3.unicorn.tasklist.TParamType;
import org.w3.unicorn.tasklist.TUi;
import org.w3.unicorn.tasklist.TaskType;
import org.w3.unicorn.tasklist.TasklistDocument;
import org.w3.unicorn.tasklist.TasklistType;
import org.w3.unicorn.tasklist.ThenType;
import org.w3.unicorn.tasklist.ValueType;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.contract.CallMethod;
import org.w3c.unicorn.contract.CallParameter;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.contract.InputMethod;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.ParameterException;
import org.w3c.unicorn.tasklist.parameters.Mapping;
import org.w3c.unicorn.tasklist.parameters.Parameter;
import org.w3c.unicorn.tasklist.parameters.ParameterFactory;
import org.w3c.unicorn.tasklist.parameters.Value;
import org.w3c.unicorn.tasklisttree.TLTCond;
import org.w3c.unicorn.tasklisttree.TLTExec;
import org.w3c.unicorn.tasklisttree.TLTIf;
import org.w3c.unicorn.tasklisttree.TLTNode;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Unmarshals the tasklist thanks to the XMLBeans tools.
 * 
 * @author Florent Batard, Jonathan Barouh
 * 
 */
public class TaskListUnmarshallerBeans implements TasksListUnmarshaller {

	private TasklistDocument aTaskList;	
	
	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tasklist");
	
	private int NodeID=0;

	
	/**
	 * The tasklist corresponding to the xml file
	 */
	private Map<String,Task> mapOfTask;
	
	/**
	 * The observers' list used to check some constraints on the tasks
	 */
	private Map<String, Observer> mapOfObserver;
	
	
	public TaskListUnmarshallerBeans(){}
	
	public TaskListUnmarshallerBeans(final Map<String, Observer> mapOfObserver) {
		TaskListUnmarshallerBeans.logger.trace("Constructor");	
		this.mapOfTask = new LinkedHashMap<String, org.w3c.unicorn.tasklist.Task>();
		this.mapOfObserver = mapOfObserver;
	}
	

	
	private void addTask(final TaskType aTask) throws ParameterException {
		TaskListUnmarshallerBeans.logger.trace("addTask");
		
		if (aTask == null) {
			TaskListUnmarshallerBeans.logger.warn("Task : null");
			return;
		}
		
		if (TaskListUnmarshallerBeans.logger.isDebugEnabled()) {
			TaskListUnmarshallerBeans.logger.trace("Add task : " + aTask.getId());
		}
		
		final Task aTaskCurrent = new Task();
//		Create the execution level tree
		aTaskCurrent.setID(aTask.getId());
		aTaskCurrent.setTree(this.ExpandTree(aTask));
		
//		 parameters
		final ParametersType aParameters = aTask.getParameters();
		if (aParameters != null) {
			final ParameterType[] listOfParameter;
			listOfParameter = aParameters.getParameterArray();		
			
			for (final ParameterType aParameterBeans : listOfParameter) {
				
				final TUi.Enum aTUi = aParameterBeans.getUi();
				final String sObserver = aParameterBeans.getObserver();
				final String sName = aParameterBeans.getName();
				
				// default values as string
				final String sDefaultValues = aParameterBeans.getDefault();
				
				final TParamType.Enum aParamType = aParameterBeans.getType();				
				
				Parameter aParameter = null; 
				
				if (sObserver != null && !"".equals(sObserver)) {
					aParameter = getParameterFromObserver(
							sName,
							sObserver,
							aTUi,
							sDefaultValues,
							aParamType);					
				}								
				else {
					
					// Values
					final Map<String, Value> mapOfValue = new LinkedHashMap<String, Value>();
					for (final ValueType aValue : 
						aParameterBeans.getValueArray()) {
						
						// name of the value
						String sValueName = aValue.getName();
						if (sValueName == null) {
							sValueName = "";
						}
						
						// Mappings of the value
						final Map<String, List<Mapping>> mapOfMapping = new LinkedHashMap<String, List<Mapping>>();
						for (final MappedType aMappedBeans : aValue.getMappedArray()) {
							final Mapping aMapping = this.createMapping(aMappedBeans);
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
							aParamType,
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

		this.mapOfTask.put(aTaskCurrent.getID(),aTaskCurrent);

	}
	
	
	/**
	 * Creates a usable mapping from a JAXB-generated one.
	 * @param aMappedJAXB the JAXB-generated mapping
	 * @return the created mapping
	 */
	private Mapping createMapping (final MappedType aMapped) {
		TaskListUnmarshallerBeans.logger.trace("createMapping");

		// The mapped observer
		final String sMappingObserver = aMapped.getObserver();
		final Observer aObserverMapped = this.mapOfObserver.get(sMappingObserver);

		if (aObserverMapped == null) {
			TaskListUnmarshallerBeans.logger.error(
					"The observer " + sMappingObserver +
					" does not seem to exist... Skipping mapping.");
			return null;
		}

		// the mapped parameter
		final String sMappingParam = aMapped.getParam();		
		// the value mapped
		String sMappingValue = aMapped.getValue();
		if (sMappingValue == null) {
			sMappingValue = "";
		}
		
		// TODO check if is useful to add input method in mapping
		final List<EnumInputMethod> listOfEnumInputMethod = new ArrayList<EnumInputMethod>();
		
		// The list of mapped input methods
		
		final List<TInputMethod.Enum> listOfTInputMethodBeans = 
			new ArrayList<TInputMethod.Enum>();									
		for (Object methodString : aMapped.getInputmethod()) {
			listOfTInputMethodBeans.add(
					TInputMethod.Enum.forString(methodString.toString()));
		}
		// by default a parameter is mapped to all input methods
		if (listOfTInputMethodBeans.size() == 0) {
			listOfTInputMethodBeans.add(TInputMethod.DIRECT);
			listOfTInputMethodBeans.add(TInputMethod.FILE);
			listOfTInputMethodBeans.add(TInputMethod.URI);
		}
		
		/*
		 * For each input method, we check that the mapped observer:
		 *  - can handle this input method
		 *  - has a parameter with the corresponding name for this input 
		 *    method
		 *  - can handle this value for this parameter 
		 */

		for (final TInputMethod.Enum aTInputMethod : listOfTInputMethodBeans) {
			final EnumInputMethod aEnumInputMethod;
			aEnumInputMethod = TaskListUnmarshallerBeans.getEnumInputMethod(aTInputMethod);
			// the observer can handle this input method
			if (aObserverMapped.getInputMethod(aEnumInputMethod) == null) {
				TaskListUnmarshallerBeans.logger.warn(
						sMappingObserver + " does not support " + 
						aEnumInputMethod.value() + " input method.");
				continue;
			}
			final CallParameter aCallParameterMapped;
			aCallParameterMapped = aObserverMapped.getInputMethod(aEnumInputMethod).getCallParameterByName(sMappingParam);		
			// the parameter exists
			if (aCallParameterMapped == null) {
				TaskListUnmarshallerBeans.logger.error(
						sMappingObserver + " does not have " +
						"a parameter named " + sMappingParam + ".");
				continue;
			}
			// the value exists
			if (!aCallParameterMapped.contains(sMappingValue)) {
				TaskListUnmarshallerBeans.logger.error(
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
	
	
	private Parameter getParameterFromObserver (
			final String sParamName,
			final String sObserverName,
			final TUi.Enum aTUi,
			final String sDefaultValues,
			final TParamType.Enum aTParamType) throws ParameterException {
		TaskListUnmarshallerBeans.logger.trace("getParameterFromObserver");
		if (TaskListUnmarshallerBeans.logger.isDebugEnabled()) {
			TaskListUnmarshallerBeans.logger.debug("Parameter name : "+sParamName+".");
			TaskListUnmarshallerBeans.logger.debug("Observer name : "+sObserverName+".");
			TaskListUnmarshallerBeans.logger.debug("TUi : "+aTUi+".");
			TaskListUnmarshallerBeans.logger.debug("Default values : "+sDefaultValues+".");
			TaskListUnmarshallerBeans.logger.debug("TParamType : "+aTParamType+".");
		}
		
		final Observer aObserver = this.mapOfObserver.get(sObserverName);
		
		// does the requested observer exist?
		if (aObserver == null) {
			TaskListUnmarshallerBeans.logger.warn(
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
				TaskListUnmarshallerBeans.logger.info(
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
	 * Create the tree from a TaskType element
	 * 
	 * @param myTask
	 *            the task to transform into a tree
	 */
	public TLTNode ExpandTree(TaskType myTask) {
		TaskListUnmarshallerBeans.logger.trace("Creation of the tree based on the Task "
				+ myTask.getId());
		TLTNode root = new TLTNode();
		root.setID(NodeID++);
		for (ExecType exec : myTask.getRoutine().getExecArray()) {
			final Observer obs=Framework.mapOfObserver.get(exec.getValue());
			root.addExec(new TLTExec(exec.getId(), obs,exec.getValue(), exec
					.getType(), exec.getParam()));
		}
		for (IfType iflist : myTask.getRoutine().getIfArray()) {
			root.addIf(fillIfs(myTask,iflist));
		}
		return root;
	}

	/**
	 * Recursion over the Then tag to fill the tree
	 * 
	 * @param myThen
	 *            the ThenType node for the recursion
	 * @return the node created
	 */
	private TLTNode FillNode(TaskType myTask,ThenType myThen) {
		TaskListUnmarshallerBeans.logger.trace("Creation of a then branch ");
		TLTNode node = new TLTNode();
		node.setID(NodeID++);
		for (ExecType exec : myThen.getExecArray()) {
			final Observer obs=Framework.mapOfObserver.get(exec.getValue());
			node.addExec(new TLTExec(exec.getId(),obs, exec.getValue(), exec
					.getType(), exec.getParam()));
		}
		for (IfType iflist : myThen.getIfArray()) {
			node.addIf(fillIfs(myTask,iflist));
		}
		return node;
	}

	/**
	 * Created the Ifnode in the tree
	 * 
	 * @param ifs
	 *            the IfType node template to create the Ifnode
	 * @return the node created
	 */
	private TLTIf fillIfs(TaskType myTask,IfType ifs) {
		TaskListUnmarshallerBeans.logger.trace("Creation of an If ");
		// Create the if node
		TLTIf ifnode = new TLTIf();
		// Cares about the conditions
		String[] conds = ifs.getTest().split(",");

		for (String cond : conds) {
			TLTCond myCond = new TLTCond();

			for (CondType condlist : myTask.getConds().getCondArray()) {
				if (condlist.getId().equals(cond)) {
					TaskListUnmarshallerBeans.logger.trace("Creation of a condition " + cond);
					myCond.setId(condlist.getId());
					final Observer obs=Framework.mapOfObserver.get(condlist.getObserver());
					myCond.setObserver(obs);
					myCond
							.setResult(condlist.getResult().equals("passed") ? true
									: false);
					myCond.setType(condlist.getType());
					myCond.setValue(condlist.getValue());

					ifnode.addCond(myCond);
					break;
				}
			}

		}

		// Add recursively the inner ifs in the then part
		if (ifs.getThen() != null) {
			TaskListUnmarshallerBeans.logger.trace("Call recursion for the Then ");
			ifnode.setIfOk(FillNode(myTask,ifs.getThen()));
			// Add recursively the inner if in the else part
			if (ifs.getElse() != null) {
				TaskListUnmarshallerBeans.logger.trace("Call recursion for the else");
				ifnode.setIfNotOk(FillNode(myTask,ifs.getElse()));
			}
		}
		return ifnode;
	}
	
	
	
	
	private org.w3c.unicorn.tasklist.parameters.Parameter createParameter (
			final TParamType.Enum aTParamType,
			final String sName,
			final TUi.Enum aTUi,
			final String sDefaultValues,
			final Map<String, Value> mapOfValue) throws ParameterException {
		TaskListUnmarshallerBeans.logger.trace("createParameter");
		if (TaskListUnmarshallerBeans.logger.isDebugEnabled()) {
			TaskListUnmarshallerBeans.logger.debug("TParamType : "+aTParamType+".");
			TaskListUnmarshallerBeans.logger.debug("Name : "+sName+".");
			TaskListUnmarshallerBeans.logger.debug("TUi : "+aTUi+".");
			TaskListUnmarshallerBeans.logger.debug("Default values : "+sDefaultValues+".");
			TaskListUnmarshallerBeans.logger.debug("Map of value : "+mapOfValue+".");
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
	 * Wraps a TInputMethod instance on an EnumInputMethod
	 * @param aTInputMethod
	 * @return
	 */
	private static EnumInputMethod getEnumInputMethod (final TInputMethod.Enum aTInputMethod) {
		switch (aTInputMethod.intValue()) {
			case TInputMethod.INT_DIRECT:
				return EnumInputMethod.DIRECT;
			case TInputMethod.INT_FILE:
				return EnumInputMethod.UPLOAD;
			case TInputMethod.INT_URI:
				return EnumInputMethod.URI;
			default:
				return EnumInputMethod.URI;
		}		
	}
	
	
	
	public Map<String, org.w3c.unicorn.tasklist.Task> getMapOfTask() {
		TaskListUnmarshallerBeans.logger.trace("getMapOfTask");
		return this.mapOfTask;
	}

	public void addURL(URL aURL) throws IOException{
		TaskListUnmarshallerBeans.logger.trace("addURL");
		if (TaskListUnmarshallerBeans.logger.isDebugEnabled()) {
			TaskListUnmarshallerBeans.logger.debug("URL : "+aURL+".");
		}

			try {
				this.aTaskList = TasklistDocument.Factory.parse(aURL.openStream());			
			} catch (XmlException e) {
				TaskListUnmarshallerBeans.logger.error("Parsing error in TasklistUnmarshaller",e);
				e.printStackTrace();
			}	
	}
	
	

	public void unmarshal() throws Exception {
		TaskListUnmarshallerBeans.logger.trace("unmarshal tasklist");
		// creates the tasklist without computing references
		for (final TaskType aTask : this.aTaskList.getTasklist().getTaskArray()) {
			if (this.mapOfTask.containsKey(aTask.getId())) {
				TaskListUnmarshallerBeans.logger.warn("Task with id "+aTask.getId()+" already defined.");
			} else {
				this.addTask(aTask);
			}
		}
		
		// computes and replaces references by their corresponding observations
		// and parameters
		for (final org.w3c.unicorn.tasklist.Task aTask : this.mapOfTask.values()) {
			TaskListUnmarshallerBeans.logger.debug("Expand task : "+aTask.getID()+".");
			aTask.setTree(aTask.expandNode(mapOfTask, aTask.getTree()));
		}

	}

}
