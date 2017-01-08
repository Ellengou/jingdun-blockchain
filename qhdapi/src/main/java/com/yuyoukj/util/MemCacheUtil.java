package com.yuyoukj.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.danga.MemCached.SockIOPool.SockIO;

public class MemCacheUtil {
	public static void main(String[] args) {
		String[] servers = new String[] { "192.168.50.130:11211" };
		Integer[] weights = new Integer[] { 5 };
		MemCacheUtil memcache = new MemCacheUtil(servers, weights);
		System.out.println();
	}

	private MemCachedClient mcc = new MemCachedClient(true);
	private boolean connected;
	private String[] servers;
	private Map chashMap = new HashMap();

	/**
	 * 保护型构造方法，不允许实例化！ 设置与缓存服务器的连接池
	 */
	public MemCacheUtil(String[] servers, Integer[] weights) {
		try {
			// 服务器列表和其权重
			// 获取socke连接池的实例对象
			SockIOPool pool = SockIOPool.getInstance();
			// SchoonerSockIOPool pool = SchoonerSockIOPool.getInstance();
			// 设置服务器信息
			pool.setServers(servers);
			pool.setWeights(weights);
			// 设置初始连接数、最小和最大连接数以及最大处理时间
			pool.setInitConn(5);
			pool.setMinConn(5);
			pool.setMaxConn(250);
			pool.setMaxIdle(1000 * 60 * 60 * 6);
			// 设置主线程的睡眠时间
			pool.setMaintSleep(3000);
			// 设置TCP的参数，连接超时等
			pool.setNagle(false);
			pool.setSocketTO(100);// Socket阻塞读取数据的超时时间
			pool.setSocketConnectTO(0);// Socket阻塞建立连接的等待时间
			// pool.setSocketTO(1000);// Socket阻塞读取数据的超时时间
			// pool.setSocketConnectTO(1000);// Socket阻塞建立连接的等待时间
			// 初始化连接池
			pool.initialize();
			SockIO sock = pool.getSock(servers[0]);
			if (sock != null) {
				connected = true;
			}
			this.servers = servers;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean add(String key, Object value) {
		return mcc.add(key, value);
	}

	public boolean add(String key, Object value, boolean flag) {
		if (value != null) {
			if (flag) {
				return mcc.add(key, JSON.toJSONString(value));
			} else {
				return mcc.add(key, value);
			}
		}
		return false;
	}

	public boolean add(String key, Object value, Date expiry) {
		return mcc.add(key, value, expiry);
	}

	public boolean add(String key, Object value, Date expiry, boolean flag) {
		if (value != null) {
			if (flag) {
				return mcc.add(key, JSON.toJSONString(value), expiry);
			} else {
				return mcc.add(key, value, expiry);
			}
		}
		return false;
	}

	public boolean delete(String key) {
		return mcc.delete(key);
	}

	public boolean delete(String key, Date expiry) {
		return mcc.delete(key, expiry);
	}

	public Object get(String key) {
		return mcc.get(key);
	}

	public <T> T get(String key, Class<T> clazz) {
		if (mcc.get(key) != null) {
			return JSON.parseObject((String) mcc.get(key), clazz);
		} else {
			return null;
		}
	}

	public <T> List<T> get(String key, Class<T> clazz, boolean flag) {
		if (mcc.get(key) != null && flag) {
			return JSON.parseArray((String) mcc.get(key), clazz);
		} else {
			return null;
		}
	}

	public Map getChashMap() {
		return chashMap;
	}

	public String[] getServers() {
		return servers;
	}

	public boolean isConnected() {
		return connected;
	}

	public boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}

	public boolean replace(String key, Object value, Date expiry) {
		return mcc.replace(key, value, expiry);
	}

	public boolean set(String key, Object value) {
		if (null != value) {
			return mcc.set(key, value);
		}
		return false;
	}

	public boolean set(String key, Object value, Date expiry) {
		if (null != value) {
			return mcc.set(key, value, expiry);
		}
		return false;
	}

	public boolean set(String key, Object value, Date expiry, boolean flag) {
		if (value != null) {
			if (flag) {
				return mcc.add(key, JSON.toJSONString(value), expiry);
			} else {
				return mcc.add(key, value, expiry);
			}
		}
		return false;
	}

	public void setChashMap(Map chashMap) {
		this.chashMap = chashMap;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}
}
