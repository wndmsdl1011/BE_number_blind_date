package com.example.BE_number_blind_date.member.Entity;

import com.example.BE_number_blind_date.member.Role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 제한, JPA만 접근할 수 있도록 설정
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, length = 30, unique = true)
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Column(name = "USER_NAME", nullable = false, length = 30)
    private String userName;

    @Column(name = "USER_PASSWORD", nullable = false, length = 200)
    private String userPassword;

    @Column(name = "GENDER", nullable = false, length = 2)
    private String gender; // "남성", "여성"

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "LOCATION", length = 30)
    private String location;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    public Member(String email, String userName, String userPassword, String gender,
                  LocalDateTime birthDate, String location, Role role) {
        this.email = email;
        this.userName = userName;
        this.userPassword = userPassword;
        this.gender = gender;
        this.birthDate = birthDate;
        this.location = location;
        this.role = role;
        this.createdAt = LocalDate.from(LocalDateTime.now());
        this.updatedAt = LocalDate.from(LocalDateTime.now());
    }

    public void updateProfile(String userName, String location) {
        this.userName = userName;
        this.location = location;
        this.updatedAt = LocalDate.from(LocalDateTime.now());
    }
}
