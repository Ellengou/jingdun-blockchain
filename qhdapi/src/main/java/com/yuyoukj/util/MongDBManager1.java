package com.yuyoukj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongDBManager1 {
	final static Log logger = LogFactory.getLog(MongDBManager1.class);

	// private int closeBDB;
	private static Mongo mg;

	private static Map<String, MongDBUtil> mgdbs = new HashMap<String, MongDBUtil>();

	public static String getDBkey() {
		return DataUtil.getRandomDigit(100) + "";
		// return Math.abs(key%100)+"";
	}

	public static Mongo getMg() {
		return mg;
	}

	public static Map<String, MongDBUtil> getMgdbs() {
		return mgdbs;
	}

	public static void setMg(Mongo mg) {
		MongDBManager1.mg = mg;
	}

	public static void setMgdbs(Map<String, MongDBUtil> mgdbs) {
		MongDBManager1.mgdbs = mgdbs;
	}

	private String osmads;

	public MongDBManager1(String ip, Integer port, String osmads) {
		try {
			this.osmads = osmads;
			MongoOptions options = new MongoOptions();
			options.autoConnectRetry = true;
			options.socketKeepAlive = true;
			options.connectionsPerHost = 1000;
			options.maxWaitTime = 5000;
			options.socketTimeout = 0;
			options.connectTimeout = 15000;
			options.threadsAllowedToBlockForConnectionMultiplier = 5000;
			mg = new Mongo(new ServerAddress(ip, port), options);
			// retdb = mg.getDB(osmdb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DBCollection getLogfeedback() throws Exception {
		return getLogfeedback("0");
	}

	public DBCollection getLogfeedback(String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		DBCollection logfeedback = null;
		if (null != mgdb) {
			logfeedback = mgdb.getLogfeedback();
		}
		return logfeedback;
	}

	public DBCollection getLogpv() throws Exception {
		return getLogpv("0");
	}

	public DBCollection getLogpv(String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		DBCollection logpv = null;
		if (null != mgdb) {
			logpv = mgdb.getLogpv();
		}
		return logpv;
	}

	public DBCollection getLogWallfeedback(String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		DBCollection logwallfeedback = null;
		if (null != mgdb) {
			logwallfeedback = mgdb.getLogwallfeedback();
		}
		return logwallfeedback;
	}

	public MongDBUtil getMgdb(String key) throws Exception {
		MongDBUtil mgdb = null;
		if (null != mgdbs) {
			mgdb = mgdbs.get(key);
			if (null == mgdb) {
				mgdb = new MongDBUtil(mg, osmads + key);
				mgdbs.put(key, mgdb);
			}
		}
		return mgdb;
	}

	public List<Object> getObject(DBCollection collection, Class<?> inputObj, BasicDBObject queryObject) throws Exception {
		return getObject(collection, inputObj, queryObject, "0");
	}

	public List<Object> getObject(DBCollection collection, Class<?> inputObj, BasicDBObject queryObject, String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		List<Object> objectList = new ArrayList<Object>();
		if (null != mgdb) {
			objectList = mgdb.getObject(collection, inputObj, queryObject);
		}
		return objectList;
	}

	public String getOsmads() {
		return osmads;
	}

	public DBCollection getThirdapifeedback() throws Exception {
		return getThirdapifeedback("0");
	}

	public DBCollection getThirdapifeedback(String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		DBCollection thirdapifeedback = null;
		if (null != mgdb) {
			thirdapifeedback = mgdb.getThirdapifeedback();
		}
		return thirdapifeedback;
	}

	public DBCollection getTopfeedback() throws Exception {
		return getTopfeedback("0");
	}

	public DBCollection getTopfeedback(String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		DBCollection topfeedback = null;
		if (null != mgdb) {
			topfeedback = mgdb.getTopfeedback();
		}
		return topfeedback;
	}

	public void saveObject(DBCollection collection, Object inputObj) throws Exception {
		saveObject(collection, inputObj, "0");
	}

	public void saveObject(DBCollection collection, Object inputObj, String dbkey) throws Exception {
		MongDBUtil mgdb = getMgdb(dbkey);
		if (null != mgdb) {
			mgdb.saveObject(collection, inputObj);
		}
	}

	public void setOsmads(String osmads) {
		this.osmads = osmads;
	}
}
