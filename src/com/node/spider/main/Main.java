package com.node.spider.main;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t = Thread.currentThread();
		final Application app = new SpiderApplication();
		app.onInit();
		app.onCreate();
		app.onStart();
//		app.onDestroy();
	}

}
