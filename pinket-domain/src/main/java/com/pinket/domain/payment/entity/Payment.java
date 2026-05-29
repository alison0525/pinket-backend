package com.pinket.domain.payment.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.order.entity.Orders;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

  // 어떤 주문의 결제인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Orders order;

  // PG사 거래 ID (토스페이먼츠 등 외부 결제 시스템의 거래 ID)
  @Column(name = "pg_transaction_id", unique = true)
  private String pgTransactionId;

  // 결제 수단 (CARD, KAKAO_PAY 등)
  @Column(length = 50)
  private String method;

  // 결제 금액
  @Column(nullable = false)
  private int amount;

  // 결제 상태 (READY, DONE, CANCELLED, FAILED)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PaymentStatus status;

  // 실제 결제 완료 시간
  @Column(name = "paid_at")
  private LocalDateTime paidAt;

  @Builder
  public Payment(Orders order, int amount) {
    this.order = order;
    this.amount = amount;
    this.status = PaymentStatus.READY; // 결제 생성 시 항상 READY
  }

  // 결제 완료
  public void done(String pgTransactionId, String method) {
    this.pgTransactionId = pgTransactionId;
    this.method = method;
    this.status = PaymentStatus.DONE;
    this.paidAt = LocalDateTime.now();
  }

  // 결제 취소
  public void cancel() {
    this.status = PaymentStatus.CANCELLED;
  }

  // 결제 실패
  public void fail() {
    this.status = PaymentStatus.FAILED;
  }
}
