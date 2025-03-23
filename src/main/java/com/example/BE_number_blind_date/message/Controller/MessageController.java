package com.example.BE_number_blind_date.message.Controller;

import com.example.BE_number_blind_date.message.Dto.DtoChatMessage;
import com.example.BE_number_blind_date.message.Service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    // 메시지 전송
    @MessageMapping("/chat/{chatRoomId}")
    @SendTo("/sub/chat/{chatRoomId}")
    public void sendMessage(@Payload DtoChatMessage dtoChatMessage, @DestinationVariable Long chatRoomId) {

        messageService.saveMessage(dtoChatMessage);

        // /sub/chat/{chatroomId} 룸번호의 구독하는 사람들에게 전송
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatRoomId, dtoChatMessage);
    }

}
