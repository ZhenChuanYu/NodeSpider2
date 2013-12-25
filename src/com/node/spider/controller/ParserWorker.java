package com.node.spider.controller;

import com.node.spider.parser.Parser;
import com.node.spider.pubclass.ParserTask;

public class ParserWorker extends Thread {

	ParserTaskQueue taskQueue;
	Parser parser;
	boolean running = true;
	boolean isBusy = false;
	final long SLEEPWHILE = 100l;// 100ms

	public ParserWorker(ParserTaskQueue taskQueue) {
		this(taskQueue, null);
	}

	public ParserWorker(ParserTaskQueue taskQueue, Parser parser) {
		this.taskQueue = taskQueue;
		if (parser == null) {
			this.parser = Parser.newParser(Parser.Type.DefaultParser);
		} else {
			this.parser = parser;
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
			ParserTask task = taskQueue.pollParserTask();
			if (task == null) {
				sleepForWhile();
				continue;
			} else {
				isBusy = true;
				parser.parse(task);
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
