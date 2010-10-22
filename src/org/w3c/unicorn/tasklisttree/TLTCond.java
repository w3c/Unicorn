// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.Observer;
import org.w3c.unicorn.exceptions.UnicornException;

/**
 * Class made to manage the XML type condType of the tasklist. Included in a
 * TLTIf, it will decide of the next node to be executed.
 * 
 * @author Barouh Jonathan & Batard Florent
 * 
 */
public abstract class TLTCond {

	private String id;

	protected Observer observer;

	private EnumCondType type;

	private boolean result;

	protected String value;
	
	protected String parameter;

	private static final Log logger = LogFactory.getLog(TLTCond.class);

	/**
	 * Advanced constructor for a TLTCond.
	 * 
	 * @param id
	 * @param observer
	 *            The name of the observer corresponding to the condition
	 * @param type
	 *            The type of the condition
	 */
	public TLTCond(String id, Observer observer, EnumCondType type, String value) {
		logger.trace("constructor(" + id + ", " + observer + ", "
				+ type.value() + ")");
		this.id = id;
		this.observer = observer;
		this.type = type;
	}

	/**
	 * Default constructor for a TLTCond.
	 * 
	 */
	public TLTCond() {
		logger.trace("constructor()");
	}
	
	public abstract boolean check(UnicornCall unicornCall) throws UnicornException;
	
	public static TLTCond createCond(EnumCondType type) {
		switch (type) {
		case MIMETYPE:
			return new MimetypeCond();
		case PARAMETER:
			return new ParameterCond();
		case XPATH:
			return new XPathCond();
    case METHOD:
        return new MethodCond();
		default:
			return null;
		}
	}
	
	public static TLTCond createCond(String type) {
		return createCond(EnumCondType.fromValue(type));
	}

	/**
	 * 
	 * @param id
	 *            The id of the condition
	 */
	public void setId(String id) {
		logger.trace("setId(" + id + ")");
		this.id = id;
	}

	/**
	 * 
	 * @param observer
	 *            The observer the condition depends on
	 */
	public void setObserver(Observer observer) {
		logger.trace("setObserver(" + observer + ")");
		this.observer = observer;
	}

	/**
	 * 
	 * @param result
	 *            The result of the test
	 */
	public void setResult(boolean result) {
		logger.trace("setResult(" + result + ")");
		this.result = result;
	}

	/**
	 * 
	 * @param type
	 *            The type of the condition
	 */
	public void setType(EnumCondType type) {
		logger.trace("setType(" + type.value() + ")");
		this.type = type;
	}

	/**
	 * 
	 * @param type
	 *            The type of the condition
	 */
	public void setType(String type) {
		logger.trace("setType(" + type + ")");
		for (EnumCondType val : EnumCondType.values()) {
			if (val.value().equals(type)) {
				this.type = val;
			}
		}
	}

	/**
	 * 
	 * @param value
	 *            value of the condition
	 */
	public void setValue(String value) {
		logger.trace("setValue(" + value + ")");
		this.value = value;
	}

	/**
	 * @return The id of the condition
	 */
	public String getId() {
		logger.trace("getId()");
		return id;
	}

	/**
	 * 
	 * @return The observer the condition depends on
	 */
	public Observer getObserver() {
		logger.trace("getObserver()");
		return observer;
	}

	/**
	 * 
	 * @return The result of the test
	 */
	public boolean getResult() {
		logger.trace("getResult()");
		return result;
	}

	/**
	 * 
	 * @return The type of the condition
	 */
	public EnumCondType getType() {
		logger.trace("getType()");
		return type;
	}

	/**
	 * 
	 * @return The value of the condition
	 */
	public String getValue() {
		logger.trace("getValue()");
		return value;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		if (this.observer != null) {
			return new String("TLTCond{id: " + this.id + ", observer: "
					+ this.observer.getID() + ", value: " + this.value + "}");
		}
		return new String("TLTCond{id: " + this.id + ", value: " + this.value
				+ "}");
	}

}
