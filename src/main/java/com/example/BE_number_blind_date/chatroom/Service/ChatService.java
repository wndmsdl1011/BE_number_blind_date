package com.example.BE_number_blind_date.chatroom.Service;

import com.example.BE_number_blind_date.chatroom.Dto.DtoCreateChatRoom;
import com.example.BE_number_blind_date.chatroom.Dto.DtoChatRoomList;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import com.example.BE_number_blind_date.chatroom.Repository.ChatRepository;
import com.example.BE_number_blind_date.chatroom.Entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;


    // 채팅방 생성 로직
    @Transactional
    public DtoCreateChatRoom createChat(String ownerEmail, String senderEmail) {

        Member owner = memberRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자룰 찾을 수 없습니다."));

        Member sender = memberRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("대화 상대를 찾을 수 없습니다."));

        // 중복 방인지??
        Optional<ChatRoom> existingChat = chatRepository.findByCreatorAndReceiver(owner, sender);
        if (existingChat.isPresent()) {
            throw new IllegalStateException("이미 존재하는 채팅방입니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .creator(owner)
                .receiver(sender)
                .createdDate(LocalDate.now())
                .build();

        chatRepository.save(chatRoom);

        return new DtoCreateChatRoom(chatRoom.getChatRoomId(), owner, sender, chatRoom.getCreatedDate() );
    }

    // 채팅방 목록 가져오기
    @Transactional(readOnly = true)
    public List<DtoChatRoomList> getChatRooms(String userEmail) {

        Optional<Member> member = memberRepository.findByEmail(userEmail);

        return chatRepository.findByCreatorOrReceiver(member.orElse(null), member.orElse(null))
                .stream()
                .map(DtoChatRoomList::new)
                .collect(Collectors.toList());
    }
}
