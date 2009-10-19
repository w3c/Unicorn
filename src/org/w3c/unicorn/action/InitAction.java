package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.exceptions.InitializationFailedException;
import org.w3c.unicorn.util.Property;

/**
 * Servlet implementation class InitAction
 */
public class InitAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitAction() {
		Framework.init();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// If PROPERTY_INIT_ACTION is not set or set to true, InitAction is only accessible from localhost.
		// If PROPERTY_INIT_ACTION is set to false, any IP can initialize Unicorn. This should not be set in production environment.
		String isProtected = Property.get("PROTECT_INIT_ACTION");
		if ((isProtected == null || isProtected.equals("true")) && (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") || request.getRemoteAddr().equals("127.0.0.1"))
				|| (isProtected != null && isProtected.equals("false"))) {
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			String task = request.getParameter("task"); 
			
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
		else
			response.sendError(403, "You are not allowed to execute this action.");
	}
	
}
