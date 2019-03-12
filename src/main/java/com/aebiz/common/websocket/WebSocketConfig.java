package com.aebiz.common.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		
		
		/*########################
         * 用户可以订阅来自"/topic"和"/user"的消息，
         * 在Controller中，可通过@SendTo注解指明发送目标，这样服务器就可以将消息发送到订阅相关消息的客户端
         *
         * 在本Demo中，使用topic来达到群发效果，使用user进行一对一发送
         *
         * 客户端只可以订阅这两个前缀的主题
         */
        config.enableSimpleBroker("/topic", "/user");

		
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user");  

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/my-websocket").setAllowedOrigins("*").withSockJS();
	}
	
	
	  /**
     * 配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(createUserInterceptor());
    }
    
    /**
     * 
     * @Title: createUserInterceptor
     * @Description: 将客户端渠道拦截器加入spring ioc容器
     * @return
     */
    @Bean
    public UserInterceptor createUserInterceptor() {
        return new UserInterceptor();
    }


}
