package com.pinket.domain.order.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {

    // 주문한 회원 - 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 주문 상태 (PENDING, PAID, CANCELLED, EXPIRED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    // 총 결제 금액
    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    // 소프트딜리트
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Orders(Member member, int totalAmount) {
        this.member = member;
        this.status = OrderStatus.PENDING; // 주문 생성 시 항상 PENDING
        this.totalAmount = totalAmount;
    }

    // 결제 완료
    public void paid() {
        this.status = OrderStatus.PAID;
    }

    // 주문 취소
    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    // 주문 만료 (5분 내 미결제)
    public void expire() {
        this.status = OrderStatus.EXPIRED;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
