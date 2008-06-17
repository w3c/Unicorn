package org.w3c.unicorn.contract;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.util.LocalizedString;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * This class allow to read and parse a RDF file about a observer contract.
 * 
 * @author Damien LEROY
 */
public class RDFContractUnmarshallerJena implements RDFContractUnmarshaller {

	/**
	 * Use to log any information during use of this class.
	 */
	private static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.contract");

	/**
	 * Model Object
	 */
	private static final Model MODEL = ModelFactory.createDefaultModel();

	/**
	 * The namespace used for unicorn
	 */
	private static final String UCN_NAMESPACE = "http://www.w3.org/unicorn#";

	private static Resource RESOURCE_OBSERVER = null;

	private static Resource RESOURCE_PARAMETER_URI = null;

	private static Resource RESOURCE_PARAMETER_UPLOAD = null;

	private static Resource RESOURCE_PARAMETER_DIRECT = null;

	private static Property PROPERTY_NAME = null;

	private static Property PROPERTY_DESCRIPTION = null;

	private static Property PROPERTY_HELP = null;

	private static Property PROPERTY_PROVIDER = null;

	private static Property PROPERTY_MIMETYPE = null;

	private static Property PROPERTY_INPUTMETHOD = null;

	private static Property PROPERTY_METHODNAME = null;

	private static Property PROPERTY_PARAMETERNAME = null;

	private static Property PROPERTY_REFERENCE = null;

	private static Property PROPERTY_TYPE = null;

	private static Property PROPERTY_LANG = null;

