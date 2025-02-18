package com.example.BE_number_blind_date.member.Service;

import com.example.BE_number_blind_date.member.Dto.CustomUserDetails;
import com.example.BE_number_blind_date.member.Entity.Member;
import com.example.BE_number_blind_date.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        Member userdata = memberRepository.findByUserName(username);

        if (userdata == null){
            throw  new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return new CustomUserDetails(userdata);
    }


}
