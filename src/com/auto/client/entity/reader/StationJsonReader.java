package com.auto.client.entity.reader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.auto.client.domain.Location;
import com.auto.client.domain.maintain.ServiceCategory;
import com.auto.client.domain.maintain.Station;

public class StationJsonReader extends AbstractJsonReader {
	
	private Location readLocation(JSONObject locationJson) throws JSONException, Exception {
		Location location = new Location();
		location.setId(locationJson.getInt("id"));
		location.setProvince(locationJson.getString("province"));
		location.setCity(locationJson.getString("city"));
		location.setDistrict(locationJson.getString("district"));
		return location;
	}
	
	@SuppressWarnings("unchecked")
	private Station readStation(JSONObject stationJsonObj) throws JSONException, Exception {
		Station station = new Station();
		station.setAddress(stationJsonObj.getString("address"));
		station.setCommentCount(stationJsonObj.getInt("commentCount"));
		station.setDesc(stationJsonObj.getString("desc"));
		station.setId(stationJsonObj.getInt("id"));
		station.setImage(stationJsonObj.getString("image"));
		station.setLatitude(stationJsonObj.getDouble("latitude"));
		station.setLevel(stationJsonObj.getInt("level"));
		station.setLongitude(stationJsonObj.getDouble("longitude"));
		station.setName(stationJsonObj.getString("name"));
		station.setScore(stationJsonObj.getDouble("score"));
		station.setVisitCount(stationJsonObj.getInt("visitCount"));
		JSONObject locationObj = stationJsonObj.getJSONObject("location");
		station.setLocation(this.readLocation(locationObj));
		//station.setServiceCategories((List<ServiceCategory>)stationJsonObj.get("serviceCategories"));
		station.setServiceCategories(null);
		return station;
	}

	@Override
	public List<Object> readObjectList(String json) throws Exception {
		List<Object> stationList = new ArrayList<Object>();
		JSONTokener jsonParser = new JSONTokener(json);
		JSONArray array = (JSONArray)jsonParser.nextValue();
		
		for(int i = 0; i < array.length(); i++) {
			JSONObject station = array.getJSONObject(i);
			stationList.add(readStation(station));
		}
		return stationList;
	}
}
