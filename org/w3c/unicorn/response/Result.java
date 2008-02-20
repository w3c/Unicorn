package org.w3c.unicorn.response;

import java.util.ArrayList;
import java.util.List;

public class Result {
	protected String uri;
	protected String lang;
	protected List<Error> errors = new ArrayList<Error>();
	protected List<Warning> warnings = new ArrayList<Warning>();
	protected List<Info> infos = new ArrayList<Info>();
	
	public Result(String lang, String uri) {
		super();
		this.uri = uri;
		this.lang = lang;
	}
	public String getUri() {
		return uri;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public List<Warning> getWarnings() {
		return warnings;
	}
	public List<Info> getInfos() {
		return infos;
	}
	public String getLang() {
		return lang;
	}
}
