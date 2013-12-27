package com.node.spider.fetch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.util.TextUtils;

import com.node.spider.pubclass.HttpStatus;
import com.node.spider.pubclass.Link;
import com.node.spider.pubclass.NameValuePair;
import com.node.spider.pubutil.CharsetUtils;

class UrlConnectionFetcher extends Fetcher {

	Type fetchType;

	public UrlConnectionFetcher() {
		fetchType = Type.UrlConnection;
	}

	@Override
	public void fetch(Link link) {
		URL url;
		try {
			addCookie(link);
			url = new URL(link.url);
			HttpURLConnection connect = (HttpURLConnection) url
					.openConnection();
			// 设置连接超时
			connect.setConnectTimeout((int) link.requestTimeOut);
			connect.setReadTimeout((int) link.readTimeout);
			// 添加头信息
			for (int i = 0; i < link.headerConfig.headers.size(); i++) {
				NameValuePair<String, String> header = link.headerConfig.headers
						.get(i);
				connect.addRequestProperty(header.getName(), header.getValue());
			}
			// 设置请求方法GET or POST
			connect.setRequestMethod(link.requestMethod);
			// 设置缓存机制
			connect.setUseCaches(link.usecache);

			if (callback != null) {
				callback.onBeforeFetch(fetchType, link,
						connect.getRequestProperties());
			}
			connect.connect();
			int statusCode = connect.getResponseCode();
			if (statusCode == HttpStatus.SC_OK) {
				try {
					String contentEncoding = connect.getContentEncoding();
					InputStream is = connect.getInputStream();
					byte[] buffer = new byte[5 * 1024];
					int length = -1;
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					if (!TextUtils.isEmpty(contentEncoding)
							&& contentEncoding.equals("gzip")) {
						InputStream gis = new GZIPInputStream(is);
						while ((length = gis.read(buffer)) != -1) {
							bos.write(buffer, 0, length);
						}
						try {
							gis.close();
						} catch (Exception e) {
							// do nothing
						}
					} else {
						while ((length = is.read(buffer)) != -1) {
							bos.write(buffer, 0, length);
						}
					}
					byte[] result = bos.toByteArray();
					// 获取成功，回调
					if (callback != null) {
						callback.onFetchSuccess(fetchType, link, result,
								connect.getHeaderFields());
					}

					try {
						bos.close();
						is.close();
					} catch (Exception e) {
						// do nothing
					}
				} catch (IOException e) {
					e.printStackTrace();
					if (callback != null) {
						callback.onNetWorkIOError(fetchType, link);
					}
				}
			} else {
				// 此连接存在问题，降低优先度最低
				if (callback != null) {
					callback.onFetchError(fetchType, link, statusCode,
							connect.getHeaderFields());
				}
			}
			connect.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			if (callback != null) {
				callback.onTimeOut(fetchType, link);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (callback != null) {
				callback.onNetWorkIOError(fetchType, link);
			}
		}
	}

	@Override
	public Fetcher clone() throws CloneNotSupportedException {
		Fetcher f = new UrlConnectionFetcher();
		f.setCallbask(callback.clone());
		return f;
	}
}
