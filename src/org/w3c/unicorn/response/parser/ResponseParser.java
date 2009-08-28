package org.w3c.unicorn.response.parser;

import org.w3c.unicorn.response.Response;

public interface ResponseParser {
	public Response parse(String r);
}
