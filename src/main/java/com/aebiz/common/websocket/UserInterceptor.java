package com.aebiz.common.websocket;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor extends ChannelInterceptorAdapter {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("name");
                
                System.out.println(name+"ddddd");
                if (name instanceof LinkedList) {
                    // 设置当前访问器的认证用户
                	System.out.println("进入认证用户！1！！");
                    accessor.setUser(new UserForWebSocket(((LinkedList) name).get(0).toString()));
                }
                
                //Authentication user = new Authentication(username); // access authentication header(s)}

            }
        }
        return message;
	}

	
}
