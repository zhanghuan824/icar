package com.auto.client.component.chat;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatThreadAdapter extends ArrayAdapter<ChatThread> {
	
	private int _resourceId;
	
	public ChatThreadAdapter(Context context, int resourceId, ChatThread[] objects) {
		super(context, resourceId, objects);
		this._resourceId = resourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = ((SherlockFragmentActivity)getContext()).getLayoutInflater();
			convertView = inflater.inflate(_resourceId, parent, false);
		}
		ChatThread thread = getItem(position);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.chat_thread_list_image);
		imageView.setBackgroundResource(R.drawable.ic_action_help);
		
		TextView tvUserName = (TextView)convertView.findViewById(R.id.chat_thread_user_name);
		//tvUserName.setText(thread.getLoginUser().getName());
		
		
		ChatMessage chatMsg = null;//thread.getChatMessages(0, 1).get(0);
		TextView tvLatestChatMsg = (TextView)convertView.findViewById(R.id.latest_chat_message);
		tvLatestChatMsg.setText(chatMsg.getMessage());
		
		TextView tvTimeChatMsg = (TextView)convertView.findViewById(R.id.latest_message_time);
		//tvTimeChatMsg.setText(chatMsg.getCreateTime().toString());
		tvTimeChatMsg.setText("23:19");
		
		return convertView;
	}
}
