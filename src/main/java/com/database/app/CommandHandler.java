package com.database.app;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;

public class CommandHandler 
{
    public static void call()
    {
      System.out.print("DBLite> ");
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      try {
        String[] splitInput = reader.readLine().split(" ");

        if(splitInput[0].equals("schema:create")) {
            Schema.create();
        } else if (splitInput[0].equals("schema:drop")) {
            Schema.drop();
        } else if (splitInput[0].equals("schema:migrate")) {
          Schema.migrate();
        } else if (splitInput[0].equals("schema:migrate:create")) {
          Migration.create(splitInput[1], splitInput[2]);
        } else if (splitInput[0].equals("write")) {
          try {
          JSONParser parser = new JSONParser();
          JSONObject json = (JSONObject) parser.parse(splitInput[2]);
          System.out.println(splitInput[2]);
          System.out.println(json);
          System.out.println(splitInput[1]);
          Writer.write(splitInput[1].toString(), json);
          } catch(Exception e) {
            System.out.println("Unable to write");
          }
        } else if (splitInput[0].equals("exit")) {
          return;
        } else {
          System.out.println("Invalid input. Try again.");
        }
        call();
    } catch (IOException e) {
      System.out.println(e);
      }
    }
}
