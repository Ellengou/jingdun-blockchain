package com.yuyoukj.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongDBUtil {
	final static Log logger = LogFactory.getLog(MongDBUtil.class);

	public static void main(String[] args) throws Exception {

	}

	private int closeBDB;
	private Mongo mg;
	private DB db;
	private DBCollection logfeedback;
	private DBCollection SDKlogfeedback;
	private DBCollection logpv;
	private DBCollection thirdapifeedback;
	private DBCollection logwallfeedback;
	private DBCollection topfeedback;
	private DBCollection livefeedback;

	public MongDBUtil(Mongo mg, String dbname) {
		try {
			// db=getMongDB(ip, port, osmdb);
			db = mg.getDB(dbname);
			logfeedback = db.getCollection("log_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			checkIndex(logfeedback, "sessionId");
			SDKlogfeedback = db.getCollection("SDK_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			checkIndex(SDKlogfeedback, "sessionId");
			logwallfeedback = db.getCollection("log_wall_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			logpv = db.getCollection("log_pv_" + DateUtil.getCurrentDate("yyyyMMdd"));
			thirdapifeedback = db.getCollection("thirdapi_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			checkIndex(thirdapifeedback, "sessionId");
			topfeedback = db.getCollection("top_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			livefeedback = db.getCollection("live_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public MongDBUtil(String ip, Integer port, String osmdb) {
		try {
			db = getMongDB(ip, port, osmdb);
			logfeedback = db.getCollection("log_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			checkIndex(logfeedback, "sessionId");
			SDKlogfeedback = db.getCollection("SDK_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			checkIndex(SDKlogfeedback, "sessionId");
			logwallfeedback = db.getCollection("log_wall_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			logpv = db.getCollection("log_pv_" + DateUtil.getCurrentDate("yyyyMMdd"));
			thirdapifeedback = db.getCollection("thirdapi_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			checkIndex(thirdapifeedback, "sessionId");
			topfeedback = db.getCollection("top_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
			livefeedback = db.getCollection("live_feedback_" + DateUtil.getCurrentDate("yyyyMMdd"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	private void checkIndex(DBCollection collection, String field) {
		if (null == collection || null == field || "".equals(field)) {
			return;
		}
		List<DBObject> indexs = collection.getIndexInfo();
		int f = 0;
		for (DBObject index : indexs) {
			BasicDBObject o = (BasicDBObject) index.get("key");
			if (null != o && null != o.getString(field)) {
				f++;
			}
		}
		if (f == 0) {
			DBObject keys = new BasicDBObject();
			keys.put(field, 1);
			collection.createIndex(keys);
		}
	}

	public DBCollection getAppointLogfeedback(String date) {
		return db.getCollection("log_feedback_" + date);
	}

	public int getCloseBDB() {
		return closeBDB;
	}

	public DBCollection getfeedbackCollectionByName(String name) {
		return db.getCollection(name);
	}

	public DBCollection getLivefeedback() {
		if (!livefeedback.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setLivefeedback(db.getCollection("live_feedback_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return livefeedback;
	}

	public DBCollection getLogfeedback() {
		if (!logfeedback.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setLogfeedback(db.getCollection("log_feedback_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return logfeedback;
	}

	public DBCollection getLogpv() {
		if (!logpv.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setLogpv(db.getCollection("log_pv_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return logpv;
	}

	public DBCollection getLogwallfeedback() {
		if (!logwallfeedback.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setLogwallfeedback(db.getCollection("log_wall_feedback_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return logwallfeedback;
	}

	public DB getMongDB(String ip, Integer port, String osmdb) throws UnknownHostException {
		DB retdb = null;
		try {
			MongoOptions options = new MongoOptions();
			options.autoConnectRetry = true;
			options.socketKeepAlive = true;
			options.connectionsPerHost = 50;
			options.socketTimeout = 600000;
			options.threadsAllowedToBlockForConnectionMultiplier = 10;
			mg = new Mongo(new ServerAddress(ip, port), options);
			retdb = mg.getDB(osmdb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retdb;
	}

	public List<Object> getObject(DBCollection collection, Class<?> inputObj, BasicDBObject queryObject) throws Exception {
		long l1 = System.currentTimeMillis();
		collection.setObjectClass(BasicDBObject.class);
		DBCursor cursor = collection.find(queryObject);
		DBObject dbObject = null;
		List<Object> objectList = new ArrayList<Object>();
		try {
			Class<?> classType = inputObj;
			while (cursor.hasNext()) {
				dbObject = cursor.next();
				Object retObject = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
				Field[] fields = classType.getDeclaredFields();
				for (Field field : fields) {
					// 动态生成getter和setter方法
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String firstChar = fieldName.substring(0, 1).toUpperCase();
					String setterName = "set" + firstChar + fieldName.substring(1);
					Method setter = classType.getMethod(setterName, new Class[] { field.getType() });
					// 执行getter方法获取当前域的值
					Object result = dbObject.get(fieldName);
					// 执行setter给user2赋值
					setter.invoke(retObject, new Object[] { result });
				}
				objectList.add(retObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// collection.remove(retBaseObj);
		long l2 = System.currentTimeMillis();
		if ((l2 - l1) > 1000) {
			logger.error("getObject-------:" + (l2 - l1));
		}
		return objectList;
	}

	public Object getObject(DBObject dbObject, Class<?> classType) {
		Object retObject = null;
		try {
			retObject = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
			Field[] fields = classType.getDeclaredFields();
			for (Field field : fields) {
				// 动态生成getter和setter方法
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String firstChar = fieldName.substring(0, 1).toUpperCase();
				String setterName = "set" + firstChar + fieldName.substring(1);
				Method setter = classType.getMethod(setterName, new Class[] { field.getType() });
				// 执行getter方法获取当前域的值
				Object result = dbObject.get(fieldName);
				// 执行setter给user2赋值
				setter.invoke(retObject, new Object[] { result });
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retObject;
	}

	public DBCollection getPreLogfeedback() {
		return db.getCollection("log_feedback_" + DateUtil.getSomedayDate(-1, "yyyyMMdd"));
	}

	public DBCollection getSDKLogfeedback() {
		if (!SDKlogfeedback.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setSDKLogfeedback(db.getCollection("SDK_feedback_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return SDKlogfeedback;
	}

	public DBCollection getThirdapifeedback() {
		if (!thirdapifeedback.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setThirdapifeedback(db.getCollection("thirdapi_feedback_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return thirdapifeedback;
	}

	public DBCollection getThirdPreLogfeedback() {
		return db.getCollection("thirdapi_feedback_" + DateUtil.getSomedayDate(-1, "yyyyMMdd"));
		// return db.getCollection("thirdapi_feedback_20131025");
	}

	public DBCollection getTopfeedback() {
		if (!topfeedback.getName().endsWith(DateUtil.getCurrentDate("yyyyMMdd"))) {
			setTopfeedback(db.getCollection("top_feedback_" + DateUtil.getCurrentDate("yyyyMMdd")));
		}
		return topfeedback;
	}

	public void saveObject(DBCollection collection, Object inputObj) throws Exception {
		Class<?> classType = inputObj.getClass();
		Field[] fields = classType.getDeclaredFields();
		BasicDBObject basicObject = new BasicDBObject();
		for (Field field : fields) {
			String fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName)) {
				continue;
			}
			String firstChar = fieldName.substring(0, 1).toUpperCase();
			String getterName = "get" + firstChar + fieldName.substring(1);
			Method getter = classType.getMethod(getterName);
			Object result = getter.invoke(inputObj);
			if (result != null) {
				basicObject.put(fieldName, result);
			}
		}
		long l1 = System.currentTimeMillis();
		collection.insert(basicObject);
		long l2 = System.currentTimeMillis();
		if ((l2 - l1) > 1000) {
			logger.error("getObject-------:" + (l2 - l1));
		}
	}

	public void setCloseBDB(int closeBDB) {
		this.closeBDB = closeBDB;
	}

	public void setLivefeedback(DBCollection livefeedback) {
		this.livefeedback = livefeedback;
	}

	public void setLogfeedback(DBCollection logfeedback) {
		DBObject keys = new BasicDBObject();
		keys.put("sessionId", 1);
		logfeedback.createIndex(keys);
		this.logfeedback = logfeedback;
	}

	public void setLogpv(DBCollection logpv) {
		this.logpv = logpv;
	}

	public void setLogwallfeedback(DBCollection logwallfeedback) {
		DBObject keys = new BasicDBObject();
		keys.put("tid", 1);
		logwallfeedback.createIndex(keys);
		this.logwallfeedback = logwallfeedback;
	}

	public void setSDKLogfeedback(DBCollection SDKlogfeedback) {
		DBObject keys = new BasicDBObject();
		keys.put("sessionId", 1);
		SDKlogfeedback.createIndex(keys);
		this.SDKlogfeedback = SDKlogfeedback;
	}

	public void setThirdapifeedback(DBCollection thirdapifeedback) {
		DBObject keys = new BasicDBObject();
		keys.put("sessionId", 1);
		thirdapifeedback.createIndex(keys);
		this.thirdapifeedback = thirdapifeedback;
	}

	public void setTopfeedback(DBCollection topfeedback) {
		this.topfeedback = topfeedback;
	}
}
