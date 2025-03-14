package com.example.BE_number_blind_date.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSoketConfig implements WebSocketMessageBrokerConfigurer {

    // 엔드포인트 등록 -> /ws로 도착하는 것은 stomp 통신
    // configureMessageBroker 에서 sub, pub을 설정, /sub가 prefix로 붙으면 구독하는 것이고 /pub은 메시지를 송신할 때 사용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")             // soket 연결 url
                .setAllowedOrigins("http://localhost:3000");  // 보안 강화 (CORS 정책 적용)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");       // 구독 url
        registry.setApplicationDestinationPrefixes("/pub");          // prefix 정의
    }
}
