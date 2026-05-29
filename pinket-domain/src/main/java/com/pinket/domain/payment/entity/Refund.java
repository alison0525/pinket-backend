package com.pinket.domain.payment.entity;

import com.pinket.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refund")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund extends BaseEntity {

    // 어떤 결제의 환불인지 - 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    // 환불 사유
    @Column(columnDefinition = "TEXT")
    private String reason;

    // 총 환불 금액 (반정규화 - 조회 성능을 위해 생성 시점에 확정)
    @Column(name = "total_refund_amount", nullable = false)
    private int totalRefundAmount;

    // 환불 상태 (REQUESTED, COMPLETED, REJECTED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RefundStatus status;

    @Builder
    public Refund(Payment payment, String reason, int totalRefundAmount) {
        this.payment = payment;
        this.reason = reason;
        this.totalRefundAmount = totalRefundAmount;
        this.status = RefundStatus.REQUESTED;
    }

    // 환불 완료
    public void complete() {
        this.status = RefundStatus.COMPLETED;
    }

    // 환불 거절
    public void reject() {
        this.status = RefundStatus.REJECTED;
    }
}