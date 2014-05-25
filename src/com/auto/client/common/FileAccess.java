package com.auto.client.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 文件访问和操作类
 * @Description: 文件访问和操作类
 */
public class FileAccess {
	/**
	 * String --> InputStream
	 * @param str
	 * @return
	 */
	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}
}
