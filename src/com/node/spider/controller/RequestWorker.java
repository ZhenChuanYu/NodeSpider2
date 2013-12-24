package com.node.spider.controller;

import com.node.spider.fetch.Fetcher;
import com.node.spider.pubclass.Link;

class RequestWorker extends Thread {

	RequestTaskQueue taskQueue;
	Fetcher fetcher;
	boolean running = true;
	boolean isBusy = false;
	final long SLEEPWHILE = 100l;// 100ms

	public RequestWorker(RequestTaskQueue taskQueue) {
		this(taskQueue, null);
	}

	public RequestWorker(RequestTaskQueue taskQueue, Fetcher fetcher) {
		this.taskQueue = taskQueue;
		if (fetcher == null) {
			this.fetcher = Fetcher.newFetcher(Fetcher.Type.UrlConnection);
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
