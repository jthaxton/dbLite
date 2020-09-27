package com.database.app;

import java.util.Iterator;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Writer {
  public static void write(String tableName, JSONObject inputObject) {
    try {
    JSONParser parser = new JSONParser();
    Object schema = parser.parse(new FileReader("schema.json"));
    JSONObject schemaObject = (JSONObject) schema;
    JSONObject tablesObject = (JSONObject) schemaObject.get("tables");
    JSONObject tableObject = (JSONObject) tablesObject.get(tableName);
    Table table = new Table(inputObject, tableObject);
    if (table.isValid()) {
      System.out.println("idFieldValue");

      int idFieldValue = getLastIdFromTableName(tableName);
      Iterator inputKeys = inputObject.keySet().iterator();
      System.out.println("Writing id " + idFieldValue + " to " + tableName);
      while(inputKeys.hasNext()) {
        String inputKey = (String) inputKeys.next();
        if (inputKey.equals("id")) {
          writeToIdFile(tableName, Integer.toString(idFieldValue), inputObject);
        } else {
          System.out.println(inputObject.get(inputKey));
          String inputKeyObject = (String) inputObject.get(inputKey).toString();
          System.out.println(inputKey);
          String fieldValue = inputKeyObject.toString();
          writeToFiles(tableName, inputKey, fieldValue, idFieldValue);
        }
      }
    }
    } catch(Exception e) {
      System.out.println(e);
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

  public static void writeToIdFile(String tableName, String idFieldValue, JSONObject tableDataObject) {
    String filepath = ".data/" + tableName + "/id/" + idFieldValue + ".json";
    try {
      File file = new File(filepath);
      file.createNewFile();
      BufferedWriter fileToWrite = new BufferedWriter(new FileWriter(file));
      tableDataObject.put("id", idFieldValue);
      fileToWrite.write(tableDataObject.toString());
      fileToWrite.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void writeToFiles(String tableName, String fieldName, String fieldValue, int id) {
    String filepath = ".data/" + tableName + "/" + fieldName + "/" + fieldValue + ".json";
    try {
      File file = new File(filepath);
      System.out.println(file.exists());
      if(file.exists()) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filepath));
        JSONArray ids = (JSONArray) (jsonObject.get("ids"));
        ids.add(id);
        JSONObject idsObject = new JSONObject();
        idsObject.put("ids", ids);
        BufferedWriter fileToWrite = new BufferedWriter(new FileWriter(filepath));
        fileToWrite.write(idsObject.toJSONString());
        fileToWrite.close();
      } else {
        File newFile = new File(filepath);
        newFile.createNewFile();
        JSONObject idsObject = new JSONObject();
        JSONArray ids = new JSONArray();
        ids.add(id);
        idsObject.put("ids", ids);

        BufferedWriter fileToWrite = new BufferedWriter(new FileWriter(newFile));
        System.out.println(idsObject);

        fileToWrite.write(idsObject.toJSONString());
        fileToWrite.close();
      }
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public static int getLastIdFromTableName(String tableName) {
    File migrationsDir = new File(".data/" + tableName + "/id/");
    File[] files = migrationsDir.listFiles();
    System.out.println(files);
    int maxId = 0;
    for(File file : files) {
      System.out.println((file.getName()));
      System.out.println(file.getName().toString().split(".json")[0]);
      int id = Integer.parseInt(file.getName().toString().split(".json")[0]);


      if (id > maxId) {
        maxId = id;
      }
    }
    return maxId + 1;
  }
}