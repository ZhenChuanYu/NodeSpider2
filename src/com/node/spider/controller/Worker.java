package com.node.spider.controller;

import com.node.spider.fetch.Fetcher;
import com.node.spider.fetch.UrlConnectionFetcher;
import com.node.spider.pubclass.Link;

class Worker extends Thread {

	TaskQueue taskQueue;
	Fetcher fetcher;
	boolean running = true;
	boolean isBusy = false;
	final long SLEEPWHILE = 100l;// 100ms

	public Worker(TaskQueue taskQueue) {
		this(taskQueue, null);
	}

	public Worker(TaskQueue taskQueue, Fetcher fetcher) {
		this.taskQueue = taskQueue;
		if (fetcher == null) {
			this.fetcher = new UrlConnectionFetcher();
		} else {
			this.fetcher = fetcher;
		}
	}

	public void stopSilent() {
		running = false;
	}

	public void shutdown() {
		this.stop();
	}

	@Override
	public void run() {
		while (running) {
			Link task = taskQueue.poll();
			if (task == null) {
				sleepForWhile();
				continue;
			} else {
				isBusy = true;
				fetcher.fetch(task);
				isBusy = false;
			}
		}
	}

	private void sleepForWhile() {
		try {
			sleep(SLEEPWHILE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
