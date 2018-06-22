package com.ubiswal.infosite.server;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class HomeHandler extends BaseHandler implements HttpHandler {
    private static final String HOME_HTML = "/html/home.html";
    final static Logger LOGGER = Logger.getLogger(HomeHandler.class);

    public void handle(HttpExchange t) throws IOException {
        LOGGER.info("Home page handler invoked.");

        try {
            String content = loadStaticPages(HOME_HTML);
            serveStaticPage(t, content);
            LOGGER.info("Returned home.html");
        } catch (Exception e) {
            LOGGER.error("Exception while serving home.html " + e);
            serveErrorPage(t);
        }
    }
}
