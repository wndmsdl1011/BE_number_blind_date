package com.example.BE_number_blind_date.chat.Service;

import com.example.BE_number_blind_date.chat.Dto.DtoChatRoom;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import com.example.BE_number_blind_date.chat.Repository.ChatRepository;
import com.example.BE_number_blind_date.chat.Entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;


    // 채팅방 생성 로직
    @Transactional
    public DtoChatRoom createChat(String ownerEmail, String senderEmail) {

        Member owner = memberRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자룰 찾을 수 없습니다."));

        Member sender = memberRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("대화 상대를 찾을 수 없습니다."));

        // 중복 방인지??
        Optional<Chat> existingChat = chatRepository.findByCreatorAndReceiver(owner, sender);
        if (existingChat.isPresent()) {
            throw new IllegalStateException("이미 존재하는 채팅방입니다.");
        }

        Chat chatRoom = Chat.builder()
                .creator(owner)
                .receiver(sender)
                .build();

        chatRepository.save(chatRoom);

        return new DtoChatRoom(chatRoom.getChatRoomId(), owner, sender);
    }

}
