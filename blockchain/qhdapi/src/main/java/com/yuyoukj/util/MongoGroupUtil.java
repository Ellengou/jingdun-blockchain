package com.yuyoukj.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoGroupUtil {
	public static String generateFinalizeObject(int type) {
		String function = "";
		switch (type) {
			case 0:
				function = "function(doc) { doc.avg = doc.count/2 ;}";
				break;
			case 1:
				function = "function(doc) { doc.avg = doc.count/2 ;}";
				break;
			default:
				function = "function(doc) { doc.avg = doc.count/2 ;}";
		}
		return function;
	}

	public static DBObject generateGroupKeyObject(HashMap<String, String> gpKeyMap) {
		DBObject keyObject = new BasicDBObject();
		Iterator<String> gpKeyIt = gpKeyMap.keySet().iterator();
		while (gpKeyIt.hasNext()) {
			keyObject.put(gpKeyIt.next(), true);
		}
		return keyObject;
	}

	public static DBObject generateInitObject(HashMap<String, Object> operateMap) {
		DBObject init = new BasicDBObject();
		for (Map.Entry<String, Object> entry : operateMap.entrySet()) {
			init.put(entry.getKey(), entry.getValue());
		}
		return init;
	}

	public static String generateReduceObject(int type) {
		String function = "";
		switch (type) {
			case 0:
				function = "function(doc, result) { result.count++;}";
				break;
			case 1:
				function = "function(doc, result) { result.count++;}";
				break;
			default:
				function = "function(doc, result) { result.count++;}";
		}
		return function;
	}
}
