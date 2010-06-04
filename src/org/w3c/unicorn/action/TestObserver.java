package org.w3c.unicorn.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TestObserver
 * 
 * @author Thomas GAMBET
 */
public class TestObserver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TestObserver() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getParameter("uri");
		response.sendRedirect(uri);
	}

}
