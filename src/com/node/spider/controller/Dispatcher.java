package com.node.spider.controller;

import java.util.PriorityQueue;

import com.node.spider.pubclass.Link;

public class Dispatcher implements TaskQueue {

	ControllerConfig config;
	Worker[] workers;
	TaskQueue workQueue;
	final static int DEFAULT_WORKER_NUM = 3;// 默认为3个线程

	public Dispatcher setConfig(ControllerConfig config) {
		this.config = config;
		return this;
	}

	public Dispatcher setTaskQueue(TaskQueue queue) {
		this.workQueue = queue;
		return this;
	}

	public Dispatcher(ControllerConfig config) {
		setConfig(config);
		workQueue = new TaskQueuePriority();
	}

	public Dispatcher() {
		this(new ControllerConfig(DEFAULT_WORKER_NUM));
	}

	private void initWorkers() {
		if (workers == null) {
			workers = new Worker[config.workerNum];
			for (int i = 0; i < config.workerNum; i++) {
				workers[i] = new Worker(workQueue);
				workers[i].start();
			}
		}
	}

	@Override
	public Link poll() {
		return null;
	}

	@Override
	public void offer(Link l) {
		initWorkers();
		workQueue.offer(l);
	}

}
