package com.database.app;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.Stack;

public class Schema 
{
    // public static boolean validate()
    // {
    //   return true;
    // }

    public static void create()
    {
      String version = Long.toString(System.currentTimeMillis());
      JSONObject object = new JSONObject();
      JSONArray migrations = new JSONArray();
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
          schema.write(object.toJSONString());
          schema.close();
          System.out.println("Schema created: schema.json");
      } catch( Exception e ) {
        System.out.println("Schema could not be created");
      }
    }

    public static void drop() {
      System.out.println("WARNING: THIS WILL PERMANENTLY DELETE YOUR SCHEMA. DO YOU WISH TO CONTINUE? (y/n)");
      yesOrNo();
    }

    public static void migrate() {
        JSONParser parser = new JSONParser();
        try {
        Object obj = parser.parse(new FileReader("schema.json"));
        JSONObject json = (JSONObject) obj;
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
      JSONArray migrations = (JSONArray) json.get("migrationsApplied");
      
      while(stack.size() > 0) {
        String file = stack.pop().getName();
        lastMigration = file;
        System.out.println(lastMigration);
        migrations.add(file);

      }
      System.out.println(migrations.size());
      String splitMigration = lastMigration.split("_")[0];
      json.put("lastMigrationApplied", lastMigration);
      json.put("migrationsApplied", migrations);
      json.put("version", splitMigration);
      System.out.println(json);
      BufferedWriter schema = new BufferedWriter(new FileWriter("schema.json"));
      schema.write(json.toJSONString());
      schema.close();
    } catch(Exception e) {
      System.out.println(e);
    }
    }

    public static void createMigration(String name, String tableName) {
      try {
        String version = Long.toString(System.currentTimeMillis());
        String filename = "migrations/" + version + "_" + name + ".json";
        BufferedWriter migration = new BufferedWriter(new FileWriter(filename));
        JSONObject object = new JSONObject();
        JSONObject fields = new JSONObject();
        object.put("version", version);
        object.put("tableName", tableName.toLowerCase());
        object.put("fields", fields);
        migration.write(object.toJSONString());
        migration.close();
      } catch(Exception e) {
        System.out.println(e);
      }
    }

    static void yesOrNo() {
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
}
