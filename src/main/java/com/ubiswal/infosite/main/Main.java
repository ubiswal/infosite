package com.ubiswal.infosite.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

import com.sun.net.httpserver.HttpServer;
import com.ubiswal.infosite.server.Authorize;
import com.ubiswal.infosite.server.GraphDatabasePageHandler;
import com.ubiswal.infosite.server.HomeHandler;
import com.ubiswal.infosite.server.OperationHandler;
import com.ubiswal.infosite.server.TestHandler;
import com.ubiswal.infosite.utils.MyLogger;

@SuppressWarnings("restriction")
public class Main {
 // Replace this with your password before starting the server
    private static final String SECRET = "SECRET";
    private static final int PORT = 80;

    public static void main(String args[]) throws IOException {
        Authorize auth = new Authorize(SECRET);
        // Create handlers
        HomeHandler homeHandler = new HomeHandler();
        TestHandler testHandler = new TestHandler();
        GraphDatabasePageHandler graphDbHandler = new GraphDatabasePageHandler();
        OperationHandler operationHandler = new OperationHandler(auth);

        // Set up logging
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url = loader.getResource("configdata/log4j.properties");
            PropertyConfigurator.configure(url);
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/test", testHandler);
        server.createContext("/db", graphDbHandler);
        server.createContext("/operation", operationHandler);
        server.createContext("/", homeHandler);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
