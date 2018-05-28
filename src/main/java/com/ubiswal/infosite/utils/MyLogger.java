package com.ubiswal.infosite.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// reference: http://www.vogella.com/tutorials/Logging/article.html
public class MyLogger {
    private static String LOG_PATH = "/var/log/Application.log";
    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;

    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler(LOG_PATH);

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}
