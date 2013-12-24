package com.node.spider.parser;

import com.node.spider.pubclass.ParserTask;

public abstract class Parser {

	public Parser() {
		
	}

	public abstract void parse(ParserTask parserTask);

	public static enum Type {
		ImageParser("image"), LinkParser("urlLink");
		private String value;

		private Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}

	public static Parser newParser(Type type) {
		switch (type) {
		case ImageParser:
			return new UrlLinkParser();
		case LinkParser:
			return new ImageResourceParser();
		default:
			return new UrlLinkParser();
		}
	}
}
