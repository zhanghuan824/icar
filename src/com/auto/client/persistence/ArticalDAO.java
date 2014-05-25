package com.auto.client.persistence;

import java.util.ArrayList;
import java.util.List;

import com.auto.client.common.DateTool;
import com.auto.client.component.artical.Artical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ArticalDAO {
	private DBHelper helper;
	
	public ArticalDAO(DBHelper dbHelper) {
		helper = dbHelper;
	}
	
	public synchronized void add(Artical artical) {
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.beginTransaction();
			
			ContentValues cv = new ContentValues();
			cv.put("url", artical.getUrl());
			cv.put("title", artical.getTitle());
			cv.put("follow_up_count", artical.getFollowUpCount());
			cv.put("image", artical.getImage());
			cv.put("update_time", DateTool.format(artical.getUpdateTime(), "yyyy-MM-dd HH:mm:ss.SSS"));
			cv.put("short_desc", artical.getShortDesc());
			db.insert("artical", "url", cv);
			db.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(db != null) 
				db.endTransaction();
		}
	}
	
	public synchronized List<Artical> query(int fromIndex, int count) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			List<Artical> articalList = new ArrayList<Artical>();
			db = helper.getReadableDatabase();
			cursor = db.rawQuery("select url,title,follow_up_count,image,update_time,short_desc from artical order by update_time desc limit ? offset ? ", 
					new String[] { String.valueOf(count), String.valueOf(fromIndex) });
			while(cursor.getCount() > 0 && cursor.moveToNext()) {
				Artical artical = new Artical();
				artical.setUrl(cursor.getString(0));
				artical.setTitle(cursor.getString(1));
				artical.setFollowUpCount(cursor.getInt(2));
				artical.setImage(cursor.getString(3));
				artical.setUpdateTime(DateTool.parse(cursor.getString(4), "yyyy-MM-dd HH:mm:ss.SSS"));
				artical.setShortDesc(cursor.getString(5));
				articalList.add(artical);
			}
			return articalList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(cursor != null) cursor.close();
		}
	}
	
	public synchronized long queryLastUpdateTime() {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			long updateTime = 0L;
			db = helper.getReadableDatabase();
			cursor = db.rawQuery("select max(update_time) as update_time from artical", new String[] {});
			while(cursor.moveToNext()) {
				updateTime = DateTool.convert(DateTool.parse(cursor.getString(0), "yyyy-MM-dd HH:mm:ss.SSS"));
				break;
			}
			return updateTime;
		} catch(Exception e) {
			e.printStackTrace();
			return 0L;
		} finally {
			if(cursor != null) cursor.close();
		}
	}
}
