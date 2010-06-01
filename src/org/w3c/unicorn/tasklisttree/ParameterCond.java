package org.w3c.unicorn.tasklisttree;

import org.w3c.unicorn.UnicornCall;

public class ParameterCond extends TLTCond {

	@Override
	public boolean check(UnicornCall unicornCall) {
		
		if (!unicornCall.getMapOfStringParameter().containsKey(parameter))
			return false;
		
		boolean passed = false;
		String[] parameterValues = unicornCall.getMapOfStringParameter().get(parameter);
		for (int i=0; i<parameterValues.length; i++)
			if (parameterValues[i].equals(value))
				passed = true;
		
		return passed;
	}

}
