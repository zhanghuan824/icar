package com.auto.client.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * �ļ����ʺͲ�����
 * @Description: �ļ����ʺͲ�����
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
