package org.w3c.unicorn.tests;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;

public class UnicornClient {

	public static void main(String[] args) {
		String task = "multithreading";
		String pageToValid = "http://flyingman.sophia.w3.org/test.txt";
		String language = "en";
		String outputTemplate = "xhtml10";

		/*
		// read parameters
		if (args.length<4) {
			System.out.println("[Usage] UnicornClient task pageToValid language outputTemplate");
			System.out.println("[Example] UnicornClient markup http://w3.org en xhtml10");
			System.out.println("[Example] UnicornClient css http://w3.org en text10");
		}
		String task = args[0];
		String pageToValid = args[1];
		String language = args[2];
		String outputTemplate = args[3];
		*/
		
		
		long before = System.currentTimeMillis() ;
		
		UnicornCall aUnicornCall = new UnicornCall();
		aUnicornCall.setTask(task); //task id
		aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
		aUnicornCall.setDocumentName(pageToValid);
		aUnicornCall.setInputParameterValue(pageToValid);
		aUnicornCall.setLang(language);

		/*
		// lang par defaut
		Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
		String[] tmp = {"en"};
		mapOfParameter.put("ucn_lang", tmp); 
		aUnicornCall.setMapOfStringParameter(mapOfParameter);
		*/
		
		try {
			aUnicornCall.doTask();
			
			Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
			mapOfStringObject.put("unicorncall", aUnicornCall);
			OutputFormater aOutputFormater = OutputFactory.getOutputFormater(
					outputTemplate, // le template --> text ou xhtml10, see unicorn.properties
					language,   // la langue
					"text/plain"); // MIME Type
			OutputModule aOutputModule = OutputFactory.getOutputModule("simple");
			PrintWriter pw = new PrintWriter(System.out);
			aOutputModule.produceOutput(aOutputFormater, mapOfStringObject, null, pw);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long after = System.currentTimeMillis() ;
		System.out.println("Elapsed time (s): "+(double)(after - before)/1000);
	}
}
