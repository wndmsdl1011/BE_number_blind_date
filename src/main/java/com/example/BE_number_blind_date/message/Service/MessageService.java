package com.example.BE_number_blind_date.message.Service;

import com.example.BE_number_blind_date.chatroom.Repository.ChatRepository;
import com.example.BE_number_blind_date.message.Dto.DtoChatMessage;
import com.example.BE_number_blind_date.message.Entity.Message;
import com.example.BE_number_blind_date.message.Repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    // 전송한 메시지를 저장
    public void saveMessage(DtoChatMessage dtoChatMessage) {
        Message message = Message.builder()
                .sender(dtoChatMessage.getSender())
                .content(dtoChatMessage.getContent())
                .createAt(LocalDateTime.now())
                .chatRoom(chatRepository.findById(dtoChatMessage.getChatRoomId()).orElseThrow())
                .build();


        messageRepository.save(message);
    }
}
