package com.auto.client.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.auto.client.common.DateTool;
import com.auto.client.component.artical.Artical;
import com.auto.client.domain.Location;
import com.auto.client.domain.maintain.Station;

public class StationDAO {

	private DBHelper helper;
	
	public StationDAO(DBHelper dbHelper) {
		helper = dbHelper;
	}
	
	public synchronized List<Station> query(int fromIndex, int count) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			List<Station> stationList = new ArrayList<Station>();
			db = helper.getReadableDatabase();
			cursor = db.rawQuery("select s.id,s.name,s.image,s.address,s.desc,s.comment_count,s.visit_count," +
					"s.score,s.level,s.longitude,s.latitude,l.id,l.province,l.city,l.district " +
					"from station s inner join location l on s.location=l.id order by l.province,l.city,l.district" +
					" limit ? offset ?", 
					new String[] { String.valueOf(count), String.valueOf(fromIndex) });
			while(cursor.getCount() > 0 && cursor.moveToNext()) {
				Station station = new Station();
				station.setId(cursor.getInt(0));
				station.setName(cursor.getString(1));
				station.setImage(cursor.getString(2));
				station.setAddress(cursor.getString(3));
				station.setDesc(cursor.getString(4));
				station.setCommentCount(cursor.getInt(5));
				station.setVisitCount(cursor.getInt(6));
				station.setScore(cursor.getDouble(7));
				station.setLevel(cursor.getInt(8));
				station.setLongitude(cursor.getDouble(9));
				station.setLatitude(cursor.getDouble(10));
				
				Location location = new Location();
				location.setId(cursor.getInt(11));
				location.setProvince(cursor.getString(12));
				location.setCity(cursor.getString(13));
				location.setDistrict(cursor.getString(14));
				station.setLocation(location);
				
				station.setServiceCategories(null);
				
				stationList.add(station);
			}
			return stationList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(cursor != null) cursor.close();
		}
	}
	
	public synchronized void add(List<Station> stations) {
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.beginTransaction();
			for(Station station : stations) {
				ContentValues cv = new ContentValues();
				cv.put("id", station.getId());
				cv.put("name", station.getName());
				cv.put("image", station.getImage());
				cv.put("address", station.getAddress());
				cv.put("desc", station.getDesc());
				cv.put("comment_count", station.getCommentCount());
				cv.put("visit_count", station.getVisitCount());
				cv.put("score", station.getScore());
				cv.put("level", station.getLevel());
				cv.put("longitude", station.getLongitude());
				cv.put("latitude", station.getLatitude());
				cv.put("location", station.getLocation().getId());
				db.insert("station", null, cv);
			}
			db.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(db != null)
				db.endTransaction();
		}
	} 
}
