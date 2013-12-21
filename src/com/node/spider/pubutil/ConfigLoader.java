package com.node.spider.pubutil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {

	private static final String TAG = ConfigLoader.class.getName();
	private static ConfigLoader loader;

	private ConfigLoader() {

	}

	public static ConfigLoader getInstance() {
		if (loader == null) {
			loader = new ConfigLoader();
		}
		return loader;
	}

	public <T> T loadConfig(Class<T> cl,String configPath) {
		try {
			T config = cl.newInstance();
			Map<String, String> map = FileUtil
					.readKeyValueConfigFile(configPath);
			if (map == null) {
				Log.e(TAG + ":" + " read configfile error filepath is "
						+ configPath == null ? "null" : configPath);
				return null;
			}
			for (String key : map.keySet()) {
				invokeGetMethod(config, cl, key, map.get(key));
			}
			return config;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private <T> void invokeGetMethod(T instance, Class<T> cl,
			String domainName, String value) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		String first = new String(new char[] { domainName.charAt(0) });
		String other = domainName.substring(1, domainName.length());
		Method m = cl.getDeclaredMethod("set" + first.toUpperCase() + other, String.class);
		m.invoke(instance, value);
	}
}
