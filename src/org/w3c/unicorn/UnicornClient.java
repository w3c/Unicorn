package org.w3c.unicorn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.w3c.unicorn.Framework;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.action.Action;
import org.w3c.unicorn.input.DirectInputParameter;
import org.w3c.unicorn.input.URIInputParameter;
import org.w3c.unicorn.input.UploadInputParameter;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.Message;
import org.w3c.unicorn.util.Property;

public class UnicornClient {

	/**
	 * Prints help contents on the standard output.
	 * 
	 */
	public static void print_help() {
		System.out.println("[Usage] UnicornClient ucn_task=TASK ucn_uri=URI ucn_format=FORMAT ucn_output=OUTPUT ...");
		System.out.println("");
		System.out.println("* tasks = one of task in tasklist.xml (eg: markup, css...)");
		System.out.println("* inputType       : uri|file");
		System.out.println("* mimetype        : text/html|text/css|... (required only if inputType='file')");
		System.out.println("* pageToValid     : an uri or a path to a file (depend on inputType)");
		System.out.println("* otherParameters : param1=val1,param2=val2...");
		System.out.println("");
		System.out.println("[Example] UnicornClient markup uri=http://w3.org en xhtml10");
		System.out.println("[Example] UnicornClient calculator uri=http://flyingman.sophia.w3.org/test en text10 x2=on,ptoto=titi");
		System.out.println("[Example] UnicornClient css file=text/css=./style/base.css fr text10 profile=css2,usermedium=screen,warning=2,lang=en");
	}

	/**
	 * Tests Unicorn client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 0 || (args.length == 1 && args[0].equals("help"))) {
			print_help();
			return;
		}

		Framework.init();

		if (!Framework.isUcnInitialized) {
			System.err.println(">> Unicorn is not properly initialized.");
			return;
		}
		UnicornCall aUnicornCall = new UnicornCall();
		
		List<String> outputParams = new ArrayList<String>();
		outputParams.add("output");
		outputParams.add("format");
		outputParams.add("charset");
		outputParams.add("mimetype");
		outputParams.add("lang");
		
		Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
		Map<String, String> specificParameters = new Hashtable<String, String>();
		Map<String, String> outputParameters   = new Hashtable<String, String>();
		ArrayList<Message> messages = new ArrayList<Message>();

		// Retrieve parameter prefixes from unicorn.properties
		String paramPrefix = Property.get("UNICORN_PARAMETER_PREFIX");
		String optParamPrefix = Property.get("UNICORN_PARAMETER_OUTPUT_PREFIX");
		
		outputParameters.put("output", "simple");
		outputParameters.put("format", "xhtml10");
		outputParameters.put("charset", "UTF-8");
		outputParameters.put("mimetype", "text/html");		
		
		
		for(int i=0; i<args.length; i++) {
			String[] param = args[i].split("=");
			
			if (param[0].startsWith(optParamPrefix)) {
				String paramName = param[0].substring(optParamPrefix.length());
				specificParameters.put(paramName, param[1]);
			} else if (param[0].startsWith(paramPrefix)) {
				String paramName = param[0].substring(paramPrefix.length());
				if (paramName.equals("lang")) {
					outputParameters.put(paramName, param[1]);
					aUnicornCall.setLang(param[1] + "," + Property.get("DEFAULT_LANGUAGE"));
				} else if (paramName.equals("task")) {
					String task = Action.getTask(param[1], messages);
					if (!task.equals(param[1])) {
						mapOfStringObject.put("default_task", Framework.mapOfTask.get(Framework.mapOfTask.getDefaultTaskId()));
					}
					aUnicornCall.setTask(task);
				} else if (outputParams.contains(paramName)) {
					outputParameters.put(paramName, param[1]);
				} else if (paramName.equals("uri")) {
					aUnicornCall.setInputParameter(new URIInputParameter(param[1]));
				} else if (paramName.equals("text_mime")) {
					aUnicornCall.addParameter(paramPrefix + "mime", param[1]);
				} else {					
					aUnicornCall.addParameter(paramName, param[1]);
				}
			} else {
				aUnicornCall.addParameter(param[0], param[1]);
			}
		}

//		// parse input type: "uri=http://flyingman.sophia.w3.org/test" or
//		// "file=text/css=./style/base.css"
//		String[] pInput = pageToValid.split("=");
//		if (pInput[0].equals("uri")) {
//			aUnicornCall.setInputParameter(new URIInputParameter(pInput[1]));
//		} else { // direct input
//			try {
//				BufferedReader bfr = new BufferedReader(new FileReader(pInput[2]));
//				String content = "";
//				String line;
//				while ((line = bfr.readLine()) != null) {
//					content = content + line + "\n";
//				}
//				bfr.close();
//
//				// Ajouter mime type dans map of parameter
//				// TODO check if this is necessary (mime-type is added to DirectInputParameter object)
//				/*Map<String, String[]> mapOfParameter = aUnicornCall
//									.getMapOfStringParameter();
//							if (mapOfParameter == null) {
//								mapOfParameter = new LinkedHashMap<String, String[]>();
//								aUnicornCall.setMapOfStringParameter(mapOfParameter);
//							}
//							String[] tmp = { pInput[1] };
//							mapOfParameter.put(Property.get("UNICORN_PARAMETER_PREFIX")
//									+ "mime", tmp);*/
//
//				aUnicornCall.setInputParameter(new DirectInputParameter(content, pInput[1]));
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		long before = System.currentTimeMillis();
		try {
			aUnicornCall.doTask();

			mapOfStringObject.put("unicorncall", aUnicornCall);
			OutputModule aOutputModule = OutputFactory.createOutputModule(outputParameters, specificParameters);
			PrintWriter pw = new PrintWriter(System.out);
			aOutputModule.produceOutput(mapOfStringObject, pw);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long after = System.currentTimeMillis();
		System.out.println("Elapsed time (s): " + (double) (after - before) / 1000);
	}

}
