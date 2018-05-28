package com.ubiswal.infosite.server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class TestHandler implements HttpHandler {

	public void handle(HttpExchange t) throws IOException {
		String response = "This is the response";
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}
}
