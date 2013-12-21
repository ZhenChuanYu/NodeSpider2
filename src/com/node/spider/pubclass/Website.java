package com.node.spider.pubclass;

import java.util.List;

public class Website {
	public String url;
	public String useragent;

	public List<Link> links;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

}
