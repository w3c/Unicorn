package org.w3c.unicorn.response.parser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;
import org.w3.unicorn.observationresponse.ObservationresponseDocument;
import org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse;
import org.w3c.unicorn.response.A;
import org.w3c.unicorn.response.Code;
import org.w3c.unicorn.response.Error;
import org.w3c.unicorn.response.Img;
import org.w3c.unicorn.response.Info;
import org.w3c.unicorn.response.Inline;
import org.w3c.unicorn.response.Longmessage;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.Result;
import org.w3c.unicorn.response.Warning;
import org.w3c.unicorn.util.LocalizedString;

/**
 * Default parser class.
 * 
 */
public class DefaultParser implements ResponseParser {

	protected static final Log logger = LogFactory.getLog(DefaultParser.class);

	/**
	 * Parses the input and returns the response.
	 * 
	 * @param inputStream
	 *            The input stream.
	 * @return The corresponding response.
	 * @throws Exception 
	 */
	public Response parse(String r) throws Exception {
		ObservationresponseDocument ord = ObservationresponseDocument.Factory.parse(r);
		return swap(ord);
	}

	/**
	 * Creates and fills a Response object from a response document.
	 * 
	 * @param x
	 *            The response document.
	 * @param lang
	 *            The language of the response.
	 * @return The response object.
	 */
	protected Response swap(ObservationresponseDocument ord) {
		Observationresponse or = ord.getObservationresponse();
		Response res = new Response();
		XMLGregorianCalendar xmlGregorianCalendar;
		if (or.getDate() != null) {
			try {
				xmlGregorianCalendar = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(
								(GregorianCalendar) or.getDate());
				or.setDate(Calendar.getInstance());
				res.setDate(xmlGregorianCalendar);
			} catch (DatatypeConfigurationException e) {

				e.printStackTrace();
				logger.error("DatatypeConfigurationException erreur de date : "
						+ e.getMessage(), e);
				return null;
			}
		}
		res.setCheckedby(or.getCheckedby());
		res.setPassed(or.getPassed());
		res.setUri(or.getUri());
		res.setVersion(or.getVersion());

		org.w3.unicorn.observationresponse.ResultDocument.Result result = or
				.getResult();

		// Fill res.result

		if (result != null) {
			org.w3.unicorn.observationresponse.WarningsDocument.Warnings warnings = result
					.getWarnings();
			if (warnings != null && warnings.getWarninglistList() != null) {
				for (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist wl : warnings
						.getWarninglistList()) {
					String lang = warnings.getLang();
					Result r = new Result(lang, wl.getUri());
					for (org.w3.unicorn.observationresponse.WarningDocument.Warning w : wl
							.getWarningList()) {
						r.getWarnings().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}

			org.w3.unicorn.observationresponse.ErrorsDocument.Errors errors = result
					.getErrors();
			if (errors != null && errors.getErrorlistList() != null) {
				for (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist el : errors
						.getErrorlistList()) {
					String lang = errors.getLang();
					Result r = new Result(errors.getLang(), el.getUri());
					for (org.w3.unicorn.observationresponse.ErrorDocument.Error e : el.getErrorList()) {
						r.getErrors().add(swap(e, lang));
					}
					res.addResult(r);
				}
			}

			org.w3.unicorn.observationresponse.InformationsDocument.Informations informations = result
					.getInformations();
			if (informations != null && informations.getInfolistList() != null) {
				String lang = informations.getLang();
				for (org.w3.unicorn.observationresponse.InfolistDocument.Infolist il : informations
						.getInfolistList()) {
					Result r = new Result(informations.getLang(), il.getUri());
					for (org.w3.unicorn.observationresponse.InfoDocument.Info i : il
							.getInfoList()) {
						r.getInfos().add(swap(i, lang));
					}
					res.addResult(r);
				}
			}
		}

		return res;

	}

	/**
	 * Creates and fills a Warning object from a warning in a response.
	 * 
	 * @param x
	 *            The warning from the document.
	 * @param lang
	 *            The language of the warning.
	 * @return The resulting Warning object.
	 */
	private Warning swap(
			org.w3.unicorn.observationresponse.WarningDocument.Warning x,
			String lang) {
		Warning y = new Warning();
		try {
			y.setLine(x.getLine());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the line. Or set it to 0?
		}
		try {
			y.setColumn(x.getColumn());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the column. Or set it to 0?
		}
		try {
			y.setContext(x.getContext());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the context. Or set it to 0?
		}
		try {
			y.setLevel(x.getLevel());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the level. Or set it to 0?
		}
		try {
			y.setMessage(swapListMessage(x.getMessageList(), lang));
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the message. Or set it to ""?
		}
		try {
			y.setLongmessage(swapListLongmessage(x.getLongmessageList(), lang));
		}	 catch(XmlValueOutOfRangeException e) {
			// just don't set the longmessage. Or set it to ""?
		}
		return y;
	}

	/**
	 * Creates and fills a Error object from an error in a response.
	 * 
	 * @param x
	 *            The error from the document.
	 * @param lang
	 *            The language of the Error.
	 * @return The resulting Error object.
	 */
	private Error swap(
			org.w3.unicorn.observationresponse.ErrorDocument.Error x,
			String lang) {
		Error y = new Error();
		try {
			y.setLine(x.getLine());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the line. Or set it to 0?
		}
		try {
			y.setColumn(x.getColumn());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the column. Or set it to 0?
		}
		try {
			y.setContext(x.getContext());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the context. Or set it to 0?
		}
		try {
			y.setErrortype(x.getErrortype());
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the type. Or set it to 0?
		}
		try {
			y.setMessage(swapListMessage(x.getMessageList(), lang));
		} catch(XmlValueOutOfRangeException e) {
			// just don't set the message. Or set it to ""?
		}
		try {
			y.setLongmessage(swapListLongmessage(x.getLongmessageList(), lang));
		}	 catch(XmlValueOutOfRangeException e) {
			// just don't set the longmessage. Or set it to ""?
		}
		
		return y;
	}

	/**
	 * Creates and fills a Info object from an info in a response.
	 * 
	 * @param x
	 *            The info from the document.
	 * @param lang
	 *            The language of the Info.
	 * @return The resulting Info object.
	 */
	private Info swap(org.w3.unicorn.observationresponse.InfoDocument.Info x,
			String lang) {
		Info y = new Info();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setContext(x.getContext());
		y.setMessage(swapListMessage(x.getMessageList(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessageList(), lang));
		return y;
	}

	/**
	 * Returns a list of localized strings from a list of simple strings. and a
	 * given language.
	 * 
	 * @param x
	 *            The list of strings.
	 * @param lang
	 *            The language of the list.
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
	 * Returns a list of Longmessage objects from a list of longmessage in a
	 * response. and a given language.
	 * 
	 * @param x
	 *            The list of longmessage.
	 * @param lang
	 *            The language of the list.
	 * @return The list of Longmessage objects.
	 */
	private List<Longmessage> swapListLongmessage(
			List<org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage> x,
			String lang) {
		List<Longmessage> y = new ArrayList<Longmessage>();
		for (Object ox : x) {
			org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage cox = (org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage) ox;
			Longmessage coy = swap(cox, lang);
			y.add(coy);
		}
		return y;
	}

	/**
	 * Creates and fills a Longmessage object from a longmessage in a response.
	 * 
	 * @param x
	 *            The info document.
	 * @param lang
	 *            The language of the Info.
	 * @return The resulting Info object.
	 */
	private Longmessage swap(
			org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage x,
			String lang) {
		Longmessage y = new Longmessage();
		y.setLang(lang);
		List<Object> list = new ArrayList<Object>();
		list = swapObj(x, lang);
		y.setContent(list);
		return y;

	}

	/**
	 * Parses an Xml extract corresponding to an object and returns its content.
	 * 
	 * @param obj
	 *            The xml extract.
	 * @param lang
	 *            The language of the document.
	 * @return The content of the XmlObject.
	 */
	private List<Object> swapObj(XmlObject obj, String lang) {
		List<Object> list = new ArrayList<Object>();
		XmlCursor cursor = obj.newCursor();
		int count = 0;
		cursor.toNextToken();

		while (cursor.hasNextToken()) {

			if (cursor.isStart()) {
				count++;
				XmlObject current = cursor.getObject();

				// Should be useless
				if (current instanceof org.w3.unicorn.observationresponse.impl.LongmessageDocumentImpl.LongmessageImpl
						|| current instanceof org.w3.unicorn.observationresponse.impl.InfoDocumentImpl.InfoImpl) {

					break;
				}

				// Case : A
				else if (current instanceof org.w3.unicorn.observationresponse.impl.ADocumentImpl.AImpl) {
					org.w3.unicorn.observationresponse.ADocument.A a = (org.w3.unicorn.observationresponse.ADocument.A) current;

					A coy = new A();
					List<Object> content = new ArrayList<Object>();

					coy.setHref(a.getHref());

					content = swapObj(current, lang);
					coy.setContent(content);
					list.add(coy);

					for (Object o : content) {
						if (o instanceof Inline) {
							count++;
						}
					}

					cursor.toEndToken();

				}
				// Case : Code
				else if (current instanceof org.w3.unicorn.observationresponse.impl.CodeDocumentImpl.CodeImpl) {
					Code coy = new Code();
					List<Object> content = new ArrayList<Object>();
					content = swapObj(current, lang);
					coy.setContent(content);
					list.add(coy);
					for (Object o : content) {
						if (o instanceof Inline) {
							count++;
						}
					}
					cursor.toEndToken();

				}
				// Case : Img
				else if (current instanceof org.w3.unicorn.observationresponse.impl.ImgDocumentImpl.ImgImpl) {
					org.w3.unicorn.observationresponse.ImgDocument.Img img = (org.w3.unicorn.observationresponse.ImgDocument.Img) current;
					Img coy = new Img();
					coy.setAlt(img.getAlt());
					coy.setHeight(img.getHeight());
					coy.setLongdesc(img.getLongdesc());
					coy.setName(img.getName());
					coy.setSrc(img.getSrc());
					coy.setWidth(img.getWidth());
					list.add(coy);

					cursor.toEndToken();

				}

				// If the class is not recognized, we check the name and
				// eventually
				// build the corresponding object

				// Case : A from XmlAnyTypeImpl
				else if (cursor.getName().toString().equals(
						"{http://www.w3.org/unicorn/observationresponse}a")) {
					A coy = new A();

					List<Object> content = new ArrayList<Object>();

					cursor.toNextToken();
					coy.setHref(cursor.getTextValue());
					cursor.toPrevToken();
					cursor.push();
					content = swapObj(current, lang);
					cursor.pop();
					coy.setContent(content);
					list.add(coy);

					for (Object o : content) {
						if (o instanceof Inline) {
							count++;
						}
					}
					cursor.toEndToken();
					count--;

				}

				// Case : Code from XmlAnyTypeImpl
				else if (cursor.getName().toString().equals(
						"{http://www.w3.org/unicorn/observationresponse}code")) {

					Code coy = new Code();
					List<Object> content = new ArrayList<Object>();
					cursor.push();
					content = swapObj(current, lang);
					cursor.pop();
					coy.setContent(content);
					list.add(coy);
					for (Object o : content) {
						if (o instanceof Inline) {
							count++;
						}
					}
					cursor.toEndToken();
					count--;
				}

				// Case : Img from XmlAnyTypeImpl
				else if (cursor.getName().toString().equals(
						"{http://www.w3.org/unicorn/observationresponse}img")) {

					cursor.toNextToken();

					Img coy = new Img();
					while (cursor.isAttr() && cursor.hasNextToken()) {

						if (cursor.getName().toString().equals("src")) {
							coy.setSrc(cursor.getTextValue());
						} else if (cursor.getName().toString().equals("alt")) {
							coy.setAlt(cursor.getTextValue());
						} else if (cursor.getName().toString().equals(
								"longdesc")) {
							coy.setLongdesc(cursor.getTextValue());
						} else if (cursor.getName().toString().equals("name")) {
							coy.setName(cursor.getTextValue());
						} else if (cursor.getName().toString().equals("width")) {
							coy.setWidth(new BigInteger(cursor.getTextValue()));
						} else if (cursor.getName().toString().equals("height")) {
							coy
									.setHeight(new BigInteger(cursor
											.getTextValue()));
						}

						cursor.toNextToken();
					}
					list.add(coy);
					cursor.toEndToken();
					count--;

				}

				// We still want to append what's inside a block even if the
				// block's unknown
				else {
					cursor.push();
					list.addAll(swapObj(current, lang));
					cursor.pop();
					cursor.toEndToken();
					count--;
				}
			}

			else if (cursor.isText()) {
				LocalizedString ls = new LocalizedString(cursor.getChars(),
						lang);
				list.add(ls);
			}

			else if (cursor.isEnd()) {
				count--;
				if (count <= 0) {
					break;
				}
			}

			cursor.toNextToken();
		}

		cursor.dispose();
		return list;
	}

}
