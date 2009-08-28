package org.w3c.unicorn.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.language.Language;
import org.w3c.unicorn.util.Property;

/**
 * Servlet implementation class TestAction
 */
public class TestAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private VelocityContext velocityContext;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		for (String key : Framework.mapOfTask.keySet()) {
			out.println(Framework.mapOfTask.get(key));
			
			
		}
		Enumeration en = req.getHeaderNames();
		while(en.hasMoreElements()) {
			out.println(en.nextElement());
		}
		
		/*resp.setContentType("text/html; charset=UTF-8");
		
		// Language negotiation
		
		velocityContext = new VelocityContext();
		
		velocityContext.put("task", Framework.mapOfTask.get("conformance"));
		
		Framework.getTemplate("parameters").merge(velocityContext, resp.getWriter());
		resp.getWriter().close();*/
		
	}

}
