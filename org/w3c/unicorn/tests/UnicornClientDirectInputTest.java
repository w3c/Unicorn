package org.w3c.unicorn.tests;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.Property;

public class UnicornClientDirectInputTest {

	public static void main(String[] args) {

		/*
		// lang par defaut
		Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
		String[] tmp = {"en"};
		mapOfParameter.put("ucn_lang", tmp); 
		aUnicornCall.setMapOfStringParameter(mapOfParameter);
		*/
		
		UnicornCall aUnicornCall = new UnicornCall();
		aUnicornCall.setTask("css"); //task id
		aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);
		aUnicornCall.setLang("en");
		
		aUnicornCall.setDocumentName("D:/stageW3C/unicorn/style/base_result.css");
		
		Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
		String[] tmp = {"text/css"};
		mapOfParameter.put("ucn_mime", tmp); 
		aUnicornCall.setMapOfStringParameter(mapOfParameter);

		aUnicornCall.setInputParameterValue("p#msie { /* msie-bug note for text/plain */ float: right; border: 1px solid black; background: white;}");
		
		//aUnicornCall.setInputParameterValue("2+3");

		
		//if (isAcceptDirectInput("calculator"))
		
		try {
			
			aUnicornCall.doTask();
			
			Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
			mapOfStringObject.put("unicorncall", aUnicornCall);
			OutputFormater aOutputFormater = OutputFactory.getOutputFormater(
					"text10", // le template --> text ou xhtml10, see unicorn.properties
					"en",   // la langue
					"text/plain"); // MIME Type
			OutputModule aOutputModule = OutputFactory.getOutputModule("simple");
			PrintWriter pw = new PrintWriter(System.out);
			aOutputModule.produceOutput(aOutputFormater, mapOfStringObject, null, pw);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
}
