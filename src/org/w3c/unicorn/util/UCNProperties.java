// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.unicorn.Framework;

public class UCNProperties extends Properties {
	
	private static final long serialVersionUID = 1L;
	
	public static Log logger = LogFactory.getLog(Framework.class);
	
	private Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z_0-9]*\\}");

	public void parse() {
		for(Object key : this.keySet()) {
			logger.trace("Parsing Property : \"" + key + "\" => \"" + this.getProperty((String) key));
			Matcher matcher = pattern.matcher(this.getProperty((String) key));
			
			if (matcher.find()) {
				matcher.reset();
				while (matcher.find()) {
					String match = matcher.group();
					logger.trace("> Pattern matched with: \"" + match + "\"");
					
					String foundKey = (String) match.subSequence(2, match.length()-1);
					
					if (!this.containsKey(foundKey)) {
						logger.warn("> String \"" + foundKey + "\" is not an existing property.");
					} else {
						String foundProp = this.getProperty(foundKey);
						logger.trace("> Found coresponding property: \"" + foundKey + "\" => \"" + foundProp +"\"");
						
						String subst = this.getProperty((String) key);
						subst = subst.replace(match, foundProp);
						this.put(key, subst);
						matcher = pattern.matcher(this.getProperty((String) key)); 
					}
				}
				logger.trace("> Parsed property: \"" + key + "\" => \"" + this.getProperty((String) key) + "\"");
			} else {
				logger.trace("> No nested property found");
			}
		}
	}

	public Enumeration<Object> keys() {
		final Iterator<Object> i = this.keySet().iterator();
		return new Enumeration<Object>() {
			public boolean hasMoreElements() {
				return i.hasNext();
			}
			public Object nextElement() {
				return i.next();
			}
		};
	}
	
	@Override
	public Set<Object> keySet() {
		return new TreeSet<Object>(super.keySet());
	}

	@Override
	public String toString() {
		String result = "";
		for(Object key : this.keySet()) {
			result += "\n\t"+key+"\t=>\t"+this.getProperty((String) key);
		}
		return result;
	}
}