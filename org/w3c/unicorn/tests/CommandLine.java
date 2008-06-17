// $Id: CommandLine.java,v 1.3 2008-06-17 13:45:31 jbarouh Exp $
// Author: Damien LEROY.
// (c) COPYRIGHT MIT, ERCIM ant Keio, 2006.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.EnumOutputModule;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputModule;

/**
 * Class to call the framework by command line.
 * @author Damien LEROY
 */
public class CommandLine {

	private static final Log logger = LogFactory.getLog("org.w3c.unicorn.tests");

	private static String sTaskID = "all";

	private static Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();

	private static EnumInputMethod aEnumInputMethod = null;

	private static String sEnumInputMethodValue = null;

	private static OutputModule aOutputModule = null; 

	/**
	 * Launches the Unicorn Framework.
	 * @param args
	 * @throws Exception 
	 */
	public static void main (String[] args) throws Exception {
		CommandLine.logger.trace("Unicorn Framework Begin.");

		CommandLine.logger.info("Read command-line arguments.");
		for (int i = 0; i < args.length; i++) {
			if (CommandLine.logger.isDebugEnabled()) {
				CommandLine.logger.debug("Argument : "+args[i]+".");
			}
			if ("-task".equals(args[i])) {
				i++;
				CommandLine.sTaskID = args[i];
				if (CommandLine.logger.isDebugEnabled()) {
					CommandLine.logger.debug("Task : "+CommandLine.sTaskID+".");
				}
			} else if ("-inputmethod".equals(args[i])) {
				i++;
				String[] tString = args[i].split("=");
				CommandLine.aEnumInputMethod = EnumInputMethod.fromValue(tString[0]);
				if (2 <= tString.length) {
					CommandLine.sEnumInputMethodValue = tString[1];
				}
				if (CommandLine.logger.isDebugEnabled()) {
					CommandLine.logger.debug(
							"Input method : " +
							CommandLine.aEnumInputMethod.toString() +
							", value : " +
							CommandLine.sEnumInputMethodValue +".");
				}
			} else if ("-outputmethod".equals(args[i])) {
				i++;
				String sOutputMethod = args[i];
				final EnumOutputModule aEnumOutputModule = EnumOutputModule.fromValue(sOutputMethod);
				if (null == aEnumOutputModule) {
					CommandLine.logger.error("Unknow output method : " + sOutputMethod + ".");
					return;
				}
				CommandLine.aOutputModule = OutputFactory.getOutputModule(aEnumOutputModule);
			} else if (args[i].contains("=")) {
				String[] tString = args[i].split("=");
				String[] val = {tString[1]};
				CommandLine.mapOfParameter.put(tString[0], val);
			}
		}

		if (null == CommandLine.aEnumInputMethod) {
			CommandLine.logger.error("No input method specified.");
			return;
		}

		if (null == CommandLine.aOutputModule) {
			CommandLine.logger.info("No output method specified use SimpleOutputModule by default.");
			CommandLine.aOutputModule = OutputFactory.getOutputModule(EnumOutputModule.SIMPLE);
		}

		if (EnumInputMethod.DIRECT.equals(CommandLine.aEnumInputMethod)) {
			// read on standard input and add to Main.eimValue
			String sEnumInputMethodValue = "";
			for (int i = System.in.read(); -1 != i; i = System.in.read()) {
				sEnumInputMethodValue += (char) i;
			}
			if (CommandLine.logger.isDebugEnabled()) {
				CommandLine.logger.debug("Direct Input :\n"+sEnumInputMethodValue);
			}
			CommandLine.sEnumInputMethodValue = sEnumInputMethodValue;
		}

		CommandLine.logger.info("Initialize framework.");
		UnicornCall aUnicornCall = new UnicornCall();
		aUnicornCall.setTask(CommandLine.sTaskID);
		aUnicornCall.setEnumInputMethod(CommandLine.aEnumInputMethod);
		//CommandLine.sEnumInputMethodValue
		aUnicornCall.setMapOfStringParameter(CommandLine.mapOfParameter);
		if (CommandLine.logger.isDebugEnabled()) {
			CommandLine.logger.debug("UnicornCall : "+aUnicornCall+".");
		}

		CommandLine.logger.info("Process request.");
		//Main.mapOfParameter.put("warning", "2");
		aUnicornCall.doTask();

		CommandLine.logger.trace("Unicorn Framework End.");
	}

}
