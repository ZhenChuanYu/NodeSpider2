package com.node.spider.pubclass;

import java.util.List;

/**
 * 需要爬取的url链接
 * 
 * @author zhenchuan
 * 
 */
public class Link {

	public Link(String url) {
		this.url = url;
	}

	/**
	 * 父节点
	 */
	public Link parent;
	/**
	 * 所有子节点
	 */
	public List<Link> subLinks;
	/**
	 * 节点url
	 */
	public String url;
	/**
	 * 优先级
	 */
	public int priority;
	/**
	 * http头信息
	 */
	public LinkHeaderConfig headerConfig;

	/**
	 * 用于设置请求是否走缓存<br>
	 * {@link UrlConnectionFetcher#fetch(Link)}
	 */
	public boolean usecache;
	/**
	 * 用于设置http请求的方法GET or POST<br>
	 * {@link UrlConnectionFetcher#fetch(Link)}
	 */
	public String requestMethod;

	/**
	 * 用于设置请求超时 <br>
	 * {@link UrlConnectionFetcher#fetch(Link)}
	 */
	public long requestTimeOut;

	/**
	 * 用于设置读取超时<br>
	 * {@link UrlConnectionFetcher#fetch(Link)}
	 */
	public long readTimeout;

	/**
	 * 设置重试次数<br>
	 * {@link UrlConnectionFetcher#fetch(Link)}
	 */
	public int retryCount;

	/**
	 * 当前重试次数
	 */
	public int currentRetryTime;

	public Link setDefaultConfig() {
		priority = 10000;
		headerConfig = new LinkHeaderConfigDefault();
		usecache = false;
		requestMethod = "GET";
		requestTimeOut = 60 * 1000l;
		readTimeout = 60 * 1000l;
		retryCount = 3;
		currentRetryTime = 0;
		return this;
	}

	public Link addCookie(String cookie) {
		if (headerConfig == null) {
			headerConfig = new LinkHeaderConfigDefault();
		}
		headerConfig.addNameValue(new NameValuePair<String, String>(
				HttpHeader.COOKIE, cookie));
		return this;
	}

	public String getCookie() {
		if (headerConfig == null) {
			return null;
		} else {
			return headerConfig.getHeaderValueOfName(HttpHeader.COOKIE);
		}
	}
}
