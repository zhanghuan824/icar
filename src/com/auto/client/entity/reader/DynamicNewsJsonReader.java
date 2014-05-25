package com.auto.client.entity.reader;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.auto.client.common.DateTool;
import com.auto.client.domain.dynamic.News;
import com.auto.client.domain.dynamic.NewsSubItem;

public class DynamicNewsJsonReader extends AbstractJsonReader {
	
	@Override
	public List<Object> readObjectList(String json) throws Exception {
		List<Object> newsList = new ArrayList<Object>();
		JSONTokener jsonParser = new JSONTokener(json);
		JSONArray array = (JSONArray)jsonParser.nextValue();
		
		for(int i = 0; i < array.length(); i++) {
			JSONObject news = array.getJSONObject(i);
			newsList.add(readNews(news));
		}
		return newsList;
	}
	
	@SuppressWarnings("deprecation")
	private News readNews(JSONObject news) throws JSONException, Exception {
		News n = new News();
		n.setDesc(news.getString("desc"));
		n.setId(Long.parseLong(news.getString("id")));
		n.setImage(news.getString("image"));
		n.setTitle(news.getString("title"));
		n.setUpdateTime(DateTool.convert(Long.parseLong(news.getString("updateTime"))));
		//n.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").parse(updateTime.replace('T', ' ')));
		n.setUrl(news.getString("url"));
		JSONArray subitemArray = news.getJSONArray("subItems");
		for(int j = 0; j < subitemArray.length(); j++) {
			JSONObject o = subitemArray.getJSONObject(j);
			NewsSubItem nsi = readNewsSubItem(o);
			n.addNewsSubItem(nsi);
		}
		return n;
	}
	
	private NewsSubItem readNewsSubItem(JSONObject newsSubItem) throws JSONException {
		NewsSubItem nsi = new NewsSubItem();
		nsi.setId(Long.parseLong(newsSubItem.getString("id")));
		nsi.setImage(newsSubItem.getString("image"));
		nsi.setTitle(newsSubItem.getString("title"));
		nsi.setUrl(newsSubItem.getString("url"));
		return nsi;
	}
}
