package com.node.spider.cookie;

import java.util.Map;

import com.node.spider.pubutil.ConfigLoader;
import com.node.spider.pubutil.Log;

/**
 * 管理url对应的cookie
 * 
 * @author zhenchuan
 * 
 */
public class CookieManager {

	private static CookieManager instance;
	private static Map<String, String> cookieMap;
	private final static String cookieCfgPath = "./cookiesettings.cfg";

	public void initLocalConfig() {
		Log.i("loading cookie...");
		cookieMap = ConfigLoader.getInstance()
				.loadKeyValueConfig(cookieCfgPath);
		if (cookieMap.size() > 0) {
			for (String key : cookieMap.keySet()) {
				Log.i("DOMAIN:" + key + "**" + "COOKIE:" + cookieMap.get(key)
						+ "  loaded" + "\n");
			}
		} else {
			Log.i("No cookie loaded");
		}

	}

	private CookieManager() {
	};

	public static CookieManager getInstance() {
		if (instance == null) {
			instance = new CookieManager();
		}
		return instance;
	}

	public synchronized void addUrlCookie(String domain, String cookies) {
		if (cookieMap.containsKey(domain)) {
			cookieMap.put(domain, cookieMap.get(domain) + cookies);
		} else {
			cookieMap.put(domain, cookies);
		}
	}

	public synchronized String getUrlCookie(String domain) {
		return cookieMap.containsKey(domain) ? cookieMap.get(domain) : "";
	}
}
