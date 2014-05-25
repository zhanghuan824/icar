package com.auto.client.entity.reader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import com.auto.client.common.NetResourceManager;

public abstract class AbstractJsonReader {
	
	public List<Object> get(String url) throws Exception {
		InputStream in = NetResourceManager.getHttpInputStream(url);
		return readObjectList(in);
	}
	
	protected String readData(InputStream in) throws Exception {
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		StringBuilder sbJson = new StringBuilder();
		int data = -1;
		while((data = isr.read()) != -1) {
			sbJson.append((char)data);
		}
		return sbJson.toString();
	}
	
	protected List<Object> readObjectList(InputStream in) throws Exception {
		String strJson = readData(in);
		if(strJson.length() > 0) {
			return readObjectList(strJson);
		} else 
			return null;
	}
	
	public abstract List<Object> readObjectList(String json) throws Exception;
}
