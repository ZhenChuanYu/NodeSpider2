package com.node.spider.pubutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.TextUtils;

import com.node.spider.pubclass.ConfigException;

public class FileUtil {
	private static final String TAG = FileUtil.class.getName();

	public static Map<String, String> readKeyValueConfigFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG + ":" + "config path is null");
			return null;
		}
		Map<String, String> pair = null;
		RandomAccessFile c = null;
		try {
			c = new RandomAccessFile(new File(filePath), "r");
			String line = null;
			pair = new HashMap<String, String>();
			while ((line = c.readLine()) != null) {
				String[] str = line.split("#");
				if (str.length == 2) {
					pair.put(str[0], str[1]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pair;
	}
}
