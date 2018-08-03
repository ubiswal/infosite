package com.ubiswal.infosite.utils;

import java.io.IOException;


import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

// reference: http://www.vogella.com/tutorials/Logging/article.html
public class MyLogger {
    private static String LOG_PATH = "/var/log/Application.log";

    static public void setup() throws IOException {
        ConsoleAppender console = new ConsoleAppender(); //create appender
        //configure the appender
        String PATTERN = "%d [%p] %c %m%n";
        console.setLayout(new PatternLayout(PATTERN)); 
        console.setThreshold(Level.ALL);
        console.activateOptions();
        //add appender to any Logger (here is root)
        Logger.getRootLogger().addAppender(console);

        FileAppender fa = new FileAppender();
        fa.setName("FileLogger");
        fa.setFile(LOG_PATH);
        fa.setLayout(new PatternLayout(PATTERN));
        fa.setThreshold(Level.INFO);
        fa.setAppend(true);
        fa.activateOptions();

        //add appender to any Logger (here is root)
        Logger.getRootLogger().addAppender(fa);
        //repeat with all other desired appenders
    }
}
