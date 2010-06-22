package org.w3c.unicorn.tasklisttree;

import org.w3c.unicorn.UnicornCall;

public class MimetypeCond extends TLTCond {

	@Override
	public boolean check(UnicornCall unicornCall) {
		
		return value.equals(unicornCall.getInputParameter().getMimeType().toString());
		
	}



}
