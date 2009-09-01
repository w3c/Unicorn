package org.w3c.unicorn.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;
import org.w3c.unicorn.util.Templates;

public class IndexAction extends Action {

	private static final long serialVersionUID = 1L;
	private VelocityContext velocityContext;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!Framework.isUcnInitialized) {
			resp.sendError(500, "Unicorn is not initialized properly. Check logs.");
			return;
		}
		
		resp.setContentType("text/html; charset=UTF-8");
		
		// Language negotiation
		String langParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "lang");
		if (langParameter == null || !Framework.getLanguageProperties().containsKey(langParameter))
			langParameter = Language.negociate(req.getLocales());
		
		velocityContext = new VelocityContext(Language.getContext(langParameter));
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		/*messages.add(new Message(Message.Level.WARNING, "un warning", null));
		messages.add(new Message(Message.Level.ERROR, "une error", null));
		messages.add(new Message(Message.Level.INFO, "une info", null));
		messages.add(new Message(Message.Level.WARNING, "un warning avec long message", "le long message\nle long message\nle long message\nle long message\nle long message\nle long message\n"));
		messages.add(new Message(Message.Level.ERROR, "une error avec long message",  "le long message\nle long message\nle long message\nle long message\nle long message\nle long message\n"));
		messages.add(new Message(Message.Level.INFO, "une info avec long message",  "le long message\nle long message\nle long message\nle long message\nle long message\nle long message\nle long message\n"));*/
		
		if (!Language.isComplete(langParameter)) {
			Message mess = new Message(Message.Level.INFO, "incomplete language", null);
			messages.add(mess);
			//velocityContext.put("message", mess);
		}
		
		if (req.getAttribute("unicorn_message") != null)
			//velocityContext.put("message", req.getAttribute("unicorn_message"));
			messages.add((Message) req.getAttribute("unicorn_message"));
		
		velocityContext.put("messages", messages);
		
		String taskParameter = req.getParameter(Property.get("UNICORN_PARAMETER_PREFIX") + "task");
		if (taskParameter == null || !Framework.mapOfTask.containsKey(taskParameter))
			taskParameter = Framework.mapOfTask.getDefaultTaskId();
		
		velocityContext.put("current_task", Framework.mapOfTask.get(taskParameter));
		
		
		
		if (req.getHeader("X-Requested-With") != null && req.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			//for JavaScript testing purposes
			/*long s = System.currentTimeMillis();
			long t;
			do {
				 t = System.currentTimeMillis();
			} while ((t - s) < 3000);*/
			// -----------------
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
