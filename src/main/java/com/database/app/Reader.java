package com.database.app;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;

public class Reader {
  public static JSONObject find(String tableName, String id) {
    String filePath = ".data/" + tableName + "/id/" + id + ".json";
    try {
    JSONParser parser = new JSONParser();
    JSONObject record = (JSONObject) parser.parse(new FileReader(filePath));
    System.out.println(record);
    return record;
    }
    catch (Exception e) {
      return new JSONObject();
    }
  }

  public static void findBy(String tableName, JSONObject jsonObject) {
    Iterator keys = jsonObject.keySet().iterator();
    JSONParser parser = new JSONParser();
    Set<Integer> union = new HashSet<Integer>(new ArrayList());
    try {
    while(keys.hasNext()) {
      String key = (String) keys.next();
      String value = jsonObject.get(key).toString();
      String filePath = ".data/" + tableName + "/" + key + "/" + value + ".json";
      JSONObject recordIdsObject = (JSONObject) parser.parse(new FileReader(filePath));
      JSONArray recordIds = (JSONArray) recordIdsObject.get("ids");
      if (union.size() == 0) {
        union = new HashSet<Integer>(recordIds);
      } else {
        Set<Integer> nextSet = new HashSet<Integer>(recordIds);
        union.retainAll(nextSet);
      }
    }
    Iterator ids = union.iterator();
    while (ids.hasNext()) {
      String idFilePath = ".data/" + tableName + "/id/" + ids.next() + ".json";
      JSONObject recordIdsObject = (JSONObject) parser.parse(new FileReader(idFilePath));
      System.out.println(recordIdsObject);
    }
  } catch (Exception e) {
    System.out.println(e);
  }
  }
}
// findBy users {"cashDollars":"213","name":"joe"}