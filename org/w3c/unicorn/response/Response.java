package org.w3c.unicorn.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

public class Response {
    protected String uri;
    protected String checkedby;
    protected String version;
    protected XMLGregorianCalendar date;
    protected Boolean passed;
    /**
     * Result aResult = mapOfLangURIResult.get("fr").get("http://w3.org/home.css")
     */
    protected Map<String, Map<String, Result>> mapOfLangURIResult = new LinkedHashMap<String, Map<String,Result>>();
    
    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the checkedby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckedby() {
        return checkedby;
    }

    /**
     * Sets the value of the checkedby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckedby(String value) {
        this.checkedby = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the passed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPassed() {
        return passed;
    }

    /**
     * Sets the value of the passed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPassed(Boolean value) {
        this.passed = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public List<Result> getResultsList() {
    	List<Result> resultList = new ArrayList<Result>();
    	//iterate all language
    	for (Map<String, Result> aMapOfURIResult : mapOfLangURIResult.values()) {
    		//iterate all URI
    		for (Result aResult : aMapOfURIResult.values()) {
    			resultList.add(aResult);
    		}
    	}
    	return resultList;
    }
    
    public Result getResult(String lang, String uri) {
    	if (mapOfLangURIResult.get(lang)==null)
    		mapOfLangURIResult.put(lang, new LinkedHashMap<String, Result>());
    	if (mapOfLangURIResult.get(lang).get(uri) == null)
    		mapOfLangURIResult.get(lang).put(uri, new Result(lang, uri));
    	return mapOfLangURIResult.get(lang).get(uri);
    }

    /**
	 * add a Result r to the map, if theres is already a result which have the
	 * same uri and the same language, we will append r to this result otherwise
	 * we'll simply add r to the list
	 */
    public void addResult(Result value) {
    	//search in the list a result which have the same uri and the same language
    	Result currentRes = this.getResult(value.lang, value.uri);
    	if (currentRes != null) {
    		 currentRes.warnings.addAll(value.warnings);
    		 currentRes.errors.addAll(value.errors);
    		 currentRes.infos.addAll(value.infos);
    	}
    	else {
    		if (mapOfLangURIResult.get(value.lang)==null)
        		mapOfLangURIResult.put(value.lang, new LinkedHashMap<String, Result>());
    		mapOfLangURIResult.get(value.lang).put(value.uri, value);
    	}
    }

    List<Error> getErrorList() {
    	List<Error> xList = new ArrayList<Error>();
    	//iterate all language
    	for (Map<String, Result> aMapOfURIResult : mapOfLangURIResult.values()) {
    		//iterate all URI
    		for (Result aResult : aMapOfURIResult.values()) {
    			xList.addAll(aResult.errors);
    		}
    	}
    	return xList;
    }
    
    List<Warning> getWarningList() {
    	List<Warning> xList = new ArrayList<Warning>();
    	//iterate all language
    	for (Map<String, Result> aMapOfURIResult : mapOfLangURIResult.values()) {
    		//iterate all URI
    		for (Result aResult : aMapOfURIResult.values()) {
    			xList.addAll(aResult.warnings);
    		}
    	}
    	return xList;
    }
    
    List<Info> getAllInfo() {
    	List<Info> xList = new ArrayList<Info>();
    	//iterate all language
    	for (Map<String, Result> aMapOfURIResult : mapOfLangURIResult.values()) {
    		//iterate all URI
    		for (Result aResult : aMapOfURIResult.values()) {
    			xList.addAll(aResult.infos);
    		}
    	}
    	return xList;
    }
}
