package com.node.spider.main;

import java.util.List;
import java.util.Map;

import com.node.spider.controller.Dispatcher;
import com.node.spider.cookie.CookieManager;
import com.node.spider.fetch.FetchCallback;
import com.node.spider.fetch.Fetcher;
import com.node.spider.fetch.Fetcher.Type;
import com.node.spider.parser.Parser;
import com.node.spider.parser.ParserCallback;
import com.node.spider.pubclass.Link;
import com.node.spider.pubclass.ParserTask;
import com.node.spider.pubutil.Log;

public class SpiderApplication implements Application {

	private final String NAME = "NodeSpider";
	Dispatcher dispater;
	String[] link = { "http://www.baidu.com", "http://www.sina.com.cn/",
			"http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com", "http://www.baidu.com",
			"http://www.sina.com.cn/", "http://www.dianping.com/",
			"http://bj.58.com/", "http://open.163.com/",
			"http://www.iteye.com/", "http://tencent.com/zh-cn/index.shtml",
			"http://www.appchina.com", "http://www.wandoujia.com",
			"http://www.baidu.com", "http://www.sina.com.cn/",
			"http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com", "http://www.baidu.com",
			"http://www.sina.com.cn/", "http://www.dianping.com/",
			"http://bj.58.com/", "http://open.163.com/",
			"http://www.iteye.com/", "http://tencent.com/zh-cn/index.shtml",
			"http://www.appchina.com", "http://www.wandoujia.com",
			"http://www.baidu.com", "http://www.sina.com.cn/",
			"http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com", "http://www.baidu.com",
			"http://www.sina.com.cn/", "http://www.dianping.com/",
			"http://bj.58.com/", "http://open.163.com/",
			"http://www.iteye.com/", "http://tencent.com/zh-cn/index.shtml",
			"http://www.appchina.com", "http://www.wandoujia.com",
			"http://www.baidu.com", "http://www.sina.com.cn/",
			"http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com",
			"http://weibo.com/1807122290/profile?topnav=1&wvr=5&user=1" };

	@Override
	public void onInit() {
		Log.init(SpiderApplication.class);
		CookieManager.getInstance().initLocalConfig();
		Log.i(NAME + " inited" + "\n");
	}

	@Override
	public void onCreate() {
		Log.i(NAME + " created" + "\n");
		// dispater = new Dispatcher();
		Fetcher fetcher = Fetcher.newFetcher(Fetcher.Type.UrlConnection);
		fetcher.setCallbask(newFetcherCallback());

		Parser parser = Parser.newParser(Parser.Type.DefaultParser);
		parser.setParserCallback(newParserCallback());

		// use default config and taskqueue
		dispater = new Dispatcher(fetcher, parser);
		for (String url : link) {
			Link l = new Link(url).setDefaultConfig();
			dispater.offer(l);
		}
	}

	@Override
	public void onStart() {
		Log.i(NAME + " starting..." + "\n");

	}

	@Override
	public void onDestroy() {
		Log.w(NAME + " destroyed" + "\n");
	}

	FetchCallback newFetcherCallback() {
		FetchCallback callback = new FetchCallback() {
			@Override
			public void onTimeOut(Type fetcherType, Link link) {
				Log.i("fetch timeout!!! url:" + link.url);
				Log.i("fetcher type is " + fetcherType);
				Log.i("throw link to requestqueue ");
				if (++link.currentRetryTime <= link.retryCount) {
					Log.i("now retry..." + link.currentRetryTime + "");
					dispater.offer(link);
				} else {
					Log.i("reach max retry time ,throw it");
				}
			}

			@Override
			public void onNetWorkIOError(Type fetcherType, Link link) {
				Log.i("network io error!!! url:" + link.url);
				Log.i("fetcher type is " + fetcherType);
				Log.i("throw link to requestqueue ");
				if (++link.currentRetryTime <= link.retryCount) {
					Log.i("now retry..." + link.currentRetryTime + "");
					dispater.offer(link);
				} else {
					Log.i("reach max retry time ,throw it");
				}
			}

			@Override
			public void onFetchSuccess(Type fetcherType, Link link,
					byte[] result, Map<String, List<String>> responseHeader) {
				Log.i("fetch success!!! url:" + link.url + " ....");
				Log.i("fetcher type is " + fetcherType);
				Log.i("response header is " + responseHeader.toString());
				Log.i("throw link to parser queue");
				// add link to parsertaskqueue
				dispater.offer(ParserTask.newParserTask(link, result,
						responseHeader));
			}

			@Override
			public void onFetchError(Type fetcherType, Link link,
					int responseCode, Map<String, List<String>> responseHeader) {
				Log.i("fetch error responseCode is  " + responseCode + " url:"
						+ link.url + " ....");
				Log.i("fetcher type is " + fetcherType);
				Log.i("response header is " + responseHeader.toString());
			}

			@Override
			public void onBeforeFetch(Type fetcherType, Link link,
					Map<String, List<String>> requestHeader) {
				Log.i("begin to fetch " + link.url + " ....");
				Log.i("fetcher type is " + fetcherType);
				Log.i("request header is " + requestHeader.toString());
			}

			@Override
			public FetchCallback clone() throws CloneNotSupportedException {
				return newFetcherCallback();
			}
		};
		return callback;
	}

	private ParserCallback newParserCallback() {
		ParserCallback callback = new ParserCallback() {

			@Override
			public ParserCallback clone() throws CloneNotSupportedException {
				// TODO Auto-generated method stub
				return newParserCallback();
			}
		};
		return callback;
	}

}
