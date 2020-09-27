package com.database.app;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Migration 
{
  static boolean isValidJson(String filename) {
    try{
      JSONParser parser = new JSONParser();
      parser.parse(filename);
      return true;
      } 
      catch(Exception e){
      System.out.println("Not a valid Json String:"+ e);
      return false;
      }
  }

  public static void create(String name, String tableName) {
    try {
      String version = Long.toString(System.currentTimeMillis());
      String filename = "migrations/" + version + "_" + name + ".json";
      BufferedWriter migration = new BufferedWriter(new FileWriter(filename));
      JSONObject object = new JSONObject();
      JSONObject fields = new JSONObject();
      JSONObject idValidator = new JSONObject();
      idValidator.put("type", "int");
      idValidator.put("null", "false");
      idValidator.put("index", "true");
      fields.put("id", idValidator);
      object.put("version", version);
      object.put("tableName", tableName.toLowerCase());
      object.put("fields", fields);
      migration.write(object.toJSONString());
      migration.close();
      System.out.println(name + " migration created.");
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}
