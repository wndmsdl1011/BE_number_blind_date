package com.example.BE_number_blind_date.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSoketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketInterceptor webSocketInterceptor;


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketInterceptor);
    }

    // 엔드포인트 등록 -> /ws로 도착하는 것은 stomp 통신
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-connect")             // soket 연결 url
                .setAllowedOriginPatterns("*");                 // CORS 설정
                //.withSockJS();

    }

//    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 구독(sub) : 접두사로 시작하는 메시지를 브로커가 처리하도록 설정
        config.enableSimpleBroker("/sub");

        // 발행(pub) : 접두사로 시작하는 메시지는 @MessageMapping이 달린 메서드로 라우팅
        config.setApplicationDestinationPrefixes("/pub");
    }
}
