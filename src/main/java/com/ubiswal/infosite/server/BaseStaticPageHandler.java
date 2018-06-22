package com.ubiswal.infosite.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public abstract class BaseStaticPageHandler {
    protected String loadStaticPages(final String fileName) throws IOException {
        InputStream in = getClass().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String content = "";
        String line = null;
        while ((line = reader.readLine()) != null) {
            content = content + line + "\n";
        }
        reader.close();
        return content;
    }
    
    protected void serveStaticPage(final HttpExchange t, final String content) throws IOException {
        t.sendResponseHeaders(200, content.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(content.getBytes());
        os.close();
    }
    
    protected void serveErrorPage(final HttpExchange t) throws IOException {
        String response = "Internal error";
        t.sendResponseHeaders(500, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
