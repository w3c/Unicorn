package org.w3c.unicorn.tests;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.output.OutputFactory;
import org.w3c.unicorn.output.OutputFormater;
import org.w3c.unicorn.output.OutputModule;
import org.w3c.unicorn.util.Property;

public class UnicornClientDirectInputTest {

	public static void main(String[] args) {

		UnicornCall aUnicornCall = new UnicornCall();
		aUnicornCall.setTask("css"); // task id
		aUnicornCall.setEnumInputMethod(EnumInputMethod.DIRECT);
		aUnicornCall.setLang("en");

		aUnicornCall
				.setDocumentName("D:/stageW3C/unicorn/style/base_result.css");

		Map<String, String[]> mapOfParameter = new LinkedHashMap<String, String[]>();
		String[] tmp = { "text/css" };
		mapOfParameter.put(Property.get("UNICORN_PARAMETER_PREFIX") + "mime",
				tmp);
		aUnicornCall.setMapOfStringParameter(mapOfParameter);

		aUnicornCall
				.setInputParameterValue("p#msie { /* msie-bug note for text/plain */ float: right; border: 1px solid black; background: white;}");

		try {

			aUnicornCall.doTask();

			Map<String, Object> mapOfStringObject = new LinkedHashMap<String, Object>();
			mapOfStringObject.put("unicorncall", aUnicornCall);
			OutputFormater aOutputFormater = OutputFactory.getOutputFormater(
					"text10", // le template --> text ou xhtml10, see
								// unicorn.properties
					"en", // la langue
					"text/plain"); // MIME Type
			OutputModule aOutputModule = OutputFactory
					.getOutputModule("simple");
			PrintWriter pw = new PrintWriter(System.out);
			aOutputModule.produceOutput(aOutputFormater, mapOfStringObject,
					null, pw);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
