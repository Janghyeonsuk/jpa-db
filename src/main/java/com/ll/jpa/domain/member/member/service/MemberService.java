package com.ll.jpa.domain.member.member.service;

import com.ll.jpa.domain.member.member.entity.Member;
import com.ll.jpa.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password, String nickname) {
        Member post = Member
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        return memberRepository.save(post);
    }

    public long count() {
        return memberRepository.count();
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
