package com.ubiswal.infosite.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class GraphDatabasePageHandler implements HttpHandler {
    private static final String GRAPH_HTML = "/html/graphdb.html";

    private final static Logger LOGGER = Logger.getLogger(GraphDatabasePageHandler.class.getName());

    @Override
    public void handle(HttpExchange t) throws IOException {
        LOGGER.info("Graph database page handler invoked.");

        try {
        	System.out.println("GRAPH!!!");
            InputStream in = getClass().getResourceAsStream(GRAPH_HTML);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String content = "";
            String line = null;
            while ((line = reader.readLine()) != null) {
                content = content + line + "\n";
            }
            reader.close();
            t.sendResponseHeaders(200, content.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(content.getBytes());
            os.close();
            LOGGER.info("Returning graphdb.html");
        } catch (Exception e) {
            LOGGER.error("Exception while serving graphdb.html " + e);
            String response = "Internal error";
            t.sendResponseHeaders(500, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
