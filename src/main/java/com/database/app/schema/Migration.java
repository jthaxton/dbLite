package com.database.app;

import org.json.simple.parser.JSONParser;

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
}
