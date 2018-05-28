package com.ubiswal.infosite.main;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.ubiswal.infosite.server.HomeHandler;
import com.ubiswal.infosite.server.TestHandler;

@SuppressWarnings("restriction")
public class Main {
	public static void main(String args[]) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new TestHandler());
        server.createContext("/", new HomeHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
	}
}
