package com.ubiswal.infosite.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class OperationHandler implements HttpHandler {
    private final static Logger LOGGER = Logger.getLogger(OperationHandler.class.getName());
    final Authorize auth;
    
    public OperationHandler(Authorize auth) {
        this.auth = Objects.requireNonNull(auth);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            Headers headers = t.getRequestHeaders();
            if (!headers.containsKey("Authorization") || !headers.containsKey("Operation")) {
                LOGGER.warn("Unauthorized access. Headers absent");
                String response = "Unauthorized!";
                t.sendResponseHeaders(401, response.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
            String authorizationHeader = new String(Base64.decodeBase64(headers.get("Authorization").get(0)), "UTF-8");
            String operationHeader = new String(Base64.decodeBase64(headers.get("Operation").get(0)), "UTF-8");
            
            LOGGER.info(operationHeader);
            LOGGER.info(authorizationHeader);

            if (!auth.authenticate(operationHeader, authorizationHeader)) {
                LOGGER.warn("Unauthorized access. HMAC mismatch.");
                String response = "Unauthorized!";
                t.sendResponseHeaders(401, response.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                LOGGER.info("Authorized.");
                String content = "Hello world!";
                t.sendResponseHeaders(200, content.getBytes().length);
                OutputStream os = t.getResponseBody();
                os.write(content.getBytes());
                os.close();
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String response = "Internal error";
            t.sendResponseHeaders(500, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
