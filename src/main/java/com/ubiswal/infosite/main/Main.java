package com.ubiswal.infosite.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String CONFIG_FILE = "/configdata/server.json";
    private static final String LOG4J_FILE = "configdata/log4j.properties";
    private static final int PORT = 80;
    
    public Map<String, String> loadConfigFile() throws IOException {
        InputStream in = getClass().getResourceAsStream(CONFIG_FILE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String content = "";
        String line = null;
        while ((line = reader.readLine()) != null) {
            content = content + line + "\n";
        }
        reader.close();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(content, Map.class);
        return map;
    }

    public static void main(String args[]) throws IOException {
        // Load configuration
        Main obj = new Main();
        Map<String, String> config = obj.loadConfigFile();
        
        // Create handlers
        Authorize auth = new Authorize(config.get("Secret"));
        HomeHandler homeHandler = new HomeHandler();
        TestHandler testHandler = new TestHandler();
        GraphDatabasePageHandler graphDbHandler = new GraphDatabasePageHandler();
        OperationHandler operationHandler = new OperationHandler(auth);

        // Set up logging
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url = loader.getResource(LOG4J_FILE);
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
