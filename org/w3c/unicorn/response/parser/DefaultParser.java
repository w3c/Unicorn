package org.w3c.unicorn.response.parser;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import org.w3c.unicorn.response.A;
import org.w3c.unicorn.response.Code;
import org.w3c.unicorn.response.Error;
import org.w3c.unicorn.response.Img;
import org.w3c.unicorn.response.Info;
import org.w3c.unicorn.response.Longmessage;
import org.w3c.unicorn.response.Response;
import org.w3c.unicorn.response.Result;
import org.w3c.unicorn.response.Warning;
import org.w3c.unicorn.util.LocalizedString;
import java.io.*;

/**
 * Default parser class.
 * 
 */
public class DefaultParser implements ResponseParser {
	protected static final Log logger = LogFactory
			.getLog("org.w3c.unicorn.response.parser.DefaultParser");

	/**
	 * Parses the input and returns the response.
	 * @param inputStream The input stream.
	 * @return The corresponding response.
	 */
	public Response parse(InputStream inputStream) {
			try {
          org.w3.unicorn.observationresponse.ObservationresponseDocument ord = org.w3.unicorn.observationresponse.ObservationresponseDocument.Factory.parse(inputStream);
          return swap(ord);
			} catch (XmlException e) {
				e.printStackTrace();
				logger.error("XMLBeansException : " + e.getMessage(), e);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("XMLBeansException : " + e.getMessage(), e);
				return null;
      }

	}

