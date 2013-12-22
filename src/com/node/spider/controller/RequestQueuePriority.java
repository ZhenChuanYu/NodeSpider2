package com.node.spider.controller;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.node.spider.pubclass.Link;

public class RequestQueuePriority implements TaskQueue {

	public RequestQueuePriority() {
	};

	PriorityQueue<Link> taskQueue;
	{
		taskQueue = new PriorityQueue<Link>(1000, new PriorityComparator());
	}

	class PriorityComparator implements Comparator<Link> {
		@Override
		public int compare(Link llink, Link rlink) {
			return llink.priority - rlink.priority;
		}
	}

	@Override
	public synchronized Link poll() {
		return taskQueue.poll();
	}

	@Override
	public synchronized void offer(Link l) {
		taskQueue.offer(l);
	}

}
