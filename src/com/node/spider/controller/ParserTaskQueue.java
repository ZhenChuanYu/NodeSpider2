package com.node.spider.controller;

import com.node.spider.pubclass.ParserTask;

public interface ParserTaskQueue {

	ParserTask pollParserTask();

	void offer(ParserTask task);

}
