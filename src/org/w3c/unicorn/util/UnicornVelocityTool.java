// Author: Thomas Gambet
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.util;

public class UnicornVelocityTool {

	public boolean isTemporaryURL(String url) {
		if (url == null)
			return false;
		if (url.startsWith(Property.get("UNICORN_URL")))
			return true;
		return false;
	}
}