    /**
	 * Parses the input and returns the response.
	 * @param inputStream The input stream.
	 * @return The corresponding response.
	 */
	public Response parse(String r) {
			try {
          org.w3.unicorn.observationresponse.ObservationresponseDocument ord = org.w3.unicorn.observationresponse.ObservationresponseDocument.Factory.parse(r);
          return swap(ord);
			} catch (XmlException e) {
				e.printStackTrace();
				logger.error("XMLBeansException : " + e.getMessage(), e);
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
	private List<LocalizedString> swapListMessage(String[] x, String lang) {
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
			} else if (ox instanceof org.w3.unicorn.observationresponse.ADocument.A) {
				org.w3.unicorn.observationresponse.ADocument.A cox = (org.w3.unicorn.observationresponse.ADocument.A) ox;
				A coy = new A();
				coy.setHref(cox.getHref());
				
				
				XmlCursor cursor=cox.newCursor();
				//System.err.println("Debut");
				//System.err.println(cursor.xmlText());
				
			    ArrayList<Object> listObj = new ArrayList<Object>();
				
				while (cursor.hasNextToken()) {
					cursor.toNextToken();
					//System.err.println(cursor.currentTokenType().intValue()==TokenType.INT_END);
					Object current = cursor.getObject();
					if(current!=null) {
						//System.err.println(current.getClass());
						
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.LongmessageDocumentImpl.LongmessageImpl.class) {
							break;
						}
						// Case : A
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.ADocumentImpl.AImpl.class) {
							org.w3.unicorn.observationresponse.ADocument.A
							a = (org.w3.unicorn.observationresponse.ADocument.A) current;
							listObj.add(a);
							//We skip 3 tokens to forget about the inner text in the A tag.
							//We don't want it in the object list
							cursor.toNextToken();
							cursor.toNextToken();
							cursor.toNextToken();
						}
						// Case : Code
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.CodeDocumentImpl.CodeImpl.class) {
							org.w3.unicorn.observationresponse.CodeDocument.Code code=(org.w3.unicorn.observationresponse.CodeDocument.Code) current;
							
							listObj.add(code);
						}
						// Case : Img
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.ImgDocumentImpl.ImgImpl.class) {
							org.w3.unicorn.observationresponse.ImgDocument.Img
							img = (org.w3.unicorn.observationresponse.ImgDocument.Img) current;
							listObj.add(img);
						}
					}
					else if(cursor.isText()){
						listObj.add(cursor.getTextValue());
					}
				
				}
				coy.setContent(swap(listObj, lang));
				y.add(coy);
				
				} else if (ox instanceof org.w3.unicorn.observationresponse.CodeDocument.Code) {
				org.w3.unicorn.observationresponse.CodeDocument.Code cox = (org.w3.unicorn.observationresponse.CodeDocument.Code) ox;
				Code coy = new Code();
				
				
				XmlCursor cursor=cox.newCursor();
				//System.err.println("Debut");
				//System.err.println(cursor.xmlText());
				
			    ArrayList<Object> listObj = new ArrayList<Object>();
				
				while (cursor.hasNextToken()) {
					cursor.toNextToken();
					//System.err.println(cursor.currentTokenType().intValue()==TokenType.INT_END);
					Object current = cursor.getObject();
					if(current!=null) {
						//System.err.println(current.getClass());
						
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.LongmessageDocumentImpl.LongmessageImpl.class) {
							break;
						}
						// Case : A
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.ADocumentImpl.AImpl.class) {
							org.w3.unicorn.observationresponse.ADocument.A
							a = (org.w3.unicorn.observationresponse.ADocument.A) current;
							listObj.add(a);
//							We skip 3 tokens to forget about the inner text in the A tag.
							//We don't want it in the object list
							cursor.toNextToken();
							cursor.toNextToken();
							cursor.toNextToken();
						}
						// Case : Code
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.CodeDocumentImpl.CodeImpl.class) {
							org.w3.unicorn.observationresponse.CodeDocument.Code code=(org.w3.unicorn.observationresponse.CodeDocument.Code) current;
							
							listObj.add(code);
						}
						// Case : Img
						if (current.getClass()==
							org.w3.unicorn.observationresponse.impl.ImgDocumentImpl.ImgImpl.class) {
							org.w3.unicorn.observationresponse.ImgDocument.Img
							img = (org.w3.unicorn.observationresponse.ImgDocument.Img) current;
							listObj.add(img);
						}
					}
					else if(cursor.isText()){
						listObj.add(cursor.getTextValue());
					}
				
				}
				
				
				coy.setContent(swap(listObj, lang));
				y.add(coy);
			} else if (ox instanceof org.w3.unicorn.observationresponse.ImgDocument.Img) {
				org.w3.unicorn.observationresponse.ImgDocument.Img cox = (org.w3.unicorn.observationresponse.ImgDocument.Img) ox;
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
			org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage x,
			String lang) {
		Longmessage y = new Longmessage();
		XmlCursor cursor=x.newCursor();
		//System.err.println("Debut");
		//System.err.println(cursor.xmlText());
		
	    ArrayList<Object> listObj = new ArrayList<Object>();
		
		while (cursor.hasNextToken()) {
			cursor.toNextToken();
			//System.err.println(cursor.currentTokenType().intValue()==TokenType.INT_END);
			Object current = cursor.getObject();
			if(current!=null) {
				//System.err.println(current.getClass());
				
				if (current.getClass()==
					org.w3.unicorn.observationresponse.impl.LongmessageDocumentImpl.LongmessageImpl.class) {
					break;
				}
				// Case : A
				if (current.getClass()==
					org.w3.unicorn.observationresponse.impl.ADocumentImpl.AImpl.class) {
					org.w3.unicorn.observationresponse.ADocument.A
					a = (org.w3.unicorn.observationresponse.ADocument.A) current;
					listObj.add(a);
					//We skip 3 tokens to forget about the inner text in the A tag.
					//We don't want it in the object list
					cursor.toNextToken();
					cursor.toNextToken();
					cursor.toNextToken();
				}
				// Case : Code
				if (current.getClass()==
					org.w3.unicorn.observationresponse.impl.CodeDocumentImpl.CodeImpl.class) {
					org.w3.unicorn.observationresponse.CodeDocument.Code code=(org.w3.unicorn.observationresponse.CodeDocument.Code) current;
					
					listObj.add(code);
				}
				// Case : Img
				if (current.getClass()==
					org.w3.unicorn.observationresponse.impl.ImgDocumentImpl.ImgImpl.class) {
					org.w3.unicorn.observationresponse.ImgDocument.Img
					img = (org.w3.unicorn.observationresponse.ImgDocument.Img) current;
					listObj.add(img);
				}
			}
			else if(cursor.isText()){
				listObj.add(cursor.getTextValue());
			}
		
		}
		
		//System.err.println("List : " + listObj);
		//System.err.println("Fin");
			
		y.setContent(swap(listObj, lang));
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
			org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage[] x,
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
	 * Swaps a warning : returns the result with the corresponding language.
	 * @param x The warning to swap.
	 * @param lang The language of the warning.
	 * @return The swapped warning.
	 */
	private Warning swap(
			org.w3.unicorn.observationresponse.WarningDocument.Warning x, String lang) {
		Warning y = new Warning();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setContext(x.getContext());
		y.setLevel(x.getLevel());
		y.setMessage(swapListMessage(x.getMessageArray(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessageArray(), lang));
		return y;
	}

	/**
	 * Swaps an error : returns the result with the corresponding language.
	 * @param x The error to swap.
	 * @param lang The language of the error.
	 * @return The swapped error.
	 */
	private Error swap(org.w3.unicorn.observationresponse.ErrorDocument.Error x,
			String lang) {
		Error y = new Error();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setErrortype(x.getErrortype());
		y.setContext(x.getContext());
		y.setMessage(swapListMessage(x.getMessageArray(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessageArray(), lang));
		return y;
	}

	/**
	 * Swaps an info : returns the result with the corresponding language.
	 * @param x The info to swap.
	 * @param lang The language of the info.
	 * @return The swapped info.
	 */
	private Info swap(org.w3.unicorn.observationresponse.InfoDocument.Info x,
			String lang) {
		Info y = new Info();
		y.setLine(x.getLine());
		y.setColumn(x.getColumn());
		y.setContext(x.getContext());
		y.setMessage(swapListMessage(x.getMessageArray(), lang));
		y.setLongmessage(swapListLongmessage(x.getLongmessageArray(), lang));
		return y;
	}

	/**
	 * Swaps a response : returns the result with the corresponding language.
	 * @param x The response to swap.
	 * @param lang The language of the response.
	 * @return The swapped response.
	 */
	private Response swap(org.w3.unicorn.observationresponse.ObservationresponseDocument ord) {
		Response res = new Response();
		org.w3.unicorn.observationresponse.ObservationresponseDocument.Observationresponse or=ord.getObservationresponse();
		res.setUri(or.getUri());
		res.setCheckedby(or.getCheckedby());
		res.setVersion(or.getVersion());
		XMLGregorianCalendar xmlGregorianCalendar;
		if (or.getDate()!=null)
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)or.getDate());
			or.setDate(Calendar.getInstance());
			res.setDate(xmlGregorianCalendar);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			logger.error("DatatypeConfigurationException erreur de date : " + e.getMessage(), e);
			return null;
		}

		res.setPassed(or.getPassed());

		// Fill res.result
		org.w3.unicorn.observationresponse.ResultDocument.Result rrr = or.getResult();
		if (rrr != null) {
			org.w3.unicorn.observationresponse.WarningsDocument.Warnings warnings = rrr.getWarnings();
			if (warnings != null && warnings.getWarninglistArray() != null) {
				for (org.w3.unicorn.observationresponse.WarninglistDocument.Warninglist wl : warnings.getWarninglistArray()) {
					String lang = warnings.getLang();
					Result r = new Result(lang, wl.getUri());
					for (org.w3.unicorn.observationresponse.WarningDocument.Warning w : wl.getWarningArray()) {
						r.getWarnings().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}

			org.w3.unicorn.observationresponse.ErrorsDocument.Errors errors = rrr.getErrors();
			if (errors != null && errors.getErrorlistArray() != null) {
				for (org.w3.unicorn.observationresponse.ErrorlistDocument.Errorlist wl : errors.getErrorlistArray()) {
					String lang = errors.getLang();
					Result r = new Result(errors.getLang(), wl.getUri());
					for (org.w3.unicorn.observationresponse.ErrorDocument.Error w : wl
							.getErrorArray()) {
						r.getErrors().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}

			org.w3.unicorn.observationresponse.InformationsDocument.Informations informations = rrr.getInformations();
			if (informations != null && informations.getInfolistArray() != null) {
				String lang = informations.getLang();
				for (org.w3.unicorn.observationresponse.InfolistDocument.Infolist wl : informations.getInfolistArray()) {
					Result r = new Result(informations.getLang(), wl.getUri());
					for (org.w3.unicorn.observationresponse.InfoDocument.Info w : wl
							.getInfoArray()) {
						r.getInfos().add(swap(w, lang));
					}
					res.addResult(r);
				}
			}
		}

		return res;
	}

}
