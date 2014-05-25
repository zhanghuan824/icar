package com.auto.client.component.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.auto.client.domain.User;

/**
 * 管理所有的会话
 * @author zhanghuan
 *
 */
public class ChatManager {
	
	/**
	 * 获取loginUser聊过天的所有用户记录
	 * @param loginUser 当前登录的用户
	 * @return 所有用户的聊天记录
	 */
	public static List<ChatThread> getChatThreads(User loginUser) {
		List<ChatThread> chatThread = new ArrayList<ChatThread>();
		User peer = new User();
		peer.setNickname("瑶瑶");
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
		chatMsgArray.add(new ChatMessage(loginUser, peer, "今天总算是好的", new Date()));
		return chatMsgArray;
	} 
	
	public static void deleteChatMessage(User loginUser, User peer, int index) {
		
	}
}
