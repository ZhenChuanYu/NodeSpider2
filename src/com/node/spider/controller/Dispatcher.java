package com.node.spider.controller;

import java.util.List;
import java.util.Map;

import com.node.spider.fetch.FetchCallback;
import com.node.spider.fetch.Fetcher;
import com.node.spider.fetch.Fetcher.Type;
import com.node.spider.parser.Parser;
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
	RequestWorkersConfig rWorkersConfig;
	RequestWorker[] rWorkers;
	RequestTaskQueue requestQueue;
	final static int DEFAULT_REQUEST_WORKER_NUM = 5;// 默认为5个线程
	Fetcher tFetcher;

	/*
	 * parser部分
	 */
	ParserWorkersConfig pWorkersConfig;
	ParserWorker[] pWorkers;
	ParserTaskQueue parserQueue;
	final static int DEFAULT_PARSER_WORKER_NUM = 3;// 默认为3个线程
	Parser tParser;

	public Dispatcher setRequestWorkersConfig(RequestWorkersConfig config) {
		this.rWorkersConfig = config;
		return this;
	}

	public Dispatcher setTemplateFetcher(Fetcher fetcher) {
		this.tFetcher = fetcher;
		return this;
	}

	public Dispatcher setTemplateParser(Parser parser) {
		this.tParser = parser;
		return this;
	}

	public Dispatcher setRequestTaskQueue(RequestTaskQueue queue) {
		this.requestQueue = queue;
		return this;
	}

	public Dispatcher setParserWorkersConfig(ParserWorkersConfig config) {
		this.pWorkersConfig = config;
		return this;
	}

	public Dispatcher setParserTaskQueue(ParserTaskQueue queue) {
		this.parserQueue = queue;
		return this;
	}

	public Dispatcher(RequestWorkersConfig rConfig,
			ParserWorkersConfig pConfig, ParserTaskQueue pQueue,
			RequestTaskQueue rQueue, Fetcher tfetcher, Parser tparser) {
		setRequestWorkersConfig(rConfig).setParserWorkersConfig(pConfig)
				.setRequestTaskQueue(rQueue).setParserTaskQueue(pQueue)
				.setTemplateFetcher(tfetcher).setTemplateParser(tparser);
	}

	public Dispatcher(Fetcher tfetcher, Parser tparser) {
		this(new RequestWorkersConfig(DEFAULT_REQUEST_WORKER_NUM),
				new ParserWorkersConfig(DEFAULT_PARSER_WORKER_NUM),
				new ParserQueuePriority(), new RequestQueuePriority(),
				tfetcher, tparser);
	}

	private void initRequestWorkers() {
		if (rWorkers == null) {
			rWorkers = new RequestWorker[rWorkersConfig.workerNum];
			for (int i = 0; i < rWorkersConfig.workerNum; i++) {
				rWorkers[i] = newRequestWorker();
				rWorkers[i].start();
			}
		}
	}

	private void initParserWorkers() {
		if (pWorkers == null) {
			pWorkers = new ParserWorker[pWorkersConfig.workerNum];
			for (int i = 0; i < pWorkersConfig.workerNum; i++) {
				pWorkers[i] = newParserWorker();
				pWorkers[i].start();
			}
		}
	}

	private ParserWorker newParserWorker() {
		try {
			return new ParserWorker(parserQueue, tParser.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private RequestWorker newRequestWorker() {
		try {
			return new RequestWorker(requestQueue, tFetcher.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
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
		initParserWorkers();
		parserQueue.offer(task);
	}
}
