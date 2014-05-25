package com.auto.client.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class NetResourceManager {

	public static InputStream getHttpInputStream(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		//make GET request to the given URL
		HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
		//receive response as input stream
		InputStream inputStream = httpResponse.getEntity().getContent();
		return inputStream;
	}
	
	public static String getJsonBody(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			//make GET request to the given URL
			HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
			//receive response as input stream
			inputStream = httpResponse.getEntity().getContent();
			//convert input stream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
		} catch(Exception e) {
			Log.d("NetResourceManager", e.getLocalizedMessage());
		}
		return result;
	}
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		StringBuilder sb = new StringBuilder();
		while((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}
		inputStream.close();
		return sb.toString();
	}
}
