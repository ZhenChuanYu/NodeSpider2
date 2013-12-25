package com.node.spider.controller;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.node.spider.pubclass.ParserTask;

public class ParserQueuePriority implements ParserTaskQueue {

	public ParserQueuePriority() {
	}

	PriorityQueue<ParserTask> taskQueue;
	{
		taskQueue = new PriorityQueue<ParserTask>(1000,
				new PriorityComparator());
	}

	class PriorityComparator implements Comparator<ParserTask> {
		@Override
		public int compare(ParserTask l, ParserTask r) {
			return l.link.priority - r.link.priority;
		}
	}

	@Override
	public ParserTask pollParserTask() {
		return taskQueue.poll();
	}

	@Override
	public void offer(ParserTask task) {
		taskQueue.offer(task);
	}

}
