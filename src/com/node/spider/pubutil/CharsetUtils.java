package com.node.spider.pubutil;

import org.apache.http.util.TextUtils;

public class CharsetUtils {
	private static String DEFAULT_CHARSET = "UTF-8";

	public static String getCharsetFromContentType(String contentType) {
		if (!TextUtils.isEmpty(contentType)) {
			String[] contents = contentType.split(";");
			for (String str : contents) {
				if (str.toLowerCase().contains("charset")) {
					return str.substring(str.indexOf("=") + 1);
				}
			}
			return DEFAULT_CHARSET;
		} else {
			return DEFAULT_CHARSET;
		}
	}

	public static String defaultCharset() {
		return DEFAULT_CHARSET;
	}
}
