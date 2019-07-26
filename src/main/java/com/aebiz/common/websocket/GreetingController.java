package com.aebiz.common.websocket;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController  
public class GreetingController {  
	
	
	 @Autowired
	    private SimpUserRegistry userRegistry;
    
    /**消息发送工具*/
    @Autowired
    private SimpMessagingTemplate template;
  
    /** 
     * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息 
     * @param topic 
     * @param headers 
     */  
    @MessageMapping("/hello") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的  
    @SendTo("/topic/greetings")  
    public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers) {  
        System.out.println("connected successfully....");  
        System.out.println(topic);  
        System.out.println(headers);  
    }  
  
    /** 
     * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中， 
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。 
     * @return 
     */  
    @MessageMapping("/message")  
    @SendToUser("/topic/message")  
    //，推送到特定用户不一定非要使用Spring Security这个框架来实现登录和访问控制，我在项目中使用的是Apache Shiro。 
    public Greeting handleSubscribe() {  
        System.out.println("this is the @SubscribeMapping('/marco')");  
        return new Greeting("I am a msg from SubscribeMapping('/macro').");  
    }  
  
    /** 
     * 测试对指定用户发送消息方法 
     * @return 
     */  
    @RequestMapping(path = "/send")  
    public Greeting send() {  
    	System.out.println("#######");
    	template.convertAndSendToUser("lsl", "/message", new Greeting("I am a msg from SubscribeMapping('/macro')."));  
        return new Greeting("I am a msg from SubscribeMapping('/macro').");  
    }  
    
    
    
    /**
     * 实现一对一
     * @return
     */
    @MessageMapping("/sendToUser") 
    @SendToUser(value="/topic/toMyUser",broadcast=false)
    public Greeting send22222() {  
    	System.out.println("#######");
    	//template.convertAndSendToUser("lsl", "/topic/toMyUser", new Greeting("I am a msg from SubscribeMapping('/macro')."));  
    	return new Greeting("I am a msg from SubscribeMapping('/macro').");  
    	
    	
    	/**
    	 * 我给服务器发送 服务器只给我发送 不给其他用户发送
    	 * 
    	 *   var name = document.getElementById('message').value;  
            stompClient.send("/app/sendToUser", {}, JSON.stringify({ 'name': name }));  
            
            
            
            
    	 * 
    	 *   stompClient.subscribe('/user/topic/toMyUser',function(greeting){  
                    alert(JSON.parse(greeting.body).content); 
                    
                    alert("11111")
                    showGreeting(JSON.parse(greeting.body).content);  
                });  
    	 * 
    	 */
    }  
    
    
    
    
    
    
    
    @MessageMapping("/sendWithFunction") 
    public void sendWithFunction() {  
    	System.out.println("#######");
    	
    	  System.out.println("当前在线人数:" + userRegistry.getUserCount());
           int i = 1;
           for (SimpUser user : userRegistry.getUsers()) {
        	   System.out.println("用户" + i++ + "---" + user);
           }
    	
    	
    	template.convertAndSendToUser("lsl", "/topic/toMyUser2", new Greeting("I am a msg from SubscribeMapping('/macro')."));
    	
    }  
    
    
    
    
    /**
     * 一对一通信
     * @param destUsername
     * @return
     * @throws Exception
     */
    
    //实现点对点点 通讯   stompClient.send("/app/demo3/hello/"
    @MessageMapping("/demo3/hello/{destUsername}")
    @SendToUser("/demo3/greetings")   //stompClient.subscribe('/user/' + $("#username").val() + '/demo3/greetings'
    public Greeting greeting(@DestinationVariable String destUsername) throws Exception {

     /*   Authentication user = (Authentication) headerAccessor.getUser();

        String sessionId = headerAccessor.getSessionId();

        Greeting greeting = new Greeting(user.getName(), "sessionId: " + sessionId + ", message: " + message.getMessage());*/

        /*
         * 对目标进行发送信息
         */
    	template.convertAndSendToUser(destUsername, "/demo3/greetings", new Greeting("系统"));

        return new Greeting("系统");
    } 
    
    
    
    
    
    
    // //实现对用户主动推送  前提是认证用户
    @RequestMapping("sendToOne")
    public String sayHi(){
    	

  	  System.out.println("当前在线人数:" + userRegistry.getUserCount());
         int i = 1;
         for (SimpUser user : userRegistry.getUsers()) {
      	   System.out.println("用户" + i++ + "---" + user);
         }
    	template.convertAndSendToUser("lsl", "/demo3/greetings", new Greeting("I am a msg from SubscribeMapping('/macro')."));
    	
    //	template.con
    	
    	/**
    	 * 
    	 * 
    	 * 
    	 *     stompClient.subscribe('/user/lsl/demo3/greetings',function(greeting){  
                    alert(JSON.parse(greeting.body).content);  
                    showGreeting4(JSON.parse(greeting.body).content);  
                });
    	 */

    	return "aaaa";
    }
    
    
    
    //实现对用户主动推送  前提是认证用户
    @ResponseBody
    @RequestMapping("/msg/sendcommuser")
    public  Greeting SendToCommUserMessage(){
    	template.convertAndSendToUser("lsl","/demo3/greetings",new Greeting("222"));
        return new Greeting("系统");
    }
    
    
  
}  
