package com.pinket.domain.notification.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    // 알림 받는 회원 - 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 알림 타입 (TICKET_OPEN, PURCHASE_COMPLETE, REFUND_COMPLETE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    // 알림 제목
    @Column(nullable = false)
    private String title;

    // 알림 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 읽음 여부
    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Builder
    public Notification(Member member, NotificationType type, String title, String content) {
        this.member = member;
        this.type = type;
        this.title = title;
        this.content = content;
        this.isRead = false; // 생성 시 항상 읽지 않은 상태
    }

    // 읽음 처리
    public void read() {
        this.isRead = true;
    }
}
