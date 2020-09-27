package com.database.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.lang.String;
import java.util.Set;
import java.util.Iterator;


public class Table {
  JSONObject tableObject;
  JSONObject schemaTableObject;
  Set schemaKeys;
  Set tableObjectKeys;
  

  public Table(JSONObject tableObject, JSONObject schemaTableObject) {
    this.tableObject = tableObject;
    this.schemaTableObject = schemaTableObject;
    this.schemaKeys = tableObject.keySet();
    this.tableObjectKeys = schemaTableObject.keySet();
  }

  public boolean isValid() {
    if (validateTypes()) {
      System.out.println("Type validations passed");
    } else return false;
    if(validatePresence()) {
      System.out.println("Presence validations passed");
    } else return false;
    return true;
  }

  boolean isValidColumn(String inputKey, String inputValue) {

    JSONObject validations = (JSONObject) schemaTableObject.get(inputKey);
    String typeString = (String) validations.get("type").toString();
    switch (typeString) {
      case "int":
        try{
          Integer.parseInt(inputValue);
        } catch (Exception e) {
          System.out.println(inputValue + "at" + inputKey + "Validation Failed");
          System.out.println("Transaction Rollback");
          return false;
        }
      default:
        return true;

    }
  }

  boolean validateTypes() {
    System.out.println("Validating types");
    
    for(Iterator iterator = tableObject.keySet().iterator(); iterator.hasNext();) {

      String key = (String) iterator.next();
      String value = (String) tableObject.get(key);
      if(!isValidColumn(key, value)) return false;
    }
    return true;
  }

  public boolean validatePresence() {
    System.out.println("Validating presence");
    for(Iterator iterator = schemaKeys.iterator(); iterator.hasNext();) {
      String key = (String) iterator.next();

      if(!tableObjectKeys.contains(key)) {
        System.out.print("Validation failed: ");
        System.out.print(key + " missing from input");
         return false;
        }
    }
    for(Iterator iterator = tableObjectKeys.iterator(); iterator.hasNext();) {
      String key = (String) iterator.next();

      if(!schemaKeys.contains(key)) {
        System.out.print("Validation failed: ");
        System.out.print(key + " missing from schema");
        return false;
      }
    }
    return true;
  }
}
