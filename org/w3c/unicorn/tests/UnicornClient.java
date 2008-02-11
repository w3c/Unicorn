package org.w3c.unicorn.tests;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;

public class UnicornClient {

	public static void print_help() {
		System.out.println("[Usage] UnicornClient task inputType=[mimetype=]pageToValid templateLanguage outputTemplate [otherParameters]");
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
	
	public static void main(String[] args) {
		UnicornCall aUnicornCall = new UnicornCall();
		
		/*
		String task = "css";
		String pageToValid = "file=text/css=D:/stageW3C/unicorn/style/base_result.css";
		String language = "en";
		String outputTemplate = "text10";
		String pParams="";
		*/
		
		String task = "calculator";
		String pageToValid = "uri=http://flyingman.sophia.w3.org/test";
		String language = "fr";
		String outputTemplate = "text10";
		String pParams = "x2=on,ucn_lang=vn";
		
		/*
		// read parameters
		String task = args[0];
		String pageToValid = args[1];
		String language = args[2];
		String outputTemplate = args[3];
		String pParams = ""; 
		if (args.length>4) { //this argument is optional
			pParams = args[4]; 
		}
		*/

		//parse other parameters: "x2=on,toto=titi" to a map<String,String[]>  
		if (pParams.length()!=0) {
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
		}

		//parse input type: "uri=http://flyingman.sophia.w3.org/test" or "file=text/css=./style/base.css"
		String[] pInput = pageToValid.split("=");
		if (pInput[0].equals("uri")) {
			aUnicornCall.setEnumInputMethod(EnumInputMethod.URI);
			aUnicornCall.setDocumentName(pInput[1]);
			aUnicornCall.setInputParameterValue(pInput[1]);
		} else {
			aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);
			File f = new File(pInput[2]);
			//TODO: read content in the file couple[2]
			String content=".h1{color:#FA0012}";
			
			//TODO: ajouter mime type dans map of parameter
	
			Map<String, String[]> mapOfParameter = aUnicornCall.getMapOfStringParameter();
			if (mapOfParameter==null) {
				mapOfParameter = new LinkedHashMap<String, String[]>();
				aUnicornCall.setMapOfStringParameter(mapOfParameter);
			}
			String[] tmp = {pInput[1]};
			mapOfParameter.put("ucn_mime", tmp);
			
			aUnicornCall.setInputParameterValue(content);
		}
		
		aUnicornCall.setTask(task); //task id
		aUnicornCall.setLang(language);

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
