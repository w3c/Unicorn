package org.w3c.unicorn.response;

import java.util.ArrayList;
import java.util.List;

public class Result {
	protected String uri;
	protected String lang;
	protected List<Error> errors = new ArrayList<Error>();
	protected List<Warning> warnings = new ArrayList<Warning>();
	protected List<Info> infos = new ArrayList<Info>();
	
	/**
	 * Constructor for Result.
	 * @param lang The language to consider.
	 * @param uri The URI to consider.
	 */
	public Result(String lang, String uri) {
		super();
		this.uri = uri;
		this.lang = lang;
	}
	
	/** 
	 * 
	 * @return The URI of the result.
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * 
	 * @return The list of errors.
	 */
	public List<Error> getErrors() {
		return errors;
	}
	
	/**
	 * 
	 * @return The list of warnings.
	 */
	public List<Warning> getWarnings() {
		return warnings;
	}
	
	/**
	 * 
	 * @return The list of infos.
	 */
	public List<Info> getInfos() {
		return infos;
	}
	
	/**
	 * 
	 * @return The language of the result.
	 */
	public String getLang() {
		return lang;
	}
}
