package com.node.spider.pubclass;

import java.util.List;

/**
 * 考虑不使用website，暂时可由{@link Link}取代
 * 
 * @author zhenchuan
 * 
 */
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
