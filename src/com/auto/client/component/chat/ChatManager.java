package com.auto.client.component.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.auto.client.domain.User;

/**
 * �������еĻỰ
 * @author zhanghuan
 *
 */
public class ChatManager {
	
	/**
	 * ��ȡloginUser�Ĺ���������û���¼
	 * @param loginUser ��ǰ��¼���û�
	 * @return �����û��������¼
	 */
	public static List<ChatThread> getChatThreads(User loginUser) {
		List<ChatThread> chatThread = new ArrayList<ChatThread>();
		User peer = new User();
		peer.setNickname("����");
		ChatThread th1 = new ChatThread();
		th1.setPeerUser(peer);
		chatThread.add(th1);
		chatThread.add(th1);
		return chatThread;
	}
	
	public static void deleteChatThread(User loginUser, User peer) {
		
	}
	
	public static List<ChatMessage> getChatMessages(User loginUser, User peer, int fromIndex, int count) {
		ArrayList<ChatMessage> chatMsgArray = new ArrayList<ChatMessage>();
		chatMsgArray.add(new ChatMessage(loginUser, peer, "���������Ǻõ�", new Date()));
		return chatMsgArray;
	} 
	
	public static void deleteChatMessage(User loginUser, User peer, int index) {
		
	}
}
