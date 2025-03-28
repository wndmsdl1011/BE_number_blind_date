package com.example.BE_number_blind_date.member.Entity;

import com.example.BE_number_blind_date.member.Role.Role;
import com.example.BE_number_blind_date.post.Entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Column(name = "USER_NAME", nullable = false, length = 30)
    private String userName;

    @Column(name = "USER_PASSWORD", nullable = false, length = 200)
    private String userPassword;

    @Column(name = "GENDER", nullable = false, length = 2)
    private String gender; // "남성", "여성"

    @Column(name = "USER_NICKNAME", nullable = false, length = 30)
    private String nickname;

    @Column(name = "USER_CONTACT", nullable = false, length = 20)
    private String contact;

    @Column(name = "USER_AGE", nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_MAJOR",nullable = false)
    private Major major;

    @Column(name = "LOCATION", length = 30)
    private String location;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Post post;


    @Builder
    public Member(String email, String userName, String userPassword, String gender,
                  String nickname, String contact, int age,Major major, String location, Role role) {
        this.email = email;
        this.userName = userName;
        this.userPassword = userPassword;
        this.gender = gender;
        this.nickname = nickname;
        this.contact = contact;
        this.age = age;
        this.major = major;
        this.location = location;
        this.role = role;
        this.createdAt = LocalDate.from(LocalDateTime.now());
        this.updatedAt = LocalDate.from(LocalDateTime.now());
    }

}
