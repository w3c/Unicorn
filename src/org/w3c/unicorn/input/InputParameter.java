package org.w3c.unicorn.input;

import javax.activation.MimeType;

import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;

public abstract class InputParameter {
	
	protected EnumInputMethod inputMethod;

	protected InputModule inputModule;
	
	protected MimeType mimeType;
	
	protected String documentName;
	
	public abstract void check() throws UnicornException;

	public InputModule getInputModule() {
		return inputModule;
	}

	public void setInputModule(InputModule inputModule) {
		this.inputModule = inputModule;
	}
	
	public MimeType getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	public abstract String getDocumentName();

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public abstract EnumInputMethod getInputMethod();

	public void setInputMethod(EnumInputMethod inputMethod) {
		this.inputMethod = inputMethod;
	}
	
}
