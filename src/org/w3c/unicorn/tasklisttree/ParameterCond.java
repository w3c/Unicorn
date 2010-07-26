// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.unicorn.tasklisttree;

import org.w3c.unicorn.UnicornCall;

public class ParameterCond extends TLTCond {

	@Override
	public boolean check(UnicornCall unicornCall) {
		
		if (!unicornCall.getMapOfStringParameter().containsKey(parameter))
			return !getResult();
		boolean passed = false;
		String[] parameterValues = unicornCall.getMapOfStringParameter().get(parameter);
		for (int i=0; i<parameterValues.length; i++)
			if (parameterValues[i].equals(value))
				passed = true;
		if (getResult())
			return passed;
		else
			return !passed;
	}

}
