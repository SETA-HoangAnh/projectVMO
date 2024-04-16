package com.example.project.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        //Đặt địa điểm đường dẫn mà broker gửi lên để đăng ký với SockJS:
        registry.enableSimpleBroker("/topic");

        //Đặt địa điểm đường dẫn cho @MessageMapping để FE nhận biết và trả về:
        registry.setApplicationDestinationPrefixes("/ws");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/api/our-websocket")
                .setHandshakeHandler(new UserHandshakeHandler()).setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
