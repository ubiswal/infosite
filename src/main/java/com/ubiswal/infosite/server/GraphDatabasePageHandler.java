package com.ubiswal.infosite.server;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class GraphDatabasePageHandler extends BaseHandler implements HttpHandler {
    private static final String GRAPH_HTML = "/html/graphdb.html";

    private final static Logger LOGGER = Logger.getLogger(GraphDatabasePageHandler.class);

    public void handle(HttpExchange t) throws IOException {
        LOGGER.info("Graph database page handler invoked.");

        try {
            String content = loadStaticPages(GRAPH_HTML);
            serveStaticPage(t, content);
            LOGGER.info("Returned graphdb.html");
        } catch (Exception e) {
            LOGGER.error("Exception while serving graphdb.html " + e);
            serveErrorPage(t);
        }
    }

}
