package com.auto.client.common;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class XMLParser {

	public XMLParser() {}
	
	/**
	 * get the XML entity from URL through HTTP request
	 * @param url XML path
	 * @return XML content
	 */
	public String getXmlFromUrl(String url) {
		String xml = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);
		} catch(UnsupportedEncodingException ex) {
			ex.printStackTrace();
			Log.e("Error:", ex.getMessage());
		} catch(ClientProtocolException ex) {
			ex.printStackTrace();
			Log.e("Error:", ex.getMessage());
		} catch(IOException ex) {
			ex.printStackTrace();
			Log.e("Error:", ex.getMessage());
		}
		
		return xml;
	}
	
	/**
	 * fetch XML dom element
	 * @param xml
	 * @return
	 */
	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
			Log.e("Error:", ex.getMessage());
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			Log.e("Error:", e.getMessage());
			return null;
		} catch (IOException e) {			
			e.printStackTrace();
			Log.e("Error:", e.getMessage());
			return null;
		}
		
		return doc;
	}
	
	/**
	 * Get node value
	 * @param node
	 * @return
	 */
	public final String getNodeValue(Node node) {
		Node child;
		if(node != null) {
			if(node.hasChildNodes()) {
				for(child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
					if(child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * Get element value 
	 * @param item
	 * @param str
	 * @return
	 */
	public String getElementValue(Element item, String name) {
		NodeList nodes = item.getElementsByTagName(name);
		return this.getNodeValue(nodes.item(0));
	}
}
