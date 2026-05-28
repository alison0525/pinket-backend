package com.pinket.domain.member.entity;

import com.pinket.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    // 소셜 로그인 제공자 (KAKAO, GOOGLE)
    @Column(name = "oauth_provider", nullable = false, length = 20)
    private String oauthProvider;

    // 소셜 로그인 제공자의 고유 ID
    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    // 권한 (USER, HOST, ADMIN)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberRole role;

    // 소프트딜리트 - null이면 활성 상태, 값이 있으면 삭제된 상태
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Member(
            String email,
            String nickname,
            String profileImageUrl,
            String oauthProvider,
            String oauthId,
            MemberRole role) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.role = role != null ? role : MemberRole.USER;
    }
}
