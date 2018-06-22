package com.ubiswal.infosite.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public abstract class BaseHandler {
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
        os.flush();
        os.close();
    }
    
    protected void serveErrorPage(final HttpExchange t) throws IOException {
        String response = "Internal error";
        t.sendResponseHeaders(500, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
    }
    
    protected void serveUnauthorizedPage(final HttpExchange t) throws IOException {
        String response = "Unauthorized!";
        t.sendResponseHeaders(401, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
    }
    
    protected void serveMalformedReqPage(final HttpExchange t) throws IOException {
        String response = "Malformed request!";
        t.sendResponseHeaders(400, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
    }
    
    protected void serveOperations(final HttpExchange t, final String response) throws IOException {
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.flush();
        os.close();
    }
    
    protected Map<String, String> convertReqToMap(final String req) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(req, Map.class);
        return map;
    }
}
