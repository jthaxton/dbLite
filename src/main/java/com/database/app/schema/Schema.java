package com.database.app;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.Stack;
import java.util.Iterator;

public class Schema 
{
    public static void create()
    {
      String version = Long.toString(System.currentTimeMillis());
      JSONObject object = new JSONObject();
      JSONArray migrations = new JSONArray();
      JSONArray tableNames = new JSONArray();
      JSONObject tables = new JSONObject();
      try {
        File file = new File("schema.json");
        if(file.exists()) {
          System.out.println("Schema already exists!");
          return;
        }
        BufferedWriter schema = new BufferedWriter(new FileWriter("schema.json"));
        
          object.put("version", version);
          object.put("lastMigrationApplied", "");
          object.put("migrationsApplied", migrations);
          object.put("tableNames", tableNames);
          object.put("tables", tables);
          schema.write(object.toJSONString());
          schema.close();
          System.out.println("Schema created: schema.json");
      } catch( Exception e ) {
        System.out.println("Schema could not be created");
      }
    }

    public static void drop() {
      System.out.println("WARNING: THIS WILL PERMANENTLY DELETE YOUR SCHEMA. DO YOU WISH TO CONTINUE? (y/n)");
      shouldDeleteSchema();
    }

    public static void migrate() {
        JSONParser parser = new JSONParser();
        try {
        Object obj = parser.parse(new FileReader("schema.json"));
        JSONObject json = (JSONObject) obj;
        JSONObject tables = (JSONObject) json.get("tables");
        JSONArray tableNames = (JSONArray) json.get("tableNames");
        String lastVersion = json.get("lastMigrationApplied").toString();
        if (migrationsUpToDate()) {
        return;
      }

      System.out.println("Applying migrations...");
      File migrationsDir = new File("migrations/");
      File[] files = migrationsDir.listFiles();
      Stack<File> stack = new Stack();
      for(int i=files.length - 1;i >= 0;i--) {
        if(files[i].getName().split("_")[0].equals(lastVersion)) return;
        stack.push(files[i]);
      }

      String lastMigration = "";
      while(stack.size() > 0) {
        String file = stack.pop().getName();
        System.out.println("Applying " + file);
        lastMigration = file;
        Object migrationFile = parser.parse(new FileReader("migrations/" + file));
        JSONObject migrationFileJSONObj = (JSONObject) migrationFile;
        JSONObject fieldsJSONObj = (JSONObject) migrationFileJSONObj.get("fields");

        tables.put(migrationFileJSONObj.get("tableName"), fieldsJSONObj);
        tableNames.add(migrationFileJSONObj.get("tableName"));
        generateDataStructure(migrationFileJSONObj);
      }
      String splitMigration = lastMigration.split("_")[0];
      json.put("lastMigrationApplied", lastMigration);
      json.put("tableNames", tableNames);
      json.put("version", splitMigration);
      json.put("tables", tables);
      System.out.println(json);
      BufferedWriter schema = new BufferedWriter(new FileWriter("schema.json"));
      schema.write(json.toJSONString());
      schema.close();
    } catch(Exception e) {
      System.out.println("Something went wrong");
    }
    }

    static void shouldDeleteSchema() {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      try {
        switch(reader.readLine().toLowerCase()) {
          case "n":
            break;
          case "y":
            File schema = new File("schema.json");
            if (schema.delete()) {
              System.out.println("Schema successfully deleted");
            } else {
              System.out.println("Schema could not be deleted. Check to make sure schema.json exists");
            }
            break;
          default:
            System.out.println("Invalid input. Try again.");
        }
    } catch (IOException e) {
      System.out.println(e);
      }
    }

    static boolean migrationsUpToDate() {
      File migrationsDir = new File("migrations/");
      File[] files = migrationsDir.listFiles();
      String name = files[files.length - 1].getName();
      String version = name.split("_")[0];
      JSONParser parser = new JSONParser();
      try {     
        Object obj = parser.parse(new FileReader("schema.json"));
        JSONObject json = (JSONObject) obj;
        System.out.println(version);
        System.out.println(json.get("version"));
        if (json.get("version").equals(version)) {
          System.out.println("Migrations up to date.");
          return true;
        }
        return false;
      } catch (Exception e) {
        System.out.println(e);
        return false;
      }
    }

    static void generateDataStructure(JSONObject migrationFileJSONObj) {
      String tableName = (String) migrationFileJSONObj.get("tableName");
      File tableDir = new File(".data/" + tableName);
      if(!tableDir.exists()) {
        tableDir.mkdir();
      }
      JSONObject fields = (JSONObject) migrationFileJSONObj.get("fields");
      Iterator keys = fields.keySet().iterator();
      while(keys.hasNext()) {
        String key = (String) keys.next();
        File fieldDir = new File(".data/" + tableName + "/" + key);
        if (!fieldDir.exists()) {
          fieldDir.mkdir();
        }
      }
    }
}
