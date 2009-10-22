package org.w3c.unicorn.response.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlOptions;
import org.w3.unicorn.observationresponse.ErrorDocument.Error;
import org.w3.unicorn.observationresponse.InfoDocument.Info;
import org.w3.unicorn.observationresponse.LongmessageDocument.Longmessage;
import org.w3.unicorn.observationresponse.WarningDocument.Warning;
import org.w3c.unicorn.response.Context;
import org.w3c.unicorn.response.Message;

public class OldMessageXBeans implements Message {

	private int type;
	private int level;
	private String uri;
	private String title;
	private String description;
	private List<Context> contexts = new ArrayList<Context>();
	
	public OldMessageXBeans(Error error) {
		type = ERROR;
		level = error.getLevel();
		if (error.getMessageList() != null)
			title = error.getMessageList().get(0);
		else
			title = "";
		description = buildDescription(error.getLongmessageList());
		Integer line;
		Integer column;
		if (error.isSetLine()) {
			try {
				line = error.getLine().intValue();
			} catch (Exception e) {
				line = null;
			}
		}
		else
			line = null;
		if (error.isSetColumn())
			try {
				column = error.getColumn().intValue();
			} catch (Exception e) {
				column = null;
			}
		else
			column = null;
		contexts.add((Context) new OldContextXBeans(error.getContext(), line, column));
	}

	public OldMessageXBeans(Info info) {
		type = INFO;
		level = 0;
		if (info.getMessageList() != null && info.getMessageList().size() > 0)
			title = info.getMessageList().get(0);
		else
			title = "";
		description = buildDescription(info.getLongmessageList());
		Integer line;
		Integer column;
		if (info.isSetLine()) {
			try {
				line = info.getLine().intValue();
			} catch (Exception e) {
				line = null;
			}
		}
		else
			line = null;
		if (info.isSetColumn())
			try {
				column = info.getColumn().intValue();
			} catch (Exception e) {
				column = null;
			}
		else
			column = null;
		contexts.add((Context) new OldContextXBeans(info.getContext(), line, column));
	}

	public OldMessageXBeans(Warning warning) {
		type = WARNING;
		level = warning.getLevel();
		if (warning.getMessageList() != null)
			title = warning.getMessageList().get(0);
		else
			title = "";
		description = buildDescription(warning.getLongmessageList());
		Integer line;
		Integer column;
		if (warning.isSetLine()) {
			try {
				line = warning.getLine().intValue();
			} catch (Exception e) {
				line = null;
			}
		}
		else
			line = null;
		if (warning.isSetColumn())
			try {
				column = warning.getColumn().intValue();
			} catch (Exception e) {
				column = null;
			}
		else
			column = null;
		contexts.add((Context) new OldContextXBeans(warning.getContext(), line, column));
	}

	private String buildDescription(List<Longmessage> longMessages) {
		StringBuilder descript = new StringBuilder();
		for (Longmessage mess : longMessages) {
			descript.append(mess.xmlText(new XmlOptions().setUseDefaultNamespace()
					.setSavePrettyPrint())
					.replaceAll("<xml-fragment[^>]*>", "<div>")
					.replaceAll("</xml-fragment[^>]*>", "</div>")
					.replaceAll("xmlns=\".*\"", ""));
		}
		return descript.toString();
	}
	
	public List<Context> getContexts() {
		return contexts;
	}

	public String getDescription() {
		return description;
	}

	public String getGroupName() {
		return null;
	}

	public int getLevel() {
		return level;
	}

	public String getTitle() {
		if (title != null)
			return title.trim();
		return null;
	}

	public int getType() {
		return type;
	}

	public String getURI() {
		return uri;
	}
	
	public void setURI(String uri) {
		this.uri = uri;
	}

}
