package com.aebiz.common.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SocketHandler implements WebSocketHandler {

	private static final ArrayList<WebSocketSession> users;

	static {
		users = new ArrayList<WebSocketSession>();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
		String username = session.getAttributes().get("user").toString();

		if (username != null) {
			session.sendMessage(new TextMessage("我们已经成功建立socket通信了"));
		}

	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> arg1) throws Exception {
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	
	 public void sendMessageToUsers(TextMessage message) {
		
		 for(WebSocketSession user : users){
			 if (user.isOpen()) {
				 try {
					user.sendMessage(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		
	 }
	 
	 
public void sendMessageToUser(String userName, TextMessage message) {
	for(WebSocketSession user : users){
		if(user.getAttributes().get("user").equals(userName)){
			try {
				user.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	 
	}
}

	/*
	 * public void sendMessageToUser(String userName, TextMessage message) {
	 * 
	 * Set<SimpUser> users = userRegistry.getUsers();         for
	 * (WebSocketSession user : users) {             if
	 * (user.getAttributes().get("user").equals(userName)) {                 try
	 * {                     if (user.isOpen()) {                        
	 * user.sendMessage(message);                     }                 } catch
	 * (IOException e) {                     e.printStackTrace();              
	 *   }                 break;             }         } }
	 */

}
