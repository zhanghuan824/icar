package com.auto.client.component.chat;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.domain.User;

public class ChatFragment extends SherlockFragment {

	public static final String TITLE = "ª·ª∞";
	private ChatThreadAdapter _adapter;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.component_chat,
				container, false);
		
		User userLogin = new User();
		userLogin.setNickname("’≈ª¿");
		List<ChatThread> chatList = ChatManager.getChatThreads(userLogin);
		ChatThread[] chatThreadArray = new ChatThread[chatList.size()];
		_adapter = new ChatThreadAdapter(getActivity(), R.layout.chat_list_item, chatList.toArray(chatThreadArray));
		ListView listView = (ListView)rootView.findViewById(R.id.chat_thread_list_view);
		listView.setAdapter(_adapter);
		return rootView;
	}
	
}
