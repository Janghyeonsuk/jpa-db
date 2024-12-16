package com.ll.jpa.domain.member.member.entity;

import com.ll.jpa.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseTime {
    @Column(unique = true, length = 30)
    private String username;

    @Column(length = 100)
    private String password;

    private String nickname;
}
