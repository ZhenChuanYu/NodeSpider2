package com.node.spider.pubclass;

import java.util.List;
import java.util.Map;

public class ParserTask {

	public Link link;// 解析的网页链接
	public byte[] fetchResult;// 需要解析的网页结果
	Map<String, List<String>> responseHeader;// 需要解析的网页头信息

	private ParserTask(Link link, byte[] fetchResult,
			Map<String, List<String>> responseHeader) {
		super();
		this.link = link;
		this.fetchResult = fetchResult;
		this.responseHeader = responseHeader;
	}

	public static ParserTask newParserTask(Link link, byte[] fetchResult,
			Map<String, List<String>> responseHeader) {
		return new ParserTask(link, fetchResult, responseHeader);
	}
}
