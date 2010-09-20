// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.util.ArrayList;

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
	public void check(ArrayList<Message> messages) throws UnicornException {
		if (document == null || document.equals(""))
			throw new UnicornException(Message.ERROR, "$message_empty_direct_input");
		if (sMimeType == null || sMimeType.equals(""))
			throw new UnicornException(Message.ERROR, "$message_missing_mime_type");
		try {
			mimeType = new MimeType(sMimeType);
		} catch (MimeTypeParseException e) {
			throw new UnicornException(Message.ERROR, "$message_invalid_mime_type", null, sMimeType);
		}
		inputModule = new DirectInputModule(mimeType, document);
	}

	@Override
	public String getDocumentName() {
		return "$submitted_file";
	}

	@Override
	public EnumInputMethod getInputMethod() {
		return EnumInputMethod.DIRECT;
	}

}
