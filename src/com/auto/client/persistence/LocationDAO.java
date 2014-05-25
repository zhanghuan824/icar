package com.auto.client.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.auto.client.domain.Location;

public class LocationDAO {
	
	private DBHelper helper;
	private SQLiteDatabase db;

	public LocationDAO(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public void delete(Location location) {
		db.delete("location", "id=?", new String[] { String.valueOf(location.getId()) });
	}
	
	public void update(Location location) {
		ContentValues cv = new ContentValues();
		cv.put("province", location.getProvince());
		cv.put("city", location.getCity());
		cv.put("district", location.getDistrict());
		db.update("location", cv, "id=?", new String[] { String.valueOf(location.getId()) });
	}

	public Location query(int id) {
		Cursor cursor = db.rawQuery("select province,city,district from location where id=?", 
				new String[] { String.valueOf(id) });
		if(cursor.moveToNext()) {
			Location location = new Location();
			location.setId(id);
			location.setProvince(cursor.getString(0));
			location.setCity(cursor.getString(1));
			location.setDistrict(cursor.getString(2));
			cursor.close();
			return location;
		} 
		return null;
	}
	
	public List<Location> queryByProvince(String province) {
		Cursor cursor = db.rawQuery("select id, city,district from location where province=?", 
				new String[] { province });
		ArrayList<Location> locationList = new ArrayList<Location>();
		while(cursor.moveToNext()) {
			Location location = new Location();
			location.setId(0);
			location.setProvince(province);
			location.setCity(cursor.getString(1));
			location.setDistrict(cursor.getString(2));
			locationList.add(location);
		} 
		cursor.close();
		return locationList;
	}
	
	public List<Location> queryByProvinceAndCity(String province, String city) {
		Cursor cursor = db.rawQuery("select id, district from location where province=? and city=?", 
				new String[] { province });
		ArrayList<Location> locationList = new ArrayList<Location>();
		while(cursor.moveToNext()) {
			Location location = new Location();
			location.setId(0);
			location.setProvince(province);
			location.setCity(city);
			location.setDistrict(cursor.getString(1));
			locationList.add(location);
		} 
		cursor.close();
		return locationList;
	}
}
