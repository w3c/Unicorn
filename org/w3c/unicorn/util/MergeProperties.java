package org.w3c.unicorn.util;


import java.io.File; 
import java.io.IOException; 
import java.util.Iterator; 
import java.util.Properties; 
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;


/** 
*  MergeProperties provides functionality to merge two separate Properties object
*  
*  @author Thomas GAMBET
*/ 
public class MergeProperties
{ 
	public static final Log logger = LogFactory.getLog("org.w3c.unicorn.until");
  
	public static Properties getMergeProperties(File defaultPropFile, File sourcePropFile) { 
		Properties defaultProps = new Properties();
		Properties sourceProps = new Properties();
		try { 
			defaultProps.load(defaultPropFile.toURL().openStream());
			sourceProps.load(sourcePropFile.toURL().openStream());
		} catch (IOException e) {
			logger.error("Merge Properties Error : " + e.getMessage(), e);
		}
		
		Properties propMerge = new Properties();
		
		Set<Object> keys = defaultProps.keySet();
		Iterator<Object> itr = keys.iterator();
		String key;
		
		while (itr.hasNext()) {
			key = itr.next().toString();
			if (sourceProps.containsKey(key))
				propMerge.put(key, sourceProps.get(key));
			else
				propMerge.put(key, defaultProps.get(key));
		}
		
		return propMerge;
	}
	
	public static void loadInVelocityContext(Properties props, VelocityContext context) {
	    Set<Object> keys = props.keySet();
	    Iterator<Object> itr = keys.iterator();
	    String key;
	    while (itr.hasNext()) {
			key = itr.next().toString();
			context.put(key, props.get(key));
	    }
	}
} 
