package com.pinket.domain.member.repository;

import com.pinket.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 활성 회원 조회 (소프트딜리트 고려)
    Optional<Member> findByEmailAndDeletedAtIsNull(String email);

    // OAuth 제공자 + OAuth ID로 회원 조회 (소셜 로그인)
    Optional<Member> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);

}
