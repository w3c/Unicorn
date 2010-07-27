// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.w3c.unicorn.Framework;

public class UnicornAuthenticator extends Authenticator {

	String username;
	String password;
	
	public UnicornAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {

		try {
			if (username == null && password == null) {
				/*String result = JOptionPane.showInputDialog("Enter 'username,password' for your SMTP Server. This is asked only once. You can also set the username and password in mail.properties");
			    StringTokenizer st = new StringTokenizer(result, ",");
			    username = st.nextToken();
			    password = st.nextToken();*/
				throw new Exception();
			}
		} catch (Exception e) { //catch (HeadlessException e) {
			Framework.logger.error("Before sending mails you must specify a username and a password in mail.properties. If your SMTP server does not need authentication, set mail.smtp.auth to false.");
		}

	    return new PasswordAuthentication(username, password);
	}
	
}
