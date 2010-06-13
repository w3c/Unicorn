package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.InitializationFailedException;
import org.w3c.unicorn.util.Language;
import org.w3c.unicorn.util.Templates;

import com.ibm.icu.util.ULocale;

/**
 * Servlet implementation class InitAction
 */
public class InitAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitAction() {
		Framework.initUnicorn();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String task = request.getParameter("task"); 
		PrintWriter out = response.getWriter();
		
		if (task == null) {
			response.setContentType("text/html; charset=UTF-8");
			VelocityContext velocityContext = new VelocityContext(Language.getContext(Language.getDefaultLocale()));
			velocityContext.put("baseUri", "./");
			velocityContext.put("user", request.getRemoteUser());
			ArrayList<ULocale> languages = new ArrayList<ULocale>();
			languages.add(Language.getDefaultLocale());
			velocityContext.put("languages", languages);
			
			Templates.write("init.vm", velocityContext, out);
		} else {
			response.setContentType("text/plain");
			
			
			
			if (task == null || task.equals("all")) {
				
				Framework.reset();
				
				out.write("Initializing core: ");
				response.flushBuffer();
				try {
					Framework.initCore();
					out.write("OK\n");
				} catch (InitializationFailedException e) {
					Framework.logger.fatal(e.getMessage(), e);
					out.write("FAILED\n" + e);
					Framework.isUcnInitialized = false;
					return;
				}
				
				out.write("Loading config files: ");
				response.flushBuffer();
				try {
					Framework.initConfig();
					out.write("OK\n");
				} catch (InitializationFailedException e) {
					Framework.logger.fatal(e.getMessage(), e);
					out.write("FAILED\n" + e);
					Framework.isUcnInitialized = false;
					return;
				}
				
				out.write("Initializing unmarshallers: ");
				response.flushBuffer();
				Framework.initUnmarshallers();
				out.write("OK\n");
				
				out.write("Initializing response parsers: ");
				response.flushBuffer();
				try {
					Framework.initResponseImplementations();
					out.write("OK\n");
				} catch (InitializationFailedException e) {
					Framework.logger.fatal(e.getMessage(), e);
					out.write("FAILED\n" + e);
					Framework.isUcnInitialized = false;
					return;
				}
			}
			
			if (task == null || task.equals("all") || task.equals("observers")) {
				
				if (!Framework.isUcnInitialized && task != null && task.equals("observers")) {
					out.write("Unable to reload the observers because Unicorn is not initialized.\n" +
							"You should initialize Unicorn fully and successfully one time before trying to perform this task (/init?task=all).");
					out.close();
					return;
				}
					
				out.write("Loading observers: ");
				response.flushBuffer();
				try {
					Framework.initObservers();
					out.write("OK\n");
				} catch (InitializationFailedException e) {
					Framework.logger.fatal(e.getMessage(), e);
					out.write("FAILED\n" + e);
					Framework.isUcnInitialized = false;
					return;
				}
			}
			
			if (task == null || task.equals("all") || task.equals("language")) {
				
				if (!Framework.isUcnInitialized && task != null && task.equals("language")) {
					out.write("Unable to reload the language files because Unicorn is not initialized.\n" +
							"You should initialize Unicorn fully and successfully one time before trying to perform this task (/init?task=all).");
					out.close();
					return;
				}
				
				out.write("Loading language files: ");
				response.flushBuffer();
				try {
					Framework.initLanguages();
					out.write("OK\n");
				} catch (InitializationFailedException e) {
					Framework.logger.fatal(e.getMessage(), e);
					out.write("FAILED\n" + e);
					Framework.isUcnInitialized = false;
					return;
				}
			}
			
			if (task == null || task.equals("all") || task.equals("tasklist")) {
				
				if (!Framework.isUcnInitialized && task != null && task.equals("tasklist")) {
					out.write("Unable to reload the tasklist because Unicorn is not initialized.\n" +
							"You should initialize Unicorn fully and successfully one time before trying to perform this task (/init?task=all).");
					out.close();
					return;
				}
				
				out.write("Loading tasklist: ");
				response.flushBuffer();
				try {
					Framework.initTasklists();
					out.write("OK\n");
				} catch (InitializationFailedException e) {
					Framework.logger.fatal(e.getMessage(), e);
					out.write("FAILED\n" + e);
					Framework.isUcnInitialized = false;
					return;
				}
			}
				
			out.write("Initializing Velocity: ");
			response.flushBuffer();
			try {
				Framework.initVelocity();
				out.write("OK\n");
			} catch (InitializationFailedException e) {
				Framework.logger.fatal(e.getMessage(), e);
				out.write("FAILED\n" + e);
				Framework.isUcnInitialized = false;
				return;
			}
			
			out.close();
		}
	}
}
