package com.node.spider.parser;

import com.node.spider.pubclass.ParserTask;

public class DefaultParser extends Parser {

	@Override
	public void parse(ParserTask parserTask) {
		
	}

	@Override
	public Parser clone() throws CloneNotSupportedException {
		Parser p = new DefaultParser();
		p.setParserCallback(this.callback.clone());
		return p;
	}

}
