package com.node.spider.main;

import com.node.spider.controller.Dispatcher;
import com.node.spider.fetch.Fetcher;
import com.node.spider.fetch.UrlConnectionFetcher;
import com.node.spider.pubclass.Link;
import com.node.spider.pubclass.Website;
import com.node.spider.pubutil.ConfigLoader;
import com.node.spider.pubutil.Log;

public class SpiderApplication implements Application {

	private final String NAME = "NodeSpider";
	Dispatcher dispater;
	String[] link = { "http://www.baidu.com", "http://www.sina.com.cn/",
			"http://http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com", "http://www.baidu.com",
			"http://www.sina.com.cn/", "http://http://www.dianping.com/",
			"http://bj.58.com/", "http://open.163.com/",
			"http://www.iteye.com/", "http://tencent.com/zh-cn/index.shtml",
			"http://www.appchina.com", "http://www.wandoujia.com",
			"http://www.baidu.com", "http://www.sina.com.cn/",
			"http://http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com", "http://www.baidu.com",
			"http://www.sina.com.cn/", "http://http://www.dianping.com/",
			"http://bj.58.com/", "http://open.163.com/",
			"http://www.iteye.com/", "http://tencent.com/zh-cn/index.shtml",
			"http://www.appchina.com", "http://www.wandoujia.com",
			"http://www.baidu.com", "http://www.sina.com.cn/",
			"http://http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com", "http://www.baidu.com",
			"http://www.sina.com.cn/", "http://http://www.dianping.com/",
			"http://bj.58.com/", "http://open.163.com/",
			"http://www.iteye.com/", "http://tencent.com/zh-cn/index.shtml",
			"http://www.appchina.com", "http://www.wandoujia.com",
			"http://www.baidu.com", "http://www.sina.com.cn/",
			"http://http://www.dianping.com/", "http://bj.58.com/",
			"http://open.163.com/", "http://www.iteye.com/",
			"http://tencent.com/zh-cn/index.shtml", "http://www.appchina.com",
			"http://www.wandoujia.com"

	};

	@Override
	public void onInit() {
		Log.init(SpiderApplication.class);
		Log.i(NAME + " inited" + "\n");
	}

	@Override
	public void onCreate() {
		Log.i(NAME + " created" + "\n");
		dispater = new Dispatcher();
		for (String url : link) {
			Link l = new Link(url);
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

}
