package com.auto.client.persistence;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.auto.client.domain.User;

public class UserDAO {

	private DBHelper helper;
	private Context context;
	
	public UserDAO(Context context) {
		helper = new DBHelper(context);
		this.context = context; 
	}
	
	public void add(List<User> users) {
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			//开始事务
			db.beginTransaction();
			for(User user : users) {
				db.execSQL("insert into person (id,email,nickname,password,sex,birthday,location,private_face_image)" +
						" values (?,?,?,?,?,?,?,?)", 
						new Object[] { user.getUserId(), user.getEmail(), user.getNickname(), user.getPassword(),
							user.getSex(), 
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(user.getBirthday()), 
							user.getLocation().getId(), 
							user.getFaceImage() });
			}
			//设置事务完成
			db.setTransactionSuccessful();
		} finally {
			if(db != null) {
				//结束事务
				db.endTransaction();
			}
		}
	}
	
	public void add(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into person (id,email,nickname,password,sex,birthday,location,private_face_image)" +
				" values (?,?,?,?,?,?,?,?)", 
				new Object[] { user.getUserId(), user.getEmail(), user.getNickname(), user.getPassword(),
					user.getSex(), 
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(user.getBirthday()), 
					user.getLocation().getId(), 
					user.getFaceImage() });
	}
	
	public void update(User user) {
		ContentValues cv = new ContentValues();
		cv.put("email", user.getEmail());
		cv.put("nickname", user.getNickname());
		cv.put("password", user.getPassword());
		cv.put("sex", String.valueOf(user.getSex()));
		cv.put("birthday", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(user.getBirthday()));
		cv.put("location", user.getLocation().getId());
		cv.put("private_face_image", user.getFaceImage());
		
		SQLiteDatabase db = null;
		db = helper.getWritableDatabase();
		db.update("user", cv, "id=?", new String[] { String.valueOf(user.getUserId()) });
	}
	
	public void delete(User user) {
		SQLiteDatabase db = null;
		db = helper.getWritableDatabase();
		db.delete("user", "id=?", new String[] { String.valueOf(user.getUserId()) }); 
	}
	
	public User query(int id) {
		SQLiteDatabase db = null;
		try {
			db = helper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select email,nickname,password,sex,birthday,location,private_face_image from user where id=?", 
					new String[] { String.valueOf(id) });
		
			if(cursor.moveToNext()) {
				User user = new User();
				user.setUserId(id);
				user.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(cursor.getString(4)));
				user.setEmail(cursor.getString(0));
				user.setNickname(cursor.getString(1));
				user.setPassword(cursor.getString(2));
				user.setSex(cursor.getString(3).charAt(0));
				user.setLocation(new LocationDAO(context).query(cursor.getInt(5)));
				user.setFaceImage(cursor.getString(6));
				return user;
			} else 
				return null;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
