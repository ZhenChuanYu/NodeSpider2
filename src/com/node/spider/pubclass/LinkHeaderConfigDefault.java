package com.node.spider.pubclass;

import com.node.spider.pubclass.HttpHeader;
import com.node.spider.pubclass.NameValuePair;

public class LinkHeaderConfigDefault extends LinkHeaderConfig {
	String DEFAULT_ACCEPT = "*/*";
	String DEFAULT_ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";
	String DEFAULT_ACCEPT_ENCODING = "gzip,deflate,sdch";
	String DEFAULT_CONNECTION = "keep-alive";
	String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36";

	public LinkHeaderConfigDefault() {
		super();
		addNameValue(
				new NameValuePair<String, String>(HttpHeader.ACCEPT,
						DEFAULT_ACCEPT))
				.addNameValue(
						new NameValuePair<String, String>(
								HttpHeader.ACCEPT_LANGUAGE,
								DEFAULT_ACCEPT_LANGUAGE))
				.addNameValue(
						new NameValuePair<String, String>(
								HttpHeader.ACCEPT_ENCODING,
								DEFAULT_ACCEPT_ENCODING))
				.addNameValue(
						new NameValuePair<String, String>(
								HttpHeader.CONNECTION, DEFAULT_CONNECTION))
				.addNameValue(
						new NameValuePair<String, String>(
								HttpHeader.USER_AGENT, DEFAULT_USER_AGENT));
	}
}
