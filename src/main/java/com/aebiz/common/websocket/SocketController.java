package com.aebiz.common.websocket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/socket")
public class SocketController {


	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/index2")
	public String index2() {
		return "index2";
	}
	
	@GetMapping("/webSocket")
	public String webSocket() {
		return "bak/webSocket";
	}
	
	
	@GetMapping("/webSocket2")
	public String webSocket2() {
		return "bak/webSocket2";
	}
	@GetMapping("/webSocket3")
	public String webSocket3() {
		return "bak/webSocket3";
	}
	
	@GetMapping("/index3")
	public String index3() {
		return "index3";
	}

	@MessageMapping("/send")
	@SendTo("/topic/send")
	public SocketMessage send(SocketMessage message) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.date = df.format(new Date());
		return message;
	}

	//@Scheduled(fixedRate = 5000)
	//@SendTo("/topic/callback")
	public Object callback() throws Exception {
		// 发现消息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
		System.out.println("111");

		return "callback111";
	}
	
	//@Scheduled(fixedRate = 5000)
	//@SendToUser(value="lsl",broadcast=false)
	public Object callback2() throws Exception {
		// 发现消息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messagingTemplate.convertAndSendToUser("lsl", "/topic/callback2", df.format(new Date())+"####");
		System.out.println("2222");

		return "callback111";
	}
	
}
