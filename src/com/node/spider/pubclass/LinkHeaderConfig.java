package com.node.spider.pubclass;

import java.util.ArrayList;

import org.apache.http.HttpHeaders;

import com.node.spider.pubclass.NameValuePair;

public abstract class LinkHeaderConfig {

	public ArrayList<NameValuePair<String, String>> headers = new ArrayList<NameValuePair<String, String>>();

	public LinkHeaderConfig() {
	};

	public LinkHeaderConfig addNameValue(NameValuePair<String, String> namevalue) {
		headers.add(namevalue);
		return this;
	}
}
