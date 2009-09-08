// $Id: IndexAction.java,v 1.14 2009-09-08 14:23:33 tgambet Exp $Id $
// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;

public class IndexAction extends Action {

	private static final long serialVersionUID = 599055553694915687L;
	
	private static Log logger = LogFactory.getLog(IndexAction.class);
	
	private VelocityContext velocityContext;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		super.doGet(req, resp);
		
		resp.setContentType("text/html; charset=UTF-8");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		String paramPrefix = Property.get("UNICORN_PARAMETER_PREFIX");
		String lang = getLanguage(req.getParameter(paramPrefix + "lang"), req, messages);
		String task = getTask(req.getParameter(paramPrefix + "task"), null);
		String queryString = getQueryStringWithout(paramPrefix + "lang", req);
		
		if (req.getAttribute("unicorn_message") != null)
			messages.add((Message) req.getAttribute("unicorn_message"));
		
		velocityContext = new VelocityContext(Language.getContext(lang));
		velocityContext.put("queryString", queryString);
		velocityContext.put("messages", messages);
		velocityContext.put("current_task", Framework.mapOfTask.get(task));
		
		/*messages.add(new Message(Message.Level.WARNING, "un warning", null));
		messages.add(new Message(Message.Level.ERROR, "une error", null));
		messages.add(new Message(Message.Level.INFO, "une info", null));
		messages.add(new Message(Message.Level.WARNING, "un warning avec long message", "le long message\nle long message\nle long message\nle long message\nle long message\nle long message\n"));
		messages.add(new Message(Message.Level.ERROR, "une error avec long message",  "le long message\nle long message\nle long message\nle long message\nle long message\nle long message\n"));
		messages.add(new Message(Message.Level.INFO, "une info avec long message",  "le long message\nle long message\nle long message\nle long message\nle long message\nle long message\nle long message\n"));*/
		
		if (req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			Templates.write("parameters.vm", velocityContext, resp.getWriter());
			resp.getWriter().close();
		} else {
			Templates.write("index.vm", velocityContext, resp.getWriter());
			resp.getWriter().close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
