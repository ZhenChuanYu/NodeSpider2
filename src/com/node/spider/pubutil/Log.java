package com.node.spider.pubutil;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log {

	private Log() {
	}

	private static Logger logger;

	public static void init(Class<?> c) {
		logger = Logger.getLogger(c);
		BasicConfigurator.configure();
	}

	public static void e(String msg) {
		logger.error(msg);
	}

	public static void i(String msg) {
		logger.info(msg);
	}

	public static void w(String msg) {
		logger.warn(msg);
	}
}
