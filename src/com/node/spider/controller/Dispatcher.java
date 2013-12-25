package com.node.spider.controller;

import java.util.List;
import java.util.Map;

import com.node.spider.fetch.FetchCallback;
import com.node.spider.fetch.Fetcher;
import com.node.spider.fetch.Fetcher.Type;
import com.node.spider.pubclass.Link;
import com.node.spider.pubclass.ParserTask;
import com.node.spider.pubutil.Log;

/**
 * 爬虫任务分发器，负责分发request 任务与parser 任务<br>
 * 1.fetcher负责处理link请求{@link Link}{@link Fetcher}<br>
 * RequestWorker代理fetcher处理并发任务 {@link RequestWorker} <br>
 * 2.parser负责处理fetcher到的页面结果(咱未完成)
 * 
 * @author zhenchuan
 * 
 */
public class Dispatcher implements RequestTaskQueue, ParserTaskQueue {

	/*
	 * fetch部分
	 */
	RequestWorkersConfig rConfig;
	RequestWorker[] rWorkers;
	RequestTaskQueue requestQueue;
	final static int DEFAULT_REQUEST_WORKER_NUM = 5;// 默认为5个线程

	/*
	 * parser部分
	 */
	ParserWorkersConfig pConfig;
	ParserWorker[] pWorkers;
	ParserTaskQueue parserQueue;
	final static int DEFAULT_PARSER_WORKER_NUM = 3;// 默认为3个线程

	public Dispatcher setRequestWorkersConfig(RequestWorkersConfig config) {
		this.rConfig = config;
		return this;
	}

	public Dispatcher setRequestTaskQueue(RequestTaskQueue queue) {
		this.requestQueue = queue;
		return this;
	}

	public Dispatcher setParserWorkersConfig(ParserWorkersConfig config) {
		this.pConfig = config;
		return this;
	}

	public Dispatcher setParserTaskQueue(ParserTaskQueue queue) {
		this.parserQueue = queue;
		return this;
	}

	public Dispatcher(RequestWorkersConfig rConfig, ParserWorkersConfig pConfig) {
		setRequestWorkersConfig(rConfig).setParserWorkersConfig(pConfig);
	}

	public Dispatcher() {
		this(new RequestWorkersConfig(DEFAULT_REQUEST_WORKER_NUM),
				new ParserWorkersConfig(DEFAULT_PARSER_WORKER_NUM));
	}

	private void initRequestWorkers() {
		if (rWorkers == null) {
			rWorkers = new RequestWorker[rConfig.workerNum];
			for (int i = 0; i < rConfig.workerNum; i++) {
				rWorkers[i] = newRequestWorker();
				rWorkers[i].start();
			}
		}
	}

	private RequestWorker newRequestWorker() {
		return new RequestWorker(requestQueue, newFetcher());
	}

	private Fetcher newFetcher() {
		Fetcher fetcher = Fetcher.newFetcher(Fetcher.Type.UrlConnection);
		fetcher.setCallbask(new FetchCallback() {
			@Override
			public void onTimeOut(Type fetcherType, Link link) {
				Log.i("fetch timeout!!! url:" + link.url);
				Log.i("fetcher type is " + fetcherType);
				Log.i("throw link to requestqueue ");
				if (++link.currentRetryTime <= link.retryCount) {
					Log.i("now retry..." + link.currentRetryTime + "");
					offer(link);
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
					offer(link);
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
				offer(ParserTask.newParserTask(link, result, responseHeader));
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
		});
		return fetcher;
	}

	public void stopAllRequestWorkers() {
		if (rWorkers == null) {
			return;
		}
		for (RequestWorker worker : rWorkers) {
			worker.running = false;
		}
	}

	public void shutdownAllRequestWorkers() {
		if (rWorkers == null) {
			return;
		}
		for (RequestWorker worker : rWorkers) {
			worker.shutdown();
		}
	}

	@Override
	public Link poll() {
		return null;
	}

	@Override
	public void offer(Link l) {
		initRequestWorkers();
		requestQueue.offer(l);
	}

	@Override
	public ParserTask pollParserTask() {
		return null;
	}

	@Override
	public void offer(ParserTask task) {

	}
}
