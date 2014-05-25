package com.auto.client.entity.reader;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.auto.client.common.DateTool;
import com.auto.client.component.artical.Artical;

public class ArticalJsonReader extends AbstractJsonReader {
	
	private Artical readArtical(JSONObject jo) throws JSONException, Exception {
		Artical artical = new Artical();
		artical.setFollowUpCount(jo.getInt("followUpCount"));
		artical.setImage(jo.getString("image"));
		artical.setShortDesc(jo.getString("shortDesc"));
		artical.setTitle(jo.getString("title"));
		artical.setUpdateTime(DateTool.convert(Long.parseLong(jo.getString("updateTime"))));
		artical.setUrl(jo.getString("url"));
		return artical;
	}

	@Override
	public List<Object> readObjectList(String json) throws Exception {
		List<Object> articalList = new ArrayList<Object>();
		JSONTokener jsonParser = new JSONTokener(json);
		JSONArray array = (JSONArray)jsonParser.nextValue();
		for(int i = 0; i < array.length(); i++) {
			JSONObject artical = array.getJSONObject(i);
			articalList.add(readArtical(artical));
		}
		return articalList;
	}
}
