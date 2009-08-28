package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.unicorn.Framework;
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
		// If PROPERTY_INIT_ACTION is set to true, any IP can initialize Unicorn. This should not be set in production environment.
		String isProtected = Property.get("PROTECT_INIT_ACTION");
		if ((isProtected == null || isProtected.equals("true")) && (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") || request.getRemoteAddr().equals("127.0.0.1"))
				|| (isProtected != null && isProtected.equals("false"))) {
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.write("Initializing core: ");
			response.flushBuffer();
			Framework.initCore();
			
			out.write("OK\nLoading config files: ");
			response.flushBuffer();
			Framework.initConfig();
			
			out.write("OK\nInitializing unmarshallers: ");
			response.flushBuffer();
			Framework.initUnmarshallers();
			
			out.write("OK\nInitializing response parsers: ");
			response.flushBuffer();
			Framework.initResponseParsers();
			
			out.write("OK\nLoading observers: ");
			response.flushBuffer();
			Framework.initObservers();
			
			out.write("OK\nLoading tasklist: ");
			response.flushBuffer();
			Framework.initTasklists();
			
			out.write("OK\nLoading language files: ");
			response.flushBuffer();
			Framework.initLanguages();
			
			out.write("OK\nInitializing Velocity: ");
			response.flushBuffer();
			Framework.initVelocity();
			out.write("OK");
			out.close();
		}
		else
			response.sendError(403, "You are not allowed to execute this action.");
	}



}
