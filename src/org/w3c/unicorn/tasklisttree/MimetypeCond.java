// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

import org.w3c.unicorn.UnicornCall;

public class MimetypeCond extends TLTCond {

	@Override
	public boolean check(UnicornCall unicornCall) {
		boolean b = value.equals(unicornCall.getInputParameter().getMimeType().toString());
		if (getResult())
			return b;
		else
			return !b;
	}



}
