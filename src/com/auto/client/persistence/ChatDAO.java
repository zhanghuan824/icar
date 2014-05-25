package com.auto.client.persistence;

import java.util.ArrayList;
import java.util.List;

import com.auto.client.common.DateTool;
import com.auto.client.component.chat.ChatMessage;
import com.auto.client.component.chat.ChatThread;
import com.auto.client.domain.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChatDAO {

	private DBHelper helper;
	private Context context;
	
	public ChatDAO(Context context) {
		this.context = context;
		helper = new DBHelper(context);
	}
	
	public void add(ChatMessage message) {
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.beginTransaction();
			db.execSQL("insert into chat_message (from_user_id,to_user_id,message,time) " +
					"values (?,?,?,?)", new Object[] { message.getFrom().getUserId(), message.getTo().getUserId(),
					message.getMessage(), DateTool.format(message.getCreateTime(), "yyyy-MM-dd HH:mm:ss.SSS") });	
			db.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			helper.close();
		}
	}
	
	
	public List<ChatThread> getAllChatThread() {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			List<ChatThread> list = new ArrayList<ChatThread>();
			db = helper.getReadableDatabase();
			cursor = db.rawQuery("select c.id, c.image, c.name, b.message, b.time, c.email, c.sex from logon_user a " +
					"inner join chat_thread b on a.id=b.login_user inner join user c on " +
					"c.id=b.peer_user where a.is_login=1 order by b.time desc", new String[] {});
			while(cursor.moveToNext()) {
				ChatThread thread = new ChatThread();
				User user = new User();
				user.setUserId(Integer.parseInt(cursor.getString(0)));
				user.setFaceImage(cursor.getString(1));
				user.setNickname(cursor.getString(2));
				user.setEmail(cursor.getString(5));
				user.setSex(cursor.getString(6).charAt(0));
				thread.setPeerUser(user);
				thread.setMessage(cursor.getString(3));
				thread.setLastTime(DateTool.parse(cursor.getString(4), "yyyy-MM-dd HH:mm:ss.SSS"));
				list.add(thread);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(cursor != null) cursor.close();
			if(db != null) 
				helper.close();
		}
	}
}
