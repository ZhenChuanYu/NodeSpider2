package com.node.spider.parser;

import com.node.spider.pubclass.ParserTask;

public abstract class Parser implements Cloneable {

	protected ParserCallback callback;

	public Parser() {

	}

	public abstract void parse(ParserTask parserTask);

	public static enum Type {
		DefaultParser("default"), ImageParser("image"), LinkParser("urlLink");
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

	public void setParserCallback(ParserCallback callback) {
		this.callback = callback;
	}

	@Override
	public abstract Parser clone() throws CloneNotSupportedException;
}
