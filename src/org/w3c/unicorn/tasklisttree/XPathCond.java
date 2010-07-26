// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

import org.w3c.unicorn.UnicornCall;
import org.w3c.unicorn.response.Response;

public class XPathCond extends TLTCond {
	
	@Override
	public boolean check(UnicornCall unicornCall) {
		Response res = unicornCall.getResponses().get(observer.getID());
		// Testing if there is a matching response in the map and if it is passed
		if (res != null) {
			if (getResult())
				return res.evaluateXPath(value);
			else
				return !res.evaluateXPath(value);
		}
		
		return false;
	}

}
