package com.node.spider.fetch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.HeaderGroup;

import com.node.spider.pubclass.Link;

public class UrlConnectionFetcher extends Fetcher {

	public UrlConnectionFetcher() {
	}

	@Override
	public void fetch(Link l) {
		URL url;
		try {
			url = new URL(l.url);
			HttpURLConnection connect = (HttpURLConnection) url
					.openConnection();
			connect.setConnectTimeout(60 * 1000);
			connect.setReadTimeout(60 * 1000);
			connect.addRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
			connect.addRequestProperty("Accept", "*/*");
			connect.addRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			connect.addRequestProperty(
					"Cookie",
					"randomSeed=388657; __Q_w_s_hat_seed=1; __Q_w_s__QZN_TodoMsgCnt=1; ts_uid=3675571111; __Q_w_s__appDataSeed=1; cpu_performance=3; RK=oY5rk+ovGv; pgv_pvi=9151162106; _gscu_661903259=87000190bn9n2q20; qzspeedup=sdch; o_cookie=372329230; p_uin=o0372329230; pgv_info=ssid=s8187492568; pgv_pvid=5720920348; Loading=Yes; qz_screen=1280x800; __layoutStat=20; cpu_performance_v8=5; zzpaneluin=; zzpanelkey=; ptui_loginuin=372329230; pt2gguin=o0372329230; uin=o0372329230; skey=@dcS6CpErY; ptisp=cnc; ptcz=9a1c7a8b60fc78cecf1d8751cacef4c54f00533bed33e66abbb398c11fa722ec; fnc=2");
			connect.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connect.addRequestProperty("Connection", "keep-alive");
			connect.setRequestMethod("GET");
			connect.setUseCaches(false);
			connect.connect();

			connect.getHeaderFields();
			System.out.println(url);
			System.out.println(connect.getHeaderField("Content-Encoding"));
			System.out.println(connect.getResponseCode());
			connect.getResponseCode();
			InputStream is = connect.getInputStream();
			InputStream urlStream = new GZIPInputStream(is);

			File f = new File("./" + Integer.toHexString(l.url.hashCode())
					+ System.currentTimeMillis() + ".html");
			if (!f.exists()) {
				f.createNewFile();
			}
			byte[] buffer = new byte[5 * 1024];
			RandomAccessFile c = new RandomAccessFile(f, "rw");
			int length = -1;
			while ((length = urlStream.read(buffer)) != -1) {
				c.write(buffer, 0, length);
			}
			c.close();
			urlStream.close();
			is.close();
			connect.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
