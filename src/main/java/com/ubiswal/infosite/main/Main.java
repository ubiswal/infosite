package com.ubiswal.infosite.main;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.ubiswal.infosite.server.HomeHandler;
import com.ubiswal.infosite.server.TestHandler;
import com.ubiswal.infosite.utils.MyLogger;

@SuppressWarnings("restriction")
public class Main {
    public static void main(String args[]) throws IOException {
        // Create handlers
        HomeHandler homeHandler = new HomeHandler();
        TestHandler testHandler = new TestHandler();

        // Set up logging
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", testHandler);
        server.createContext("/", homeHandler);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
