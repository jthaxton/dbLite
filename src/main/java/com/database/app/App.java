package com.database.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.StringBuilder;
import java.io.InputStreamReader;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Starting DBLite.");
        // CommandHandler.call();
        Writer.write("users");
    }
}
