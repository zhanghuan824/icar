package com.auto.client.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "auto.db3";
	private static final int DATABASE_VERSION = 1;
	
	public DBHelper(Context context) {
		//CursorFactory设置为null，使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public DBHelper(Context context, String dbName, CursorFactory factory, int version) {
		super(context, dbName, factory, version);
	}
	
	@Override
	//数据库第一次被创建时会调用
	public void onCreate(SQLiteDatabase db) {
		//user
		db.execSQL("create table if not exists user " +
				"(id int," +
				"email text," +
				"nickname text," +
				"password text," +
				"sex text," +
				"birthday text," +
				"location int," +
				"private_face_image text)");
		
		//location
		db.execSQL("create table if not exists location " + 
				"(id int," +
				"province text," +
				"city text," +
				"district text)");
		
		//dynamic news
		db.execSQL("create table if not exists dynamic_news " +
				"(id text, url text, title text, `desc` text," +
				"image text, update_time text)");
		
		//dynamic news sub item
		db.execSQL("create table if not exists dynamic_news_sub_item (" +
				"id text, title text, image text, news_id text, url text)");
		
		//chat message
		db.execSQL("create table if not exists chat_message (" +
				"from_user_id text, to_user_id text, message text, time text)");
		
		//chat history
		db.execSQL("create table if not exists chat_thread (" +
				"login_user text, peer_user text, message text, time text)");
		
		//user
		db.execSQL("create table if not exists user (" +
				"id text, image text, name text, email text, sex char(1))");
		
		//logon user
		db.execSQL("create table if not exists logon_user (" +
				"id text, mobile text, password text, name text, personal_desc text, is_login int)");
		
		//Artical
		db.execSQL("create table if not exists artical (" +
				"url text, title text, follow_up_count int, image text, update_time text, short_desc text)");
		
		//Station
		db.execSQL("create table if not exists station (" +
				"id int, name text,image text,address text,`desc` text, comment_count int," +
				"visit_count int, score double, level int, longitude double, latitude double," +
				"location int)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
