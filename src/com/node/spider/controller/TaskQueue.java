package com.node.spider.controller;

import com.node.spider.pubclass.Link;

public interface TaskQueue {

	Link poll();

	void offer(Link l);

}
