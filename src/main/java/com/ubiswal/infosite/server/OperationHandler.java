package com.ubiswal.infosite.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class OperationHandler implements HttpHandler {
    private final static Logger LOGGER = Logger.getLogger(GraphDatabasePageHandler.class.getName());

    @Override
    public void handle(HttpExchange t) throws IOException {
        LOGGER.info("Graph database page handler invoked.");

        try {
            System.out.println("GRAPH!!!");
            String content = "Hello world!";
            t.sendResponseHeaders(200, content.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(content.getBytes());
            os.close();
        } catch (Exception e) {
            String response = "Internal error";
            t.sendResponseHeaders(500, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
