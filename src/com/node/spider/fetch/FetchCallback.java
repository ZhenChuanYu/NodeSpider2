package com.node.spider.fetch;

import java.util.List;
import java.util.Map;

import com.node.spider.pubclass.Link;

public interface FetchCallback extends Cloneable{
	// 在请求发出之前
	void onBeforeFetch(Fetcher.Type fetcherType, Link link,
			Map<String, List<String>> requestHeader);

	// 抓取成功
	void onFetchSuccess(Fetcher.Type fetcherType, Link link, byte[] result,
			Map<String, List<String>> responseHeader);

	// 抓取失败--网络返回错误404,500...
	void onFetchError(Fetcher.Type fetcherType, Link link, int responseCode,
			Map<String, List<String>> responseHeader);

	// 抓取失败--网络超时
	void onTimeOut(Fetcher.Type fetcherType, Link link);

	// 抓取失败 -- 网络IO异常（无网络？）
	void onNetWorkIOError(Fetcher.Type fetcherType, Link link);
	
	FetchCallback clone() throws CloneNotSupportedException;
}
