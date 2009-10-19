package org.w3c.unicorn.response;
//$Id: ResponseFactory.java,v 1.1 2009-10-16 16:25:00 tgambet Exp $
//Author: Thomas Gambet
//(c) COPYRIGHT MIT, ERCIM and Keio, 2009.
//Please first read the full copyright statement in file COPYRIGHT.html
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.response.impl.DefaultResponseXBeans;

public class ResponseFactory {

	private static final Log logger = LogFactory.getLog(ResponseFactory.class);

	public static Response getResponse(InputStream is, String responseType, String requestUri, String charset) throws UnicornException {
		
		Response res;
		try {
			if (Framework.responseImpl.get(responseType) != null) {
				res = Framework.responseImpl.get(responseType).getConstructor(InputStream.class, String.class).newInstance(is, charset);
			} else if (Framework.responseImpl.get("default") != null) {
				res = Framework.responseImpl.get("default").getConstructor(InputStream.class, String.class).newInstance(is, charset);
				logger.warn("ResponseType unknown: " + responseType + ". The default responseType is instanciated instead.");
			} else {
				res = new DefaultResponseXBeans(is, charset);
			}
			res.setRequestUri(requestUri);
			return res;
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof UnicornException)
				throw (UnicornException) e.getCause();
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}  catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
}