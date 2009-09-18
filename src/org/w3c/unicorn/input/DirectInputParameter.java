package org.w3c.unicorn.input;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Message;

public class DirectInputParameter extends InputParameter {
	
	private String document;
	
	private String sMimeType;
	
	public DirectInputParameter(String document, String sMimeType) {
		this.document = document;
		this.sMimeType = sMimeType;
	}

	@Override
	public void check() throws UnicornException {
		if (document == null || document.equals(""))
			throw new UnicornException(Message.Level.ERROR, "$message_empty_direct_input", null);
		if (sMimeType == null || sMimeType.equals(""))
			throw new UnicornException(Message.Level.ERROR, "$message_missing_mime_type", null);
		try {
			mimeType = new MimeType(sMimeType);
		} catch (MimeTypeParseException e) {
			throw new UnicornException(Message.Level.ERROR, "$message_invalid_mime_type", null);
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
