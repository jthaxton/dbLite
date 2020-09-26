package com.database.app;

import java.util.ArrayList;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.InputStreamReader;


public class Writer {
  public static void write(String tableName) {
    try {
    JSONObject inputObject = parseInput();
    JSONParser parser = new JSONParser();
    Object schema = parser.parse(new FileReader("schema.json"));
    JSONObject schemaObject = (JSONObject) schema;
    JSONObject tablesObject = (JSONObject) schemaObject.get("tables");
    JSONObject tableObject = (JSONObject) tablesObject.get(tableName);
    Table table = new Table(inputObject, tableObject);
    if (table.isValid()) {
      System.out.println("Made it");
    }
    } catch(Exception e) {

    }
  }
  public static JSONObject parseInput() {
    try {
    BufferedReader streamReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8")); 
    StringBuilder responseStrBuilder = new StringBuilder();
    
    String inputStr;
    while ((inputStr = streamReader.readLine()) != null) {
        responseStrBuilder.append(inputStr);
        if(inputStr.contains("}")) break;
    }
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(responseStrBuilder.toString());
    return json;
    } catch(Exception e) {
        return new JSONObject();
    }
  }

  public static void writeToFiles() {

  }

  // public static String getLastIdFromTableName(String tableName) {
  //   File migrationsDir = new File(".data/" + tableName + "/id/");
  //   File[] files = migrationsDir.listFiles();
  //   ArrayList<Integer> indices = new ArrayList<Integer>();
  //   int maxId = 0;
  //   for(File file : files) {
  //     int id = String.parseInt(file.getName().split(".")[0]);
  //     if (id > maxId) {
  //       maxId = id;
  //     }
  //   }
  //   return Integer.toString(currMax);
  // }
}