// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.input;

import java.util.ArrayList;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import org.apache.commons.fileupload.FileItem;
import org.w3c.unicorn.contract.EnumInputMethod;
import org.w3c.unicorn.exceptions.UnicornException;
import org.w3c.unicorn.util.Message;

public class UploadInputParameter extends InputParameter {
	
	private FileItem file;
	
	public UploadInputParameter(FileItem file) {
		this.file = file;
	}
	
	@Override
	public void check(ArrayList<Message> messages) throws UnicornException {
		if (file.getName() == null || file.getName().equals("")) {
			throw new UnicornException(Message.ERROR, "$message_no_uploaded_file", null);
		}
		if (file.getSize() == 0) {
			throw new UnicornException(Message.ERROR, "$message_empty_uploaded_file", null);
		}
		String sMimeType = file.getContentType();
		if (null == sMimeType || "".equals(sMimeType)) {
			// TODO Is there another solution here to find the mime-type ?
			throw new UnicornException(Message.ERROR, "$message_not_found_mime_type", null);
		}
		try {
			mimeType = new MimeType(sMimeType);
		} catch (MimeTypeParseException e) {
			throw new UnicornException(Message.ERROR, "$message_invalid_mime_type", null);
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
	
	@Override
	public void dispose() {
		super.dispose();
		if (null != this.file) {
			this.file.delete();
			this.file = null;
		}
	}

}