	/**
	 * Description of a observer to complete with information from a RDF file.
	 */
	static {
		try {
			RDFContractUnmarshallerJena.MODEL.read(new URL(
					org.w3c.unicorn.util.Property.get("OBSERVER_RDF_MODEL"))
					.openStream(), null);

			// define resource use to find information into the RDF graph
			RDFContractUnmarshallerJena.RESOURCE_OBSERVER = RDFContractUnmarshallerJena.MODEL
					.getResource(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "Observer");
			RDFContractUnmarshallerJena.RESOURCE_PARAMETER_URI = RDFContractUnmarshallerJena.MODEL
					.getResource(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "ParameterURI");
			RDFContractUnmarshallerJena.RESOURCE_PARAMETER_UPLOAD = RDFContractUnmarshallerJena.MODEL
					.getResource(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "ParameterUpload");
			RDFContractUnmarshallerJena.RESOURCE_PARAMETER_DIRECT = RDFContractUnmarshallerJena.MODEL
					.getResource(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "ParameterDirectInput");

			// define property use to find information into the RDF graph
			RDFContractUnmarshallerJena.PROPERTY_DESCRIPTION = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "description");
			RDFContractUnmarshallerJena.PROPERTY_HELP = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "help");
			RDFContractUnmarshallerJena.PROPERTY_INPUTMETHOD = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "inputMethod");
			RDFContractUnmarshallerJena.PROPERTY_METHODNAME = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "methodName");
			RDFContractUnmarshallerJena.PROPERTY_MIMETYPE = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "mimetype");
			RDFContractUnmarshallerJena.PROPERTY_NAME = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "name");
			RDFContractUnmarshallerJena.PROPERTY_PARAMETERNAME = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "parameterName");
			RDFContractUnmarshallerJena.PROPERTY_PROVIDER = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "provider");
			RDFContractUnmarshallerJena.PROPERTY_REFERENCE = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "reference");
			RDFContractUnmarshallerJena.PROPERTY_TYPE = RDFContractUnmarshallerJena.MODEL
					.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
			RDFContractUnmarshallerJena.PROPERTY_LANG = RDFContractUnmarshallerJena.MODEL
					.getProperty(RDFContractUnmarshallerJena.UCN_NAMESPACE
							+ "lang");

		} catch (final MalformedURLException e) {
			RDFContractUnmarshallerJena.logger.error("MalformedURLException : "
					+ e.getMessage(), e);
			e.printStackTrace();
		} catch (final IOException e) {
			RDFContractUnmarshallerJena.logger.error("IOException : "
					+ e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private String sID = null;

	private LocalizedString aLocalizedStringName = null;

	private LocalizedString aLocalizedStringDescription = null;

	private LocalizedString aLocalizedStringHelpLocation = null;

	private LocalizedString aLocalizedStringProvider = null;

	// name of parameter lang if observer has one
	private String nameOfLangParameter = null;

	public String getID() {
		return this.sID;
	}

	public LocalizedString getName() {
		return this.aLocalizedStringName;
	}

	public LocalizedString getDescription() {
		return this.aLocalizedStringDescription;
	}

	public LocalizedString getHelpLocation() {
		return this.aLocalizedStringHelpLocation;
	}

	public LocalizedString getProvider() {
		return this.aLocalizedStringProvider;
	}

	public String getNameOfLangParameter() {
		return this.nameOfLangParameter;
	}

	/**
	 * Map of different input method handle by the observer.
	 */
	private Map<EnumInputMethod, InputMethod> mapOfInputMethod = null;

	/**
	 * List of all method who can be used to call the observer. This list is
	 * from the parsing of a WADL file.
	 */
	private List<CallMethod> listOfCallMethod = null;

	/**
	 * RDF Model to parse the RDF file.
	 */
	private Model aModel = null;

	/**
	 * Constructor of the RDFUnmarshallerJena class.
	 * 
	 * @param listOfCallMethod
	 *            List of all method who can be used to call the observer.
	 */
	public RDFContractUnmarshallerJena(final List<CallMethod> listOfCallMethod) {
		// this.aObserverDescription = new ObserverDescription();
		this.aLocalizedStringName = new LocalizedString();
		this.aLocalizedStringDescription = new LocalizedString();
		this.aLocalizedStringHelpLocation = new LocalizedString();
		this.aLocalizedStringProvider = new LocalizedString();
		this.aModel = ModelFactory.createDefaultModel();
		this.listOfCallMethod = listOfCallMethod;
		this.mapOfInputMethod = new LinkedHashMap<EnumInputMethod, InputMethod>();
	}

	/**
	 * Return map of different input method handle by the observer.
	 */
	public Map<EnumInputMethod, InputMethod> getMapOfInputMethod() {
		return this.mapOfInputMethod;
	}

	public void addURL(final URL aURL) throws IOException {
		RDFContractUnmarshallerJena.logger.trace("addURL");
		if (RDFContractUnmarshallerJena.logger.isDebugEnabled()) {
			RDFContractUnmarshallerJena.logger.debug("URL : " + aURL + ".");
		}
		// read the RDF file
		final Model aModel = ModelFactory.createDefaultModel();
		aModel.read(aURL.openStream(), null);
		this.aModel.add(aModel);
	}

	/**
	 * Unmarshal a RDF file.
	 * 
	 * @param aURLRDF
	 *            The URL of the RDF file.
	 */
	public void unmarshal() throws IOException, MimeTypeParseException {
		RDFContractUnmarshallerJena.logger.trace("unmarshal");

		// find Observer into the RDF graph
		for (StmtIterator siObserver = this.aModel.listStatements(null,
				RDFContractUnmarshallerJena.PROPERTY_TYPE,
				RDFContractUnmarshallerJena.RESOURCE_OBSERVER); siObserver
				.hasNext();) {
			final Resource subject = siObserver.nextStatement().getSubject();
			// log
			if (RDFContractUnmarshallerJena.logger.isDebugEnabled()) {
				RDFContractUnmarshallerJena.logger.debug("Observer " + subject);
			}

			this.sID = subject.getProperty(
					RDFContractUnmarshallerJena.PROPERTY_REFERENCE)
					.getLiteral().getString();

			// find and add lang
			for (StmtIterator si = subject
					.listProperties(RDFContractUnmarshallerJena.PROPERTY_LANG); si
					.hasNext();) {
				final Literal l = si.nextStatement().getLiteral();
				this.nameOfLangParameter = l.getString();
			}

			// find and add input method
			for (StmtIterator aStatementIterator = subject
					.listProperties(RDFContractUnmarshallerJena.PROPERTY_INPUTMETHOD); aStatementIterator
					.hasNext();) {

				final Resource aResourceParameter = (Resource) aStatementIterator
						.nextStatement().getObject();

				final String sMethodName = aResourceParameter.getProperty(
						RDFContractUnmarshallerJena.PROPERTY_METHODNAME)
						.getLiteral().getString();
				// log
				RDFContractUnmarshallerJena.logger.debug("Method name = "
						+ sMethodName);

				final String sParameterName = aResourceParameter.getProperty(
						RDFContractUnmarshallerJena.PROPERTY_PARAMETERNAME)
						.getLiteral().getString();
				// log
				RDFContractUnmarshallerJena.logger.debug("Parameter name = "
						+ sParameterName);

				final InputMethod aInputMethod = new InputMethod();

				// find method
				for (Iterator iter = this.listOfCallMethod.iterator(); iter
						.hasNext();) {
					final CallMethod aCallMethod = (CallMethod) iter.next();
					if (aCallMethod.getID().equals(sMethodName)) {
						// log
						if (RDFContractUnmarshallerJena.logger.isDebugEnabled()) {
							RDFContractUnmarshallerJena.logger
									.debug("setMethod : " + aCallMethod.getID());
						}
						aInputMethod.setCallMethod(aCallMethod);
					}
				}

				aInputMethod.setCallParameter(aInputMethod.getCallMethod()
						.getMapOfCallParameter().get(sParameterName));
				// log
				if (RDFContractUnmarshallerJena.logger.isDebugEnabled()) {
					RDFContractUnmarshallerJena.logger.debug("setParameter : "
							+ aInputMethod.getCallParameter().getName());
				}

				if (aResourceParameter.hasProperty(
						RDFContractUnmarshallerJena.PROPERTY_TYPE,
						RDFContractUnmarshallerJena.RESOURCE_PARAMETER_URI)) {
					this.mapOfInputMethod
							.put(EnumInputMethod.URI, aInputMethod);
				} else if (aResourceParameter.hasProperty(
						RDFContractUnmarshallerJena.PROPERTY_TYPE,
						RDFContractUnmarshallerJena.RESOURCE_PARAMETER_UPLOAD)) {
					this.mapOfInputMethod.put(EnumInputMethod.UPLOAD,
							aInputMethod);
				} else if (aResourceParameter.hasProperty(
						RDFContractUnmarshallerJena.PROPERTY_TYPE,
						RDFContractUnmarshallerJena.RESOURCE_PARAMETER_DIRECT)) {
					this.mapOfInputMethod.put(EnumInputMethod.DIRECT,
							aInputMethod);
				} else {
					RDFContractUnmarshallerJena.logger
							.error("InputMethod has not specific type.");
				}

				// find and add mimetype handle by the observer
				for (StmtIterator si = aResourceParameter
						.listProperties(RDFContractUnmarshallerJena.PROPERTY_MIMETYPE); si
						.hasNext();) {
					final String s = si.nextStatement().getLiteral()
							.getString();
					if (RDFContractUnmarshallerJena.logger.isDebugEnabled()) {
						RDFContractUnmarshallerJena.logger.debug("MimeType : "
								+ s + ".");
					}
					aInputMethod.addMimeType(new MimeType(s));
				}

			}

			// find and add localized name
			for (StmtIterator aStatementIterator = subject
					.listProperties(RDFContractUnmarshallerJena.PROPERTY_NAME); aStatementIterator
					.hasNext();) {
				final Literal l = aStatementIterator.nextStatement()
						.getLiteral();
				RDFContractUnmarshallerJena.logger.debug("Name : lang="
						+ l.getLanguage() + " text=" + l.getString());
				this.aLocalizedStringName.addLocalization(l.getLanguage(), l
						.getString());
			}

			// find and add localized description
			for (StmtIterator si = subject
					.listProperties(RDFContractUnmarshallerJena.PROPERTY_DESCRIPTION); si
					.hasNext();) {
				final Literal l = si.nextStatement().getLiteral();
				RDFContractUnmarshallerJena.logger.debug("Description : lang="
						+ l.getLanguage() + " text=" + l.getString());
				this.aLocalizedStringDescription.addLocalization(l
						.getLanguage(), l.getString());
			}

			// find and add localized provider
			for (StmtIterator si = subject
					.listProperties(RDFContractUnmarshallerJena.PROPERTY_PROVIDER); si
					.hasNext();) {
				final Literal l = si.nextStatement().getLiteral();
				RDFContractUnmarshallerJena.logger.debug("Provider : lang="
						+ l.getLanguage() + " text=" + l.getString());
				this.aLocalizedStringProvider.addLocalization(l.getLanguage(),
						l.getString());
			}

			// find and add localized help
			for (StmtIterator si = subject
					.listProperties(RDFContractUnmarshallerJena.PROPERTY_HELP); si
					.hasNext();) {
				final Literal l = si.nextStatement().getLiteral();
				if (RDFContractUnmarshallerJena.logger.isDebugEnabled()) {
					RDFContractUnmarshallerJena.logger.debug("Help : lang="
							+ l.getLanguage() + " text=" + l.getString());
				}
				this.aLocalizedStringHelpLocation.addLocalization(l
						.getLanguage(), l.getString());
			}

		} // find Observer into the RDF graph

	}

}
