package org.w3c.unicorn.response.parser;

import java.io.InputStream;

import org.w3c.unicorn.response.Response;

public interface ResponseParser {
	public Response parse(InputStream inputStream);
}
