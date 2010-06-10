package org.w3c.unicorn.util;

import java.io.StringWriter;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.unicorn.Framework;
import com.mindprod.compactor.*;

public class Templates {
	
	public static void write(String templateName, VelocityContext context, Writer writer) {
		try {
			StringWriter sw = new StringWriter();
			if (Property.get("USE_HTML_COMPACTOR").equalsIgnoreCase("true")) {
				Framework.getVelocityEngine().mergeTemplate(templateName, "UTF-8", context, sw);
				Compactor compactor = new Compactor();
				writer.append(compactor.compactString(sw.toString(), templateName));
			} else {
				Framework.getVelocityEngine().mergeTemplate(templateName, "UTF-8", context, writer);
			}
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
