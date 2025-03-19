package com.example.BE_number_blind_date.chat.Controller;


import com.example.BE_number_blind_date.chat.Dto.DtoCreateChatRoom;
import com.example.BE_number_blind_date.chat.Dto.DtoChatRoomList;
import com.example.BE_number_blind_date.chat.Service.ChatService;
import com.example.BE_number_blind_date.util.JWTUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JWTUtil jwtUtil;


    // 채팅방 생성 로직
    @PostMapping("/create/chat/room")
    public ResponseEntity<?> createChatRoom(@RequestHeader(value = "Authorization", required = false) String token,      // 채팅을 요청한 사람
                                            @RequestParam String ownerEmail) {       // 방장

        log.info(ownerEmail);
        token = token.replace("Bearer ", "");

        if (jwtUtil.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
        }

        String senderEmail = jwtUtil.getUsername(token);  // 수신자를 가져옴

        if (senderEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        DtoCreateChatRoom newchatroom = chatService.createChat(ownerEmail, senderEmail );
        return ResponseEntity.ok(newchatroom);

    }

    // 현재 참여하고 있는 채팅방 목록 조회
    @GetMapping("/chat/rooms")
    public ResponseEntity<List<DtoChatRoomList>> getAllChatRooms(@RequestHeader(value = "Authorization", required = false) String token) {
        token = token.replace("Bearer ", "");   // 본인

        String userEmail = jwtUtil.getUsername(token);
        List<DtoChatRoomList> chatRooms = chatService.getChatRooms(userEmail);

        return ResponseEntity.ok(chatRooms);
    }


}
