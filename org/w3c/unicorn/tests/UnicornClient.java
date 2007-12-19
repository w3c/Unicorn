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
		UnicornCall aUnicornCall = new UnicornCall();
		/*
		String task = "css";
		String pageToValid = "http://w3.org";
		String language = "en";
		String outputTemplate = "text10";
		String pParams="";
		*/
		/*
		String task = "calculator";
		String pageToValid = "http://flyingman.sophia.w3.org/test";
		String language = "fr";
		String outputTemplate = "text10";
		String pParams = "x2=on";
		*/
		
		// read parameters
		if (args.length<4) {
			System.out.println("[Usage] UnicornClient task pageToValid templateLanguage outputTemplate [otherParameters]");
			System.out.println("[Example] UnicornClient calculator http://flyingman.sophia.w3.org/test en text10 x2=on,ptoto=titi");
			System.out.println("[Example] UnicornClient markup http://w3.org en xhtml10");
			System.out.println("[Example] UnicornClient markup http://w3.org en xhtml10");
			System.out.println("[Example] UnicornClient css http://w3.org en text10");
		}
		String task = args[0];
		String pageToValid = args[1];
		String language = args[2];
		String outputTemplate = args[3];
		String pParams = ""; //pParam = "x2=on,toto=tata"
		if (args.length>4) {
			pParams = args[4];
		}
		
		// simple parser des paramètres
		if (pParams!=null && !pParams.isEmpty()) {
			Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
			String[] couples = pParams.split(",");
			for (int i=0; i<couples.length; i++) {
				String[] couple = couples[i].split("=");
				if (couple.length==2) {
					String[] tmp = {couple[1]}; 
					mapOfParameter.put(couple[0], tmp);
				}
				else 
					System.err.println("Error parameter!");
			}
			aUnicornCall.setMapOfStringParameter(mapOfParameter);
	
			aUnicornCall.setTask(task); //task id
			aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
			aUnicornCall.setDocumentName(pageToValid);
			aUnicornCall.setInputParameterValue(pageToValid);
			aUnicornCall.setLang(language);
		}

		/*
		// lang par defaut
		Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
		String[] tmp = {"en"};
		mapOfParameter.put("ucn_lang", tmp); 
		aUnicornCall.setMapOfStringParameter(mapOfParameter);
		*/
		
		
		long before = System.currentTimeMillis() ;
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
