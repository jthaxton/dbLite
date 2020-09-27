package com.database.app;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.Iterator;

public class JsonUtility {
  public static JSONArray convertToJsonArray(JSONObject obj) {
    JSONObject jsonObject = (JSONObject) obj;
    Iterator x = obj.keySet().iterator();
    JSONArray jsonArray = new JSONArray();
    
    while (x.hasNext()){
      String key = (String) x.next();
      jsonArray.add(key);
    }
    return jsonArray;
  }
}

