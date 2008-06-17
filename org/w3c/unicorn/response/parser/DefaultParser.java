package org.w3c.unicorn.response.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.generated.observationresponse.Errorlist;
import org.w3c.unicorn.generated.observationresponse.Errors;
import org.w3c.unicorn.generated.observationresponse.Infolist;
import org.w3c.unicorn.generated.observationresponse.Informations;
import org.w3c.unicorn.generated.observationresponse.Observationresponse;
import org.w3c.unicorn.generated.observationresponse.Warninglist;
import org.w3c.unicorn.generated.observationresponse.Warnings;
import org.w3c.unicorn.response.A;
import org.w3c.unicorn.response.Code;
import org.w3c.unicorn.response.Error;
import org.w3c.unicorn.response.Img;
import org.w3c.unicorn.response.Info;
import org.w3c.unicorn.response.Longmessage;
import org.w3c.unicorn.response.Warning;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.Result;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Default parser class.
 * 
 */
public class DefaultParser implements ResponseParser {
	protected static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.response.parser.DefaultParser");

	private static JAXBContext aJAXBContext = null;

	private static Unmarshaller aUnmarshaller = null;

	static {
		try {
			aJAXBContext = JAXBContext
					.newInstance("org.w3c.unicorn.generated.observationresponse");
			aUnmarshaller = aJAXBContext.createUnmarshaller();
		} catch (final JAXBException e) {
			logger.error("JAXBException : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Parses the input and returns the response.
	 * @param inputStream The input stream.
	 * @return The corresponding response.
	 */
	public Response parse(InputStream inputStream) {
		try {
			return swap((Observationresponse) (aUnmarshaller
					.unmarshal(inputStream)));
		} catch (JAXBException e) {
			logger.error("JAXBException : " + e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a list of localized strings from a list of simple strings
	 * and a given language.
	 * @param x The list of strings.
	 * @param lang The language of the list.
	 * @return The new list of localized strings.
	 */
	private List<LocalizedString> swapListMessage(List<String> x, String lang) {
		List<LocalizedString> y = new ArrayList<LocalizedString>();
		for (Object ox : x) {
			String cox = (String) ox;
			LocalizedString coy = new LocalizedString(cox, lang);
			y.add(coy);
		}
		return y;
	}

	/**
	 * Returns a list of localized objects from a list of simple objects
	 * and a given language. 
	 * @param x The initial list of objects.
	 * @param lang The language of the list.
	 * @return The new list of localized objects.
	 */
	private List<Object> swap(List<Object> x, String lang) {
		List<Object> y = new ArrayList<Object>();
		for (Object ox : x) {
			if (ox instanceof String) {
				String cox = (String) ox;
				LocalizedString coy = new LocalizedString(cox, lang);
				y.add(coy);
			} else if (ox instanceof org.w3c.unicorn.generated.observationresponse.A) {
				org.w3c.unicorn.generated.observationresponse.A cox = (org.w3c.unicorn.generated.observationresponse.A) ox;
				A coy = new A();
				coy.setHref(cox.getHref());
				coy.setContent(swap(cox.getContent(), lang));
				y.add(coy);
			} else if (ox instanceof org.w3c.unicorn.generated.observationresponse.Code) {
				org.w3c.unicorn.generated.observationresponse.Code cox = (org.w3c.unicorn.generated.observationresponse.Code) ox;
				Code coy = new Code();
				coy.setContent(swap(cox.getContent(), lang));
				y.add(coy);
			} else if (ox instanceof org.w3c.unicorn.generated.observationresponse.Img) {
				org.w3c.unicorn.generated.observationresponse.Img cox = (org.w3c.unicorn.generated.observationresponse.Img) ox;
				Img coy = new Img();
				coy.setAlt(cox.getAlt());
				coy.setHeight(cox.getHeight());
				coy.setLongdesc(cox.getLongdesc());
				coy.setName(cox.getName());
				coy.setSrc(cox.getSrc());
				coy.setWidth(cox.getWidth());
				y.add(coy);
			}
		}
		return y;
	}

	/**
	 * Swaps a message : returns the result with the corresponding language.
	 * @param x The message to swap.
	 * @param lang The language of the message.
	 * @return The swapped message.
	 */
	private Longmessage swap(
			org.w3c.unicorn.generated.observationresponse.Longmessage x,
			String lang) {
		Longmessage y = new Longmessage();
		y.setContent(swap(x.getContent(), lang));
		return y;
	}

	/**
	 * Returns a list of localized messages from a list of simple messages
	 * and a given language.
	 * @param x The list of messages.
	 * @param lang The language of the list.
	 * @return The new list of localized messages.
	 */
	private List<Longmessage> swapListLongmessage(
			List<org.w3c.unicorn.generated.observationresponse.Longmessage> x,
			String lang) {
		List<Longmessage> y = new ArrayList<Longmessage>();
		for (Object ox : x) {
			org.w3c.unicorn.generated.observationresponse.Longmessage cox = (org.w3c.unicorn.generated.observationresponse.Longmessage) ox;
			Longmessage coy = swap(cox, lang);
			y.add(coy);
		}
		return y;
	}

	/**
	 * Swaps a warning : returns the result with the corresponding language.
	 * @param x The warning to swap.
	 * @param lang The language of the warning.
	 * @return The swapped warning.
	 */
	private Warning swap(
			org.w3c.unicorn.generated.observationresponse.Warning x, String lang) {
		Warning y = new Warning();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setContext(x.getContext());
		y.setLevel(x.getLevel());
		y.setMessage(swapListMessage(x.getMessage(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessage(), lang));
		return y;
	}

	/**
	 * Swaps an error : returns the result with the corresponding language.
	 * @param x The error to swap.
	 * @param lang The language of the error.
	 * @return The swapped error.
	 */
	private Error swap(org.w3c.unicorn.generated.observationresponse.Error x,
			String lang) {
		Error y = new Error();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setErrortype(x.getErrortype());
		y.setContext(x.getContext());
		y.setMessage(swapListMessage(x.getMessage(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessage(), lang));
		return y;
	}

	/**
	 * Swaps an info : returns the result with the corresponding language.
	 * @param x The info to swap.
	 * @param lang The language of the info.
	 * @return The swapped info.
	 */
	private Info swap(org.w3c.unicorn.generated.observationresponse.Info x,
			String lang) {
		Info y = new Info();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setContext(x.getContext());
		y.setMessage(swapListMessage(x.getMessage(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessage(), lang));
		return y;
	}

	/**
	 * Swaps a response : returns the result with the corresponding language.
	 * @param x The response to swap.
	 * @param lang The language of the response.
	 * @return The swapped response.
	 */
	private Response swap(Observationresponse or) {
		Response res = new Response();
		res.setUri(or.getUri());
		res.setCheckedby(or.getCheckedby());
		res.setVersion(or.getVersion());
		res.setDate(or.getDate());
		res.setPassed(or.isPassed());

		// Fill res.result
		org.w3c.unicorn.generated.observationresponse.Result rrr = or
				.getResult();
		if (rrr != null) {
			Warnings warnings = rrr.getWarnings();
			if (warnings != null && warnings.getWarninglist() != null) {
				for (Warninglist wl : warnings.getWarninglist()) {
					String lang = warnings.getLang();
					Result r = new Result(lang, wl.getUri());
					for (org.w3c.unicorn.generated.observationresponse.Warning w : wl
							.getWarning()) {
						r.getWarnings().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}

			Errors errors = rrr.getErrors();
			if (errors != null && errors.getErrorlist() != null) {
				for (Errorlist wl : errors.getErrorlist()) {
					String lang = errors.getLang();
					Result r = new Result(errors.getLang(), wl.getUri());
					for (org.w3c.unicorn.generated.observationresponse.Error w : wl
							.getError()) {
						r.getErrors().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}

			Informations informations = rrr.getInformations();
			if (informations != null && informations.getInfolist() != null) {
				String lang = informations.getLang();
				for (Infolist wl : informations.getInfolist()) {
					Result r = new Result(informations.getLang(), wl.getUri());
					for (org.w3c.unicorn.generated.observationresponse.Info w : wl
							.getInfo()) {
						r.getInfos().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}
		}

		return res;
	}

}
