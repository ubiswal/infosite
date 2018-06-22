package com.ubiswal.infosite.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import org.apache.log4j.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class OperationHandler extends BaseHandler implements HttpHandler {
    private final static Logger LOGGER = Logger.getLogger(OperationHandler.class);
    final Authorize auth;
    
    public OperationHandler(Authorize auth) {
        this.auth = Objects.requireNonNull(auth);
    }

    public void handle(HttpExchange t) throws IOException {
        try {
            Headers headers = t.getRequestHeaders();
            String authorizationHeader = new String(Base64.decodeBase64(headers.get("Authorization").get(0)), "UTF-8");
            String operationHeader = new String(Base64.decodeBase64(headers.get("Operation").get(0)), "UTF-8");

            if (!auth.authenticate(operationHeader, authorizationHeader)) {
                LOGGER.warn("Unauthorized access attempt.");
                serveUnauthorizedPage(t);
            } else {
                LOGGER.info("Successfully authorized");
                
                // Step 1: Get back the map
                String request = IOUtils.toString(t.getRequestBody(), StandardCharsets.UTF_8);
                try {
                    Map<String, String> requestParams = convertReqToMap(request);
                    String operation = requestParams.get("Operation");
                   
                    if (operation.equals("AddEntry")) {
                        String name = requestParams.get("Name");
                        serveOperations(t, addEntry(name));
                        return;
                    } else if (operation.equals("ListEntries")) {
                        serveOperations(t, listEntries());
                        return;
                    }
                    serveMalformedReqPage(t);
                } catch (RuntimeException e) {
                    serveMalformedReqPage(t);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            serveErrorPage(t);
        }
    }
    
    private String addEntry(final String name) {
        return "Added " + name;
    }
    
    private String listEntries() {
        return "All entries";
    }
}
