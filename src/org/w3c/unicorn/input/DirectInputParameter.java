package org.w3c.unicorn.input;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Property;

public class DirectInputParameter extends InputParameter {
	
	private String document;
	
	private String sMimeType;
	
	public DirectInputParameter(String document, String sMimeType) {
		this.document = document;
		this.sMimeType = sMimeType;
	}

	@Override
	public void check() throws UnicornException {
		if (null == sMimeType || "".equals(sMimeType)) {
			//throw new NoMimeTypeException("Mimetype not found.");
			throw new UnicornException("");
		}
		try {
			mimeType = new MimeType(sMimeType);
		} catch (MimeTypeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputModule = new DirectInputModule(mimeType, document);
	}

	@Override
	public String getDocumentName() {
		//TODO
		return "Direct Input Document";
	}

	@Override
	public EnumInputMethod getInputMethod() {
		return EnumInputMethod.DIRECT;
	}

}
