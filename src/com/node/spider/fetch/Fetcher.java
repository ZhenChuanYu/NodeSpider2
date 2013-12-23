package com.node.spider.fetch;

import java.net.MalformedURLException;
import java.net.URL;

import com.node.spider.cookie.CookieManager;
import com.node.spider.pubclass.Link;

public abstract class Fetcher {

	protected FetchCallback callback;

	public Fetcher() {
	}

	public abstract void fetch(Link l);

	public static enum Type {
		UrlConnection("urlconnection-client"), ApacheHttpClient(
				"apachehttp-client");
		String value;

		private Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static Fetcher newFetcher(Fetcher.Type type) {
		Fetcher fetcher;
		switch (type) {
		case UrlConnection:
			fetcher = new UrlConnectionFetcher();
			break;
		case ApacheHttpClient:
			fetcher = new HttpClientFetcher();
			break;
		default:
			fetcher = new UrlConnectionFetcher();
		}
		return fetcher;
	}

	public Fetcher setCallbask(FetchCallback callback) {
		this.callback = callback;
		return this;
	}

	public void addCookie(Link link) throws MalformedURLException {
		link.addCookie(CookieManager.getInstance().getUrlCookie(
				new URL(link.url).getHost()));
	}
}
