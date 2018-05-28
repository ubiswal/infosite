package com.ubiswal.infosite.server;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class HomeHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		String response = "<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"  <head>\n" + 
				"    Home\n" + 
				"  </head>\n" + 
				"\n" + 
				"  <body>\n" + 
				"    Welcome to my homepage!\n" + 
				"  </body>\n" + 
				"</html>\n" + 
				"";
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}
}
