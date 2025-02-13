package com.example.BE_number_blind_date.member.Dto;

import com.example.BE_number_blind_date.member.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    // Role 값을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole().name();
            }
        }));


        return collection;
    }

    @Override
    public String getPassword() {

        return member.getUserPassword();
    }

    @Override
    public String getUsername() {

        return member.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}