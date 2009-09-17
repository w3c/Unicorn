package org.w3c.unicorn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

//import sun.net.www.protocol.http.HttpURLConnection;



public class Test {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String s = "${TEST} sdf zer ${TEST2}";
		System.out.println(s.matches("\\$\\{[a-zA-Z_0-9]*\\}"));
		
		Matcher matcher = Pattern.compile("\\$\\{[a-zA-Z_0-9]*\\}").matcher(s);
		
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		
		System.out.println(matcher.lookingAt());
		System.out.println(matcher.group());
		System.out.println(matcher.group(0));
		System.out.println(matcher.group(1));
		System.out.println(matcher.lookingAt());
		System.out.println(matcher.group());
	     //replaceAll(repl)*/
		
		URL docUrl = null;
		try {
			docUrl = new URL("http://www.w3.org:9788");
			//docUrl = new URL("http://qa-dev.w3.org/app");
			//docUrl = new URL("http://www.w3.org/Team");
			//docUrl = new URL("http://snowball.w3.org/unicorn/observe?ucn_uri=beta.w3.org&ucn_task=conformance");
			//System.out.println(docUrl.getContent().toString());
			//System.out.println(docUrl2.getContent().toString().length());
			URLConnection con = docUrl.openConnection();
			System.out.println(con.getConnectTimeout());
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			con.getContent();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) { 
			e.printStackTrace();
		} catch (SSLException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Read timed out"))
				System.out.println("a");
			if (e.getMessage().contains("connect timed out"))
				System.out.println("b");
		} catch (IOException e) {
			try {
				int responseCode;
				if (docUrl != null) {
					responseCode = ((HttpURLConnection) docUrl.openConnection()).getResponseCode();
					switch (responseCode) {
					case HttpURLConnection.HTTP_UNAUTHORIZED:
						System.out.println("HTTP_UNAUTHORIZED");
						break;
					}
				}
			} catch (Exception e2) {
				System.out.println("e2");
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
