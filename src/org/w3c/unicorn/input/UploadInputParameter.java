package org.w3c.unicorn.input;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.fileupload.FileItem;
import org.w3c.unicorn.contract.EnumInputMethod;

import org.w3c.unicorn.exceptions.UnicornException;

public class UploadInputParameter extends InputParameter {
	
	private FileItem file;
	
	public UploadInputParameter(FileItem file) {
		this.file = file;
	}
	
	@Override
	public void check() throws UnicornException {
		if (file.getName() == null || file.getName().equals("")) {
			//throw new NoDocumentException("No document provided");
			throw new UnicornException("");
		}
		if (file.getSize() == 0) {
			//throw new EmptyDocumentException("Empty document provided");
			throw new UnicornException("");
		}
		String sMimeType = file.getContentType();
		if (null == sMimeType || "".equals(sMimeType)) {
			//throw new NoMimeTypeException("Mimetype not found");
			throw new UnicornException("");
		}
		try {
			mimeType = new MimeType(sMimeType);
		} catch (MimeTypeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputModule = new FileItemInputModule(mimeType, file);
	}

	@Override
	public String getDocumentName() {
		return file.getName();
	}

	@Override
	public EnumInputMethod getInputMethod() {
		return EnumInputMethod.UPLOAD;
	}

}
