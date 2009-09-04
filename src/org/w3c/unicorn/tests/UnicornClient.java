package org.w3c.unicorn.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.Framework;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.InitializationFailedException;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.Property;

public class UnicornClient {

	/**
	 * Prints help contents on the standard output.
	 * 
	 */
	public static void print_help() {
		System.out
				.println("[Usage] UnicornClient task inputType=[mimetype=]pageToValid templateLanguage outputTemplate [otherParameters]");
		System.out.println("");
		System.out
				.println("* tasks = one of task in tasklist.xml (eg: markup, css...)");
		System.out.println("* inputType       : uri|file");
		System.out
				.println("* mimetype        : text/html|text/css|... (required only if inputType='file')");
		System.out
				.println("* pageToValid     : an uri or a path to a file (depend on inputType)");
		System.out.println("* otherParameters : param1=val1,param2=val2...");
		System.out.println("");
		System.out
				.println("[Example] UnicornClient markup uri=http://w3.org en xhtml10");
		System.out
				.println("[Example] UnicornClient calculator uri=http://flyingman.sophia.w3.org/test en text10 x2=on,ptoto=titi");
		System.out
				.println("[Example] UnicornClient css file=text/css=./style/base.css fr text10 profile=css2,usermedium=screen,warning=2,lang=en");
	}

	/**
	 * Tests Unicorn client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			print_help();
		} else if (args.length == 1 && args[0].equals("help")) {
			print_help();
		} else {
			// read parameters
			String task = args[0];
			String pageToValid = args[1];
			String language = args[2];
			String outputTemplate = args[3];
			String pParams = "";
			if (args.length > 4) { // this argument is optional
				pParams = args[4];
			}
			
			Framework.init();
			
			if (!Framework.isUcnInitialized) {
				System.err.println(">> Unicorn is not properly initialized.");
				return;
			}
			
			UnicornCall aUnicornCall = new UnicornCall();

			// parse other parameters: "x2=on,toto=titi" to a
			// map<String,String[]>
			if (pParams.length() != 0) {
				Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
				String[] couples = pParams.split(",");
				for (int i = 0; i < couples.length; i++) {
					String[] couple = couples[i].split("=");
					if (couple.length == 2) {
						String[] tmp = { couple[1] };
						mapOfParameter.put(couple[0], tmp);
					} else {
						System.err.println("Error parameter!");
					}
				}
				aUnicornCall.setMapOfStringParameter(mapOfParameter);
			}

			// parse input type: "uri=http://flyingman.sophia.w3.org/test" or
			// "file=text/css=./style/base.css"
			String[] pInput = pageToValid.split("=");
			if (pInput[0].equals("uri")) {
				aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
				aUnicornCall.setDocumentName(pInput[1]);
				aUnicornCall.setInputParameterValue(pInput[1]);
			} else { // direct input
				try {
					aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);

					// read content in the file pInput[2], example:
					// pInput[2]=base.css alors content=".h1{color:#FA0012}";
					BufferedReader bfr = new BufferedReader(new FileReader(pInput[2]));
					String content = "";
					String line;
					while ((line = bfr.readLine()) != null) {
						content = content + line + "\n";
					}
					bfr.close();

					// Ajouter mime type dans map of parameter
					Map<String, String[]> mapOfParameter = aUnicornCall
							.getMapOfStringParameter();
					if (mapOfParameter == null) {
						mapOfParameter = new LinkedHashMap<String, String[]>();
						aUnicornCall.setMapOfStringParameter(mapOfParameter);
					}
					String[] tmp = { pInput[1] };
					mapOfParameter.put(Property.get("UNICORN_PARAMETER_PREFIX")
							+ "mime", tmp);

					aUnicornCall.setInputParameterValue(content);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			aUnicornCall.setTask(task); // task id
			aUnicornCall.setLang(language);

			long before = System.currentTimeMillis();
			try {
				aUnicornCall.doTask();

				Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
				mapOfStringObject.put("unicorncall", aUnicornCall);
				OutputFormater aOutputFormater = OutputFactory
						.createOutputFormater(outputTemplate, // text or xhtml10,
															// see
															// unicorn.properties
								language, "text/html"); // MIME Type
				OutputModule aOutputModule = OutputFactory
						.createOutputModule("simple");
				PrintWriter pw = new PrintWriter(System.out);
				aOutputModule.produceOutput(aOutputFormater, mapOfStringObject,
						null, pw);
				pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long after = System.currentTimeMillis();
			System.out.println("Elapsed time (s): " + (double) (after - before)
					/ 1000);
		}
	}
}
