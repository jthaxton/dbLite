package com.database.app;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CommandHandler 
{
    public static void call()
    {
      System.out.print("DBLite> ");
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      try {
        String[] splitInput = reader.readLine().toLowerCase().split(" ");
        switch(splitInput[0]) {
          case "schema:create":
            Schema.create();
            break;
          case "schema:drop":
            Schema.drop();
            break;
          case "schema:migrate":
            Schema.migrate();
            break;
          case "schema:migrate:create":
            Migration.create(splitInput[1], splitInput[2]);
            break;
          case "write":
            System.out.print("DBLite> ");
            // BufferedReader jsonInput = new BufferedReader(new InputStreamReader(System.in));
            // Model.write(jsonInput);
          case "exit":
            return;
          default:
            System.out.println("Invalid input. Try again.");
        }
        call();
    } catch (IOException e) {
      System.out.println(e);
      }
    }
}
