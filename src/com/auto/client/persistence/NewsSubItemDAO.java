package com.auto.client.persistence;

import java.util.ArrayList;
import java.util.List;

import com.auto.client.domain.dynamic.NewsSubItem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsSubItemDAO {

	private DBHelper helper;
	
	public NewsSubItemDAO(DBHelper dbHelper) {
		helper = dbHelper;
	}
	
	public synchronized void add(List<NewsSubItem> nsi) throws Exception {
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.beginTransaction();
			addWithTransaction(nsi, db);
			db.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			db.endTransaction();
		}
	}
	
	public void addWithTransaction(List<NewsSubItem> nsi, SQLiteDatabase db) {
		for(NewsSubItem n : nsi) {
			db.execSQL("insert into dynamic_news_sub_item (id,title,image,news_id,url) values (?,?,?,?,?)",
					new Object[] { String.valueOf(n.getId()), n.getTitle(), n.getImage(), 
									String.valueOf(n.getNewsId()), n.getUrl() });
		}
	}
	
	public synchronized List<NewsSubItem> queryByNewsId(long newsId) {
		SQLiteDatabase db = null;
		db = helper.getReadableDatabase();
		return queryByNewsId(newsId, db);
	} 
	
	public List<NewsSubItem> queryByNewsId(long newsId, SQLiteDatabase db) {
		List<NewsSubItem> nsiList = new ArrayList<NewsSubItem>();
		Cursor cursor = db.rawQuery("select id,title,image,url from dynamic_news_sub_item where news_id=?", 
				new String[] { String.valueOf(newsId) });
		while(cursor.moveToNext()) {
			NewsSubItem nsi = new NewsSubItem();
			nsi.setId(Long.parseLong(cursor.getString(0)));
			nsi.setTitle(cursor.getString(1));
			nsi.setImage(cursor.getString(2));
			nsi.setUrl(cursor.getString(3));
			nsi.setNewsId(newsId);
			nsiList.add(nsi);
		}
		cursor.close();
		return nsiList;
	}
}
