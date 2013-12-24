package com.node.spider.controller;

import com.node.spider.pubclass.Link;

public class ParserQueue implements RequestTaskQueue {

	@Override
	public Link poll() {
		return null;
	}

	@Override
	public void offer(Link l) {

	}

}
