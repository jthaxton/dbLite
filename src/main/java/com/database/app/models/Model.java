package com.database.app;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.Iterator;
import java.io.File;

public class Model 
{
  // public static void write(BufferedReader input) {
  //   JSONObject json = generateJSONObjectFromInput(input);
  //   JSONParser parser = new JSONParser();
  //   try {     
  //     Object parsedSchema = parser.parse(new FileReader("schema.json"));
  //     JSONObject parsedSchemaJson = (JSONObject) parsedSchema;
  //     JSONObject fields = (JSONObject) parsedSchemaJson.get("fields");

  //   }
  //   catch(Exception e) {
  //     System.out.println(e);
  //   }
  //   }

  // public static boolean isValidInsertion(JSONObject jsonInput, JSONObject schemaJson) {
  //   String tableName = (String) jsonInput.get("tableName");
  //   Iterator keys = jsonInput.keySet().iterator();
  //   while(keys.hasNext()) {
  //     String key = (String) keys.next();
  //     File fieldDir = new File(".data/" + tableName + "/" + key);
  //     if (!fieldDir.exists()) {
  //       fieldDir.mkdir();
  //     }
  //   }
  // }

  // public static JSONObject generateJSONObjectFromInput(BufferedReader input) {
  //   StringBuilder sb = new StringBuilder();

  //   String line;
  //   while ((line = input.readLine()) != null) {
  //       sb.append(line);
  //   }
  //   JSONObject json = (JSONObject) new JSONObject(sb.toString());
  //   return json;
  // }
}
